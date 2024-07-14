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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author lethimcook
 */
@MultipartConfig
public class SongServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("[SongServlet] :: Action: " + action);
        boolean r = false;
        try {
            switch (action) {
                case "updateSong" -> {
                    Integer songId = Integer.valueOf(request.getParameter("songId"));
                    String title = request.getParameter("title");
                    String album = request.getParameter("album");
                    String artist = request.getParameter("artist");
                    Part songImg = request.getPart("songImg");
//                    System.out.println("[Song Servlet] :: " + action);
//                    System.out.println("Id: " + songId + ", title: " + title + ", " + album + ", aritst: " + artist + ", songImg: " + songImg.getSubmittedFileName());
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
                            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            response.getWriter().write("Update failed...");
                        }
                    }
                }

                case "deleteSong" -> {
                    System.out.println("[SongServlet] :: Handle delete song");
                    System.out.println("TESTING: " + getServletContext().getRealPath("/"));
                    String sId = request.getParameter("sId");
                    int id;
                    try {
                        id = Integer.parseInt(sId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID format: " + sId);
                        response.sendRedirect("login");
                        return;
                    }

                    SongDao dao = new SongDao();
                    Optional<Song> songOptional = dao.get(id);
                    System.out.println("SongDAO returned: " + (songOptional.isPresent() ? "Song found" : "Song not found"));

                    if (songOptional.isPresent()) {
                        Song song = songOptional.get();
                        Helpers h = new Helpers();
                        // Get the file paths from the song object
                        String songFilePath = h.replaceWithBackwardSlash(song.getSongFilePath());
                        String songImagePath = h.replaceWithBackwardSlash(song.getSongImagePath());
                        System.out.println("Testing songFilePath: " + songFilePath);
                        System.out.println("Testing songImagePath: " + songFilePath);
                        // Delete the song file and image file from disk
                        deleteFile(songFilePath, request);
                        deleteFile(songImagePath, request);
                        deleteFolder(songFilePath, request);

                        // Delete the song from the database
                        dao.delete(id);
                        System.out.println("Deleted song with ID: " + id);
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("No song found with ID: " + id);
                        return;
                    }
                    response.getWriter().write("Deleting success!");
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

    /**
     * Deletes a file given its path.
     *
     * @param filePath The path of the file to be deleted.
     */
    private void deleteFile(String filePath, HttpServletRequest request) {
        if (filePath != null && !filePath.isEmpty()) {
            // Get the servlet context path where your web application is deployed
            String str_appPath = getServletContext().getRealPath("/");
            Path appPath = Paths.get(str_appPath);
            File appFile = appPath.toFile();

            if (appFile != null) {
                // Construct the full file path within the desired directory
                String fullPath = appFile.getAbsolutePath() + File.separator + filePath;
                Path fullPathObj = Paths.get(fullPath);
                System.out.println("Testing full path: " + fullPath);
                File file = fullPathObj.toFile();
                System.out.println("Testing final filePath : " + file.getPath());

                if (file.exists() && file.isFile()) {
                    boolean deleted = file.delete();
                    System.out.println("File " + fullPath + " deletion status: " + deleted);
                } else {
                    System.out.println("File " + fullPath + " does not exist or is not a file.");
                }
            } else {
                System.out.println("Parent directory not found for file: " + filePath);
            }
        } else {
            System.out.println("File path is null or empty: " + filePath);
        }
    }

    private void deleteFolder(String filePath, HttpServletRequest request) {
        if (filePath != null && !filePath.isEmpty()) {
            // Get the servlet context path where your web application is deployed
            String str_appPath = getServletContext().getRealPath("/");
            Path appPath = Paths.get(str_appPath);
            File appFile = appPath.toFile();

            if (appFile != null) {

                // Construct the full file path within the desired directory
                String fullPath = appFile.getAbsolutePath() + File.separator + filePath;
                Path toPath_folder = Paths.get(fullPath);
                File folder = toPath_folder.toFile();
                Path fullPathObj = Paths.get(folder.getAbsolutePath());

                File file = fullPathObj.toFile();
                file = file.getParentFile();
                if (file.exists() && file.isDirectory()) {
                    boolean deleted = file.delete();
                    System.out.println("Folder " + file.toString() + " deletion status: " + deleted);
                } else {
                    System.out.println("Folder " + file.toString() + " does not exist or is not a Folder.");
                }
            } else {
                System.out.println("Parent directory not found for Folder");
            }
        } else {
            System.out.println("Folder path is null or empty: " + filePath);
        }
    }
}
