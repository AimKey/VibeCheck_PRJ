package Controller.Servlets;

import Model.Daos.SongDao;
import Model.Song;
import Utils.Helpers;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.function.Supplier;

/**
 *
 * @author lethimcook
 */
@MultipartConfig
public class SongServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        boolean r = false;
        try {
            switch (action) {
                case "updateSong" -> {
                    Integer songId = Integer.valueOf(request.getParameter("songId"));
                    String title = request.getParameter("title");
                    String album = request.getParameter("album");
                    String artist = request.getParameter("artist");
                    Part songImg = request.getPart("songImg");
                    System.out.println("[Song Servlet] :: " + action);
                    System.out.println("Id: " + songId + ", title: " + title + ", " + album + ", aritst: " + artist + ", songImg: " + songImg.getSubmittedFileName());
                    // Get the song from DB
                    Supplier<Song> supplier = () -> null;
                    Song target = new SongDao().get(songId).orElseGet(supplier);
                    if (target == null) {
                        System.out.println("[Song Servlet] :: WARNING, INVALID SONG IN DATABASE");
                    } else {
                        if (songImg.getSize() <= 0 || songImg.getSubmittedFileName().isEmpty()) {
                            // Update without new song img
                            r = new SongDao().update(target, new String[]{title, artist, album});
                        } else {
                            // Update with new song img
                            String baseDir = getServletContext().getRealPath("/");
                            String songImgDir = new Helpers().getNewFileLocation(baseDir, "songs", target.getTitle(), "jpg");
                            System.out.println("Saving new image to: " + songImgDir);
                            // In case the song has a fallback jpg, we need to change that too.
                            String newSongImagePath = "songs/" + target.getTitle() + File.separator + target.getTitle() + ".jpg";
                            new Helpers().buildDirectory(songImgDir);
                            songImg.write(songImgDir); // Overwrite the old one
                            r = new SongDao().update(target, new String[]{title, artist, album, newSongImagePath});
                        }

                        if (r) {
                            response.getWriter().write("Update success!");
                        } else {
                            response.getWriter().write("Update failed...");
                        }
                    }
                }
                default ->
                    throw new AssertionError();
            }
        } catch (ServletException | NumberFormatException e) {
            System.out.println(e.getMessage());
            response.getWriter().write(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response.getWriter().write("FATAL: Song does not exist on server disk!");
        }

    }
}
