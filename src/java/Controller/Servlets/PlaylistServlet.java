package Controller.Servlets;

import Model.AppUser;
import Model.Daos.PlaylistDao;
import Model.Daos.PlaylistSongsDao;
import Model.Daos.SongDao;
import Model.Playlist;
import Model.Song;
import Utils.JSONWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaylistServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String param = request.getParameter("action");
        switch (param) {
            case "get" -> {
                String result = null;
                System.out.println("Getting specified playlist");
                Integer id = Integer.valueOf(request.getParameter("id"));
                if (id == 0) {
                    Playlist p = new Playlist(0, 0, "System", new SongDao().getAll().size(), LocalDate.now());
                    result = new JSONWriter<Playlist>().getJSONString(p);
                } else {
                    Optional<Playlist> optional = new PlaylistDao().get(id);
                    if (optional.isPresent()) {
                        Playlist p = optional.get();
                        result = new JSONWriter<Playlist>().getJSONString(p);
                    }
                }
                response.getWriter().write(result);
            }
            case "getSongs" -> {
                Integer playlistId = Integer.valueOf(request.getParameter("playlistId"));
                List<Song> uniqueSongs = new PlaylistSongsDao().getUniqueSongs(playlistId);
                List<String> songFilePaths = new ArrayList<>();
                for (Song song : uniqueSongs) {
                    songFilePaths.add(song.getSongFilePath());
                }
                String jsonResult = new JSONWriter<List<String>>().getJSONString(songFilePaths);
                response.getWriter().write(jsonResult);
            }
            default -> {
                System.out.println("Action: " + param);
                throw new AssertionError();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String param = request.getParameter("action");
        switch (param) {
            case "create" -> {
                System.out.println("Creating new playlist");
                HttpSession session = request.getSession();
                AppUser user = (AppUser) session.getAttribute("user");
                Integer userId = user.getUserId();
                String playlistName = request.getParameter("playlistName");
                System.out.println("User id: " + userId + ", playlist: " + playlistName);
                if (userId != null && playlistName != null && !playlistName.trim().isEmpty()) {
                    Playlist newPlaylist = new Playlist(userId, playlistName, 0, LocalDate.now());
                    new PlaylistDao().insert(newPlaylist);
                }

                response.sendRedirect("settings");
            }
            case "updatePName" -> {
                String pName = request.getParameter("pName");
                Integer pId = Integer.valueOf(request.getParameter("pId"));
                Playlist pl = new PlaylistDao().get(pId).orElse(null);
                Boolean r = new PlaylistDao().update(pl, new String[]{pName});
                response.sendRedirect("settings");
            }

            case "delete" -> {
                String o = request.getParameter("pId");
                if (o != null) {
                    int id = Integer.parseInt(o);
                    Boolean r = new PlaylistDao().delete(id);
                    if (r) {
                        response.sendRedirect("settings");
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().write("Failed to delete");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Null parameter");
                }
            }
            default -> {
                throw new AssertionError();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
