package Controller.Servlets;

import Model.Song;
import Utils.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

@MultipartConfig
public class UploadSong extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utils utils = new Utils();

        // Define the base directory where songs will be saved
        String baseDir = getServletContext().getRealPath("/") + "../../build/songs/";
        baseDir = Paths.get(baseDir).normalize().toString();  // Normalize the path to remove any redundant elements
        Collection<Part> parts = request.getParts();

        try {
            for (Part part : parts) {
                if (part.getName().equals("songsUpload") && part.getSize() > 0) {
                    String fileName = utils.getFileNameWithoutExtension(part.getSubmittedFileName());
                    String songDir = "songs/" + fileName + "/";
                    String fullPath = baseDir + File.separator + fileName + File.separator + part.getSubmittedFileName();

                    // Ensure the song directory exists and save the file
                    utils.setupSongFolder(fullPath, parts);

                    // Read song metadata
                    Song song = utils.readSongMetadata(new File(fullPath), true, baseDir + File.separator + fileName + File.separator + fileName + ".jpg");

                    // Log metadata for debugging
                    System.out.println("Title: " + song.getTitle());
                    System.out.println("Artist: " + song.getArtist());
                    System.out.println("Album: " + song.getAlbum());
                    System.out.println("Duration: " + song.getDuration());
                    System.out.println("File Path: " + songDir + part.getSubmittedFileName());
                    System.out.println("Image Path: " + songDir + fileName + ".jpg");

                    // Set song metadata as request attributes
                    request.setAttribute("title", song.getTitle());
                    request.setAttribute("artist", song.getArtist());
                    request.setAttribute("album", song.getAlbum());
                    request.setAttribute("duration", song.getDuration());
                    request.setAttribute("songFilePath", songDir + part.getSubmittedFileName());
                    request.setAttribute("songImagePath", songDir + fileName + ".jpg");

                    // Forward to JSP located in web/Pages/Includes
                    request.getRequestDispatcher("Pages/Includes/admin_upload.jsp").forward(request, response);
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect("Pages/Includes/upload_failed.jsp");
        }
    }
}
