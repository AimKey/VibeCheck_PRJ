package Controller.Servlets;

import Database.DatabaseInformation;
import Model.Daos.PlaylistSongsDao;
import Model.Daos.SongDao;
import Model.PlaylistSongs;
import Model.Song;
import Utils.JSONWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author phamm
 */
public class PlaylistSongsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String param = request.getParameter("playlistId");
        Integer playlistId = -1;
        if (param != null) {
            playlistId = Integer.valueOf(param);
        }
        switch (action) {
            case "get" -> {
                ArrayList<Song> songs;
                if (playlistId == 0) {
                    // Return the system songs
                    songs = new SongDao().getAll();
                } else {
                    songs = new PlaylistSongsDao().getAllSongsByPlaylistId(playlistId);
                }
                response.setContentType("application/json;charset=UTF-8");

                String json1 = new JSONWriter<ArrayList<Song>>().getJSONString(songs);
                response.getWriter().write(json1);
            }
            case "getUnique" -> {
                ArrayList<Song> songs = new PlaylistSongsDao().getUniqueSongs(playlistId);
                response.setContentType("application/json;charset=UTF-8");

                String json1 = new JSONWriter<ArrayList<Song>>().getJSONString(songs);
                response.getWriter().write(json1);
            }
            default -> {
                System.out.println("Something went wrong! (PlaylistSongs JSON)");
                throw new AssertionError();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String param = request.getParameter("playlistId");
        Integer playlistId = -1;
        if (param != null) {
            playlistId = Integer.valueOf(param);
        }
        System.out.println("Getting action: " + action);

        if (action == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("No action provided!!!");
            return;
        }
        switch (action) {
            case "insertSongs" -> {
                String songIds = request.getParameter("songIds");
                if (songIds != null && playlistId != -1) {
                    String[] songIdArray = songIds.split(",");
                    PlaylistSongsDao playlistSongsDao = new PlaylistSongsDao();
                    for (String songIdStr : songIdArray) {
                        int songId = Integer.parseInt(songIdStr);
                        System.out.println("Inserting songId: " + songId + ", to playlist: " + playlistId);
                        PlaylistSongs playlistSongs = new PlaylistSongs(playlistId, songId);
                        playlistSongsDao.insert(playlistSongs);
                    }
                }
                response.sendRedirect("settings");
            }

            case "delSong" -> {
                System.out.println("DEBUG: DeleteServlet - doPost method called");

                try {
                    String songIdStr = request.getParameter("songId");
                    String playlistIdStr = request.getParameter("playlistId");

                    System.out.println("DEBUG: songId = " + songIdStr);

                    if (songIdStr == null || playlistIdStr == null || action == null) {
                        System.out.println("DEBUG: Missing parameters.");
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Missing parameters.");
                        return;
                    }
                    int songId = Integer.parseInt(songIdStr);

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

                } catch (NumberFormatException e) {
                    System.out.println("DEBUG: Invalid songId or playlistId format: " + e.getMessage());
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Invalid songId or playlistId format.");
                }
            }
            default -> {
                System.out.println("Something went wrong! (PlaylistSongs)");
                throw new AssertionError();
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
