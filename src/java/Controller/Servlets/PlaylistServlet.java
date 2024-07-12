package Controller.Servlets;

import Model.Daos.PlaylistDao;
import Model.Playlist;
import Utils.JSONWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.Cookie;

public class PlaylistServlet extends HttpServlet {

    //logger for debugging 
    private static final Logger LOGGER = Logger.getLogger(PlaylistServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    private Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("id".equals(cookie.getName())) {
                        userId = Integer.valueOf(cookie.getValue());
                        break;
                    }
                }
            }
        }
        return userId;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String param = request.getParameter("action");
        LOGGER.log(Level.INFO, "Received GET request with action: {0}", param);
        switch (param) {
            case "getJSON" -> {
                String result = null;
                if (param.equals("all")) {
                    LOGGER.log(Level.INFO, "TODO: Handle get ALL playlist");
                } else if (param.equals("get")) {
                    Integer id = Integer.valueOf(request.getParameter("id"));
                    LOGGER.log(Level.INFO, "Getting playlist with id: {0}", id);
                    Optional<Playlist> optional = new PlaylistDao().get(id);
                    if (optional.isPresent()) {
                        Playlist p = optional.get();
                        result = new JSONWriter<Playlist>().getJSONString(p);
                        LOGGER.log(Level.INFO, "Found playlist: {0}", p);
                    } else {
                        LOGGER.log(Level.WARNING, "Playlist with id {0} not found", id);
                    }
                }
                if (result != null) {
                    response.getWriter().write(result);
                }
            }
            default -> {
                LOGGER.log(Level.SEVERE, "Unknown action: {0}", param);
                throw new AssertionError();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String param = request.getParameter("action");
        LOGGER.log(Level.INFO, "Received POST request with action: {0}", param);
        switch (param) {
            case "create" -> {
                Integer userId = getUserId(request);
                String playlistName = request.getParameter("playlistName");

                LOGGER.log(Level.INFO, "Creating new playlist with name: {0} for userId: {1}", new Object[]{playlistName, userId});

                if (userId != null && playlistName != null && !playlistName.trim().isEmpty()) {
                    Playlist newPlaylist = new Playlist(userId, playlistName, 0, LocalDate.now());
                    boolean success = new PlaylistDao().insert(newPlaylist);
                    if (success) {
                        LOGGER.log(Level.INFO, "Playlist created successfully: {0}", newPlaylist);
                    } else {
                        LOGGER.log(Level.SEVERE, "Failed to create playlist: {0}", newPlaylist);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Invalid userId or playlistName. userId: {0}, playlistName: {1}", new Object[]{userId, playlistName});
                }

                response.sendRedirect("settings");
            }
            case "updatePName" -> {
                String pName = request.getParameter("pName");
                Integer pId = Integer.valueOf(request.getParameter("pId"));
                LOGGER.log(Level.INFO, "Updating playlist name to: {0} for playlistId: {1}", new Object[]{pName, pId});
                // Here you should implement the update logic
                response.sendRedirect("settings");
            }
            default -> {
                LOGGER.log(Level.SEVERE, "Unknown action: {0}", param);
                throw new AssertionError();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Received DELETE request");
        // Implement delete logic
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Received PUT request");
        // Implement update logic
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
