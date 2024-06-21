package Controller.Servlets;

import Model.Song;
import Model.SongDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
        // Cookies and session stuff getting from here
        String id_raw = request.getParameter("id");
        int id = Integer.parseInt(id_raw);
        String action = request.getParameter("action");

        if (!"delete".equalsIgnoreCase(action)) {
            response.sendRedirect("index.html");
            return;
        }

        SongDAO dao = new SongDAO();
        Optional<Song> songOptional = dao.get(id);

        if (songOptional.isPresent()) {
            Song song = songOptional.get();

            // Delete the song file and image file from disk
            // hope it work ~_~
            deleteFile(song.getSongFilePath());
            deleteFile(song.getSongImagePath());

            // Delete the song from the database
            dao.delete(id);
        }

        response.sendRedirect("index.html"); // Redirect after processing
    }

    /**
     * Deletes a file given its path.
     *
     * @param filePath The path of the file to be deleted.
     */
    private void deleteFile(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }
}
