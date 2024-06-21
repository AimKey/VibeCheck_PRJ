
package Controller.Servlets;

import Database.DatabaseInformation;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author lethimcook
 */
public class EditSong extends HttpServlet {

    private DatabaseInformation db;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        db = new DatabaseInformation();
        int songId = Integer.parseInt(request.getParameter("songId"));
        String title = request.getParameter("title");
        int duration = Integer.parseInt(request.getParameter("duration"));
        String filePath = request.getParameter("filePath");
        String genre = request.getParameter("genre");
        String coverPictureLink = request.getParameter("coverPictureLink");

        try (Connection con = db.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "update Song set title = ?, duration = ?, file_path = ?, genre = ?, cover_picture_link = ? where songId = ?");
            stmt.setString(1, title);
            stmt.setInt(2, duration);
            stmt.setString(3, filePath);
            stmt.setString(4, genre);
            stmt.setString(5, coverPictureLink);
            stmt.setInt(6, songId);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // TODO: insert edit_success.html
        response.sendRedirect("hmuhmu");
    }
}
