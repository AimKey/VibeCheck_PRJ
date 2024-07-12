package Controller.Servlets;

import Database.DatabaseInformation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@MultipartConfig
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: DeleteServlet - doGet method called");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: DeleteServlet - doPost method called");

        try {
            String songIdStr = request.getParameter("songId");
            String playlistIdStr = request.getParameter("playlistId");
            String action = request.getParameter("action");

            System.out.println("DEBUG: songId = " + songIdStr);
            System.out.println("DEBUG: playlistId = " + playlistIdStr);
            System.out.println("DEBUG: action = " + action);

            if (songIdStr == null || playlistIdStr == null || action == null) {
                System.out.println("DEBUG: Missing parameters.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing parameters.");
                return;
            }

            int songId = Integer.parseInt(songIdStr);
            int playlistId = Integer.parseInt(playlistIdStr);

            if (action.equals("deleteSongFromPlaylist")) {
                DatabaseInformation i = new DatabaseInformation();
                // Delete the song from the playlist
                try (Connection conn = i.getConnection()) {
                    System.out.println("DEBUG: Connection established");

                    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM PlaylistSongs WHERE playlistId = ? AND songId = ?");
                    pstmt.setInt(1, playlistId);
                    pstmt.setInt(2, songId);
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("DEBUG: Song deleted successfully from playlist!");
                        response.getWriter().write("Song deleted successfully from playlist!");
                    } else {
                        System.out.println("DEBUG: No rows affected, check if the song and playlist IDs are correct.");
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("No song found with the provided ID in the specified playlist.");
                    }
                } catch (SQLException e) {
                    System.out.println("DEBUG: Error deleting song from playlist: " + e.getMessage());
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Error deleting song from playlist: " + e.getMessage());
                }
            } else {
                System.out.println("DEBUG: Unknown action: " + action);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Unknown action.");
            }
        } catch (NumberFormatException e) {
            System.out.println("DEBUG: Invalid songId or playlistId format: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid songId or playlistId format.");
        }
    }
}
