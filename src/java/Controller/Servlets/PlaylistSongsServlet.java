package Controller.Servlets;

import Model.Daos.PlaylistSongsDao;
import Model.PlaylistSongs;
import Model.Song;
import Utils.JSONWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

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
                ArrayList<Song> songs = new PlaylistSongsDao().getAllSongsByPlaylistId(playlistId);
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
        switch (action) {
            case "insertSongs" -> {
                String songIds = request.getParameter("songIds");
                if (songIds != null && playlistId != -1) {
                    String[] songIdArray = songIds.split(",");
                    PlaylistSongsDao playlistSongsDao = new PlaylistSongsDao();
                    for (String songIdStr : songIdArray) {
                        int songId = Integer.parseInt(songIdStr);
                        PlaylistSongs playlistSongs = new PlaylistSongs(playlistId, songId);
                        playlistSongsDao.insert(playlistSongs);
                    }
                }
                response.sendRedirect("settings");
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
