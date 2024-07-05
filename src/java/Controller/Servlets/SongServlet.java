package Controller.Servlets;

import Database.DatabaseInformation;
import Model.Daos.SongDao;
import Model.Song;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.function.Supplier;

/**
 *
 * @author lethimcook
 */
@MultipartConfig
public class SongServlet extends HttpServlet {

    private DatabaseInformation db;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "updateSong" -> {
                int songId = Integer.parseInt(request.getParameter("songId"));
                String title = request.getParameter("title");
                String album = request.getParameter("album");
                String artist = request.getParameter("artist");
                Part songImg = request.getPart("songImg");
                System.out.println("[Song Servlet] :: " + action);
                // System.out.println("Id: " + songId + ", title: " + title + ", " + album + ", aritst: " + artist + ", songImg: " + songImg.getSubmittedFileName());
                // Get the song from DB
                Supplier<Song> supplier = () -> null;
                Song target = new SongDao().get(songId).orElseGet(supplier);
                if (target == null) {
                    System.out.println("[Song Servlet] :: WARNING, INVALID SONG IN DATABASE");
                } else {
                    String songImgPath = target.getSongImagePath();
                    if (songImg.getSize() <= 0 || songImg.getSubmittedFileName().isEmpty()) {
                        // Update without new song img
                        new SongDao().update(target, new String[]{title, album, artist});
                    } else {
                        // Update with new song img
                    }
                }
            }
            default ->
                throw new AssertionError();
        }

        // TODO: insert edit_success.html
        response.sendRedirect("settings");
    }
}
