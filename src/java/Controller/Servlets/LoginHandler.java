/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Servlets;

import Database.DatabaseInformation;
import Model.AppUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        db = new DatabaseInformation();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        AppUser aUser = null;
        String user = request.getParameter("username");
        String pass = request.getParameter("pass");
        System.out.println("Request user: " + user + ", pass: " + pass);
        int count = 0;
        try (Connection con = db.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM AppUser a WHERE a.username = ? AND a.password = ?");
            stmt.setString(1, user);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("userId");
                String name = rs.getString("username");
                String email = rs.getString("email");
                String profilePicPath = rs.getString("profilePicPath");
                Boolean isAdmin = rs.getBoolean("isAdmin");
                count++;
                aUser = new AppUser(id, name, email, profilePicPath, isAdmin);
            }

            stmt.close();
            rs.close();

            System.out.println("User information: ");
            System.out.println(aUser);

            // No user found
            if (aUser == null) {
                request.setAttribute("msg", "Wrong user account!");
                request.getRequestDispatcher("Pages/Common/login.jsp").forward(request, response);
            } else {
                // Found user, logging in
                // Creating cookies
                Cookie userC = new Cookie("user", aUser.getUsername());
                Cookie adminC = new Cookie("admin", String.valueOf(aUser.getIsAdmin()));
                Cookie idC = new Cookie("id", String.valueOf(aUser.getUserId()));

                // Set cookies age
                userC.setMaxAge(60 * 60 * 24);
                adminC.setMaxAge(60 * 60 * 24);
                idC.setMaxAge(60 * 60 * 24);

                // Add cookies
                response.addCookie(userC);
                response.addCookie(adminC);
                response.addCookie(idC);

                // Setting the user session
                request.getSession().setAttribute("user", aUser.getUsername());
                request.getSession().setAttribute("id", aUser.getUserId());
                request.getSession().setAttribute("isAdmin", aUser.getIsAdmin());
                                request.getSession().setAttribute("isAdmin", aUser.getIsAdmin());

                // Dispatch
                if (aUser.getIsAdmin()) {
//                    request.getRequestDispatcher("admin.jsp").forward(request, response);
                    response.sendRedirect("admin");
                } else {
//                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    response.sendRedirect("Pages/Common/main.html");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
