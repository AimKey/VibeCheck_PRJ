
package Controller;

import Misc.DatabaseInformation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author lethimcook
 */
public class UploadSong extends HttpServlet {
    
    private DatabaseInformation db;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        db = new DatabaseInformation("127.0.0.1", "AppMusicDatabase");
        
        String title = request.getParameter("title");
        int duration = Integer.parseInt(request.getParameter("duration"));
        String filePath = request.getParameter("filePath");
        String genre = request.getParameter("genre");
        String artistName = request.getParameter("artistName");
        String bio = request.getParameter("bio");
        String picturePath = request.getParameter("picturePath");
        
        try (Connection con = db.getConnection()) {
            // check if artist exist
            int artistId = -1;
            PreparedStatement stmt = con.prepareStatement("select artistId from Artist where artist name = ?");
            stmt.setString(1, artistName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                artistId = rs.getInt("artistId");
            } else {
                // insert new artist
                stmt = con.prepareStatement("insert into artist (artistName, bio, profile_picture_link) values (?,?,?)", 
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, artistName);
                stmt.setString(2, bio);
                stmt.setString(3, picturePath);
                stmt.executeUpdate();
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    artistId = rs.getInt(1);
                }
            }
            
            // check if song exists
            stmt = con.prepareStatement("select songId from Song where title = ? and artistId = ?");
            stmt.setString(1, title);
            stmt.setInt(2, artistId);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                // insert new song
                stmt = con.prepareStatement("insert into Song (artistId, title, duration, file_path, genre, cover_picture_link) values (?, ?, ?, ?, ?, ?)");
                stmt.setInt(1, artistId);
                stmt.setString(2, title);
                stmt.setInt(3, duration);
                stmt.setString(4, filePath);
                stmt.setString(5, genre);
                stmt.setString(6, picturePath);
                stmt.executeUpdate();
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // insert upload_success.html
        response.sendRedirect("smh");
    }
    
}
