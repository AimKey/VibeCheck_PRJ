/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Servlets;

import Model.AppUser;
import Model.Daos.AppUserDao;
import Utils.Helpers;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.function.Supplier;

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
        System.out.println("[UserServlet] Action :: " + action);
        try {
            switch (action) {
                case "update" -> {
                    System.out.println("[USER] Update :: ");
                    int userId = Integer.parseInt(request.getParameter("uId"));
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String email = request.getParameter("email");
                    Part img = request.getPart("userImg");
//                    System.out.println("User id: " + userId + ", name: " + username + ", pass: " + password + ", confirm: " + confirm + ", img: " + img.getSubmittedFileName());
//                    System.out.println("Headers: " + img.getHeader("content-type"));

                    String baseDir = request.getServletContext().getRealPath("/");
                    Helpers helpers = new Helpers();

                    Supplier<AppUser> a = () -> null;
                    AppUser user = new AppUserDao().get(userId).orElseGet(a);
                    if (user != null) {
                        String relativePath = helpers.generateRelativePathForObject("users", username, "jpg");
                        relativePath = helpers.replaceWithForwardSlash(relativePath);
//                        System.out.println("Relative path: " + relativePath);
//                        System.out.println("Path to save: " + path);
                        boolean result = new AppUserDao().update(user, new String[]{String.valueOf(userId), username, email, password, relativePath});
                        if (result && img.getSize() > 0) {
                            String path = helpers.getNewFileLocation(baseDir, "users", username, "jpg");
                            System.out.println(img.getSubmittedFileName());
                            System.out.println("[User Servlet] : Changing user img");
                            helpers.buildDirectory(path);
                            img.write(path);
                        }
                        request.getSession().setAttribute("user", user);
                    }
                    // TOOD: Toast notification ?
                    request.setAttribute("userStatus", true);
                    request.getRequestDispatcher("settings").forward(request, response);
                }

                case "delete" -> {
                    Integer userId = Integer.valueOf(request.getParameter("uId"));
                    System.out.println("[USER] Delete :: " + userId);
                    boolean r = new AppUserDao().delete(userId);
                    if (r) {
                        response.sendRedirect("settings");
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }

                }

                case "promote" -> {
                    Integer userId = Integer.valueOf(request.getParameter("uId"));
                    System.out.println("[USER] Promote :: " + userId);
                    boolean r = new AppUserDao().promoteUser(userId);
                    if (r) {
                        response.sendRedirect("settings");
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                }

                default -> {
                    System.out.println("Unknown action: " + action);
                    throw new AssertionError();
                }
            }
        } catch (Exception e) {
            System.out.println("[UserServlet] ERROR :: " + e.getMessage());
        }

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
