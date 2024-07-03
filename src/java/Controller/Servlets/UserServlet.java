/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("Action: " + action);
        switch (action) {
            case "update" -> {
                System.out.println("[USER] Update :: ");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String confirm = request.getParameter("confirm-password");
                String url = request.getParameter("url");
                Part img = request.getPart("userImage");
                System.out.println("name: " + username + ", pass: " + password + ", confirm: " + confirm + ", url: " + url + ", img: " + img.getSubmittedFileName());
            }

            case "delete" -> {
                Integer userId = Integer.valueOf(request.getParameter("uId"));
                System.out.println("[USER] Delete :: " + userId);
            }
            
            case "promote" -> {
                Integer userId = Integer.valueOf(request.getParameter("uId"));
                System.out.println("[USER] Promote :: " + userId);
            }
            
            default -> {
                System.out.println("Unknown action: " + action);
                throw new AssertionError();
            }
        }
        response.sendRedirect("settings");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
