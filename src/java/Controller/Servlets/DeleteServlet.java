package Controller.Servlets;

import Model.Song;
import Model.SongDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * DeleteServlet to handle the deletion of a song and its associated files.
 *
 * Author: EgiKarina
 */
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Assuming there's a session going on
        System.out.println("Received a POST request for deletion.");

        String id_raw = request.getParameter("id");
        System.out.println("Received ID: " + id_raw);

        int id;
        try {
            id = Integer.parseInt(id_raw);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format: " + id_raw);
            response.sendRedirect("index.html");
            return;
        }

        String action = request.getParameter("action");
        System.out.println("Received action: " + action);

        if (!"delete".equalsIgnoreCase(action)) {
            System.out.println("Invalid action, redirecting to index.html");
            response.sendRedirect("index.html");
            return;
        }

        SongDAO dao = new SongDAO();
        Optional<Song> songOptional = dao.get(id);
        System.out.println("SongDAO returned: " + (songOptional.isPresent() ? "Song found" : "Song not found"));

        if (songOptional.isPresent()) {
            Song song = songOptional.get();
            System.out.println("Song info: ");
            System.out.println("Song id: " + song.getSongId());
            System.out.println("Title: " + song.getTitle());
            System.out.println("Album: " + song.getAlbum());
            System.out.println("Artist id: " + song.getArtistId());
            System.out.println("File path: " + song.getSongFilePath());
            System.out.println("Image path: " + song.getSongImagePath());
            // Get the file paths from the song object
            String songFilePath = song.getSongFilePath();
            String songImagePath = song.getSongImagePath();

            // Delete the song file and image file from disk
            deleteFile(songFilePath, request);
            deleteFile(songImagePath, request);
            deleteFolder(songFilePath, request);

            // Delete the song from the database
            dao.delete(id);
            System.out.println("Deleted song with ID: " + id);
        } else {
            System.out.println("No song found with ID: " + id);
        }

        response.sendRedirect("index.html");
        System.out.println("Redirecting to index.html");
    }

    /**
     * Deletes a file given its path.
     *
     * @param filePath The path of the file to be deleted.
     */
    private void deleteFile(String filePath, HttpServletRequest request) {
        if (filePath != null && !filePath.isEmpty()) {
            // Get the servlet context path where your web application is deployed
            String str_appPath = request.getServletContext().getRealPath("/");
            Path appPath = Paths.get(str_appPath);
            File appFile = appPath.toFile();

            // Move up two levels from the web application deployment directory
            appFile = appFile.getParentFile().getParentFile();

            if (appFile != null) {
                // Construct the full file path within the desired directory
                String fullPath = appFile.getAbsolutePath() + File.separator + filePath;
                Path fullPathObj = Paths.get(fullPath);

                File file = fullPathObj.toFile();

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
            String str_appPath = request.getServletContext().getRealPath("/");
            Path appPath = Paths.get(str_appPath);
            File appFile = appPath.toFile();

            // Move up two levels from the web application deployment directory
            appFile = appFile.getParentFile().getParentFile();

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
