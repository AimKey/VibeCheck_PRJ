/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Misc.DatabaseInformation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*; // What the frick netbeans frick yourself

/**
 *
 * @author phamm
 */
public class LoginHandler extends HttpServlet {

    private DatabaseInformation db;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Connecting..., Method: " + request.getMethod());
        db = new DatabaseInformation("Kuul", "AppMusicDatabase");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
      
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        int count = 0;
        try (Connection con = db.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT COUNT(*) FROM AppUser a WHERE a.username = ? AND a.password = ?");
            stmt.setString(1, user);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            
            rs.next(); // Move to the first row
            count = rs.getInt(1); // If count = 0 equals no user 
            
            stmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        if (count == 1) {
            request.setAttribute("r", true);
            request.getRequestDispatcher("success.html").forward(request, response);
        } else {
            request.setAttribute("r", false);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
