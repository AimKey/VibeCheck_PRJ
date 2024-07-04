package Controller.Servlets;

import Database.DatabaseInformation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SaveSong extends HttpServlet {

    private DatabaseInformation db;
    private static final int DEFAULT_ARTIST_ID = 1;
    private static final String DEFAULT_SONG_IMAGE_PATH = "default_song_image.jpg";
    private static final String DEFAULT_ARTIST_IMAGE_PATH = "default_artist_image.jpg";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        db = new DatabaseInformation();
        
        String title = request.getParameter("title");
        String artistName = request.getParameter("artist");
        String durationStr = request.getParameter("duration");
        String filePath = request.getParameter("songFilePath");
        String coverPictureLink = request.getParameter("songImagePath");

        // Print received parameters for debugging
        System.out.println("Title: " + title);
        System.out.println("Artist: " + artistName);
        System.out.println("Duration: " + durationStr);
        System.out.println("File Path: " + filePath);
        System.out.println("Cover Picture Link: " + coverPictureLink);

        // Validate parameters
        if (title == null || title.isEmpty() ||
            artistName == null || artistName.isEmpty() ||
            durationStr == null || durationStr.isEmpty() ||
            filePath == null || filePath.isEmpty() ||
            coverPictureLink == null || coverPictureLink.isEmpty()) {
            System.out.println("Validation failed: one or more parameters are empty");
            response.sendRedirect("Pages/Includes/upload_failed.jsp");
            return;
        }

        int duration;
        try {
            // Parse duration from MM:SS format to seconds
            String[] durationParts = durationStr.split(":");
            int minutes = Integer.parseInt(durationParts[0]);
            int seconds = Integer.parseInt(durationParts[1]);
            duration = (minutes * 60) + seconds;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Failed to parse duration: " + e.getMessage());
            response.sendRedirect("Pages/Includes/upload_failed.jsp");
            return;
        }

        try (Connection con = db.getConnection()) {
            // Check if artist exists
            int artistId = DEFAULT_ARTIST_ID;
            PreparedStatement stmt = con.prepareStatement("SELECT artistId, profilePicturePath FROM Artist WHERE artistName = ?");
            stmt.setString(1, artistName);
            ResultSet rs = stmt.executeQuery();
            String artistImagePath = DEFAULT_ARTIST_IMAGE_PATH;
            if (rs.next()) {
                artistId = rs.getInt("artistId");
                artistImagePath = rs.getString("profilePicturePath");
                if (artistImagePath == null || artistImagePath.isEmpty()) {
                    artistImagePath = DEFAULT_ARTIST_IMAGE_PATH;
                }
                System.out.println("Artist found: " + artistName + " with ID " + artistId);
            } else {
                // Insert new artist
                stmt = con.prepareStatement("INSERT INTO Artist (artistName, profilePicturePath) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, artistName);
                stmt.setString(2, DEFAULT_ARTIST_IMAGE_PATH);
                stmt.executeUpdate();
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    artistId = rs.getInt(1);
                    System.out.println("New artist inserted: " + artistName + " with ID " + artistId);
                }
            }

            // Check if song exists
            stmt = con.prepareStatement("SELECT songId FROM Song WHERE title = ? AND artistId = ?");
            stmt.setString(1, title);
            stmt.setInt(2, artistId);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                // Insert new song
                stmt = con.prepareStatement("INSERT INTO Song (artistId, title, duration, songFilePath, songImagePath, album) VALUES (?, ?, ?, ?, ?, ?)");
                stmt.setInt(1, artistId);
                stmt.setString(2, title);
                stmt.setInt(3, duration);
                stmt.setString(4, filePath);
                stmt.setString(5, (coverPictureLink == null || coverPictureLink.isEmpty()) ? DEFAULT_SONG_IMAGE_PATH : coverPictureLink);
                stmt.setString(6, request.getParameter("album")); // Album can be null
                stmt.executeUpdate();
                System.out.println("New song inserted: " + title + " by " + artistName);
            } else {
                System.out.println("Song already exists: " + title + " by " + artistName);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            response.sendRedirect("Pages/Includes/upload_failed.jsp");
            return;
        }

        response.sendRedirect("Pages/Includes/upload_success.jsp");
    }
}
