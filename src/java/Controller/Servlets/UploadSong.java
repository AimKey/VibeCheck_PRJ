package Controller.Servlets;

import Model.Daos.SongDao;
import Model.Song;
import Utils.Helpers;
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
        Helpers utils = new Helpers();
        String param = request.getParameter("action");
        System.out.println("Action: " + param);
        // Define the base directory where songs will be saved
        String baseDir = getServletContext().getRealPath("/") + "songs/";
        baseDir = Paths.get(baseDir).normalize().toString();  // Normalize the path to remove any redundant elements
        Collection<Part> parts = request.getParts();
//        System.out.println("Reading base dir: " + baseDir);
        int count = 0;
        try {
            for (Part part : parts) {
                System.out.println("Getting file: " + part.getSubmittedFileName());
                if (part.getName().equals("songsUpload") && part.getSize() > 0) {
                    String fileName = utils.getFileNameWithoutExtension(part.getSubmittedFileName());
                    String songDir = "songs\\" + fileName + "\\";
                    String fullPath = baseDir + File.separator + fileName + File.separator + part.getSubmittedFileName();

                    System.out.println("Reveive songs: " + part.getSubmittedFileName());
                    count++;
                    // Ensure the song directory exists and save the file
                    utils.setupSongFolder(fullPath, part);

                    // Read song metadata
                    Song song = utils.readSongMetadata(new File(fullPath), true, baseDir + File.separator + fileName + File.separator + fileName + ".jpg");
                    song.setSongFilePath(songDir + part.getSubmittedFileName());
                    song.setSongImagePath(songDir + fileName + ".jpg");

//                    // Log metadata for debugging
//                    System.out.println("Title: " + song.getTitle());
//                    System.out.println("Artist: " + song.getArtist());
//                    System.out.println("Album: " + song.getAlbum());
//                    System.out.println("Duration: " + song.getDuration());
//                    System.out.println("File Path: " + songDir + part.getSubmittedFileName());
//                    System.out.println("Image Path: " + songDir + fileName + ".jpg");
                    // Bro put it in the SongDao
                    System.out.println("[SONG DAO DEBUG] :: " + new SongDao().insertWithMsg(song));
                }
            }
            response.getWriter().write("Uploaded " + count + " songs successfuly!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.getWriter().write("Upload error: " + e.getMessage());
        }
    }
}
