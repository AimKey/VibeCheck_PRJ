/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Servlets;

import Model.AppUser;
import Model.Daos.AppUserDao;
import Model.Daos.PlaylistDao;
import Model.Daos.SongDao;
import Model.Playlist;
import Model.Song;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.function.Supplier;

/**
 *
 * @author phamm
 */
public class Settings extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This servlet only job is to prepare stuff for display (Replace the old setting page on load filter)
        System.out.println("[Setting Page]: Session: " + request.getSession().getId());
        AppUser user = null;
        // Check if user is changed
        if (request.getAttribute("userStatus") != null) {
            boolean status = (Boolean) request.getAttribute("userStatus");
            System.out.println("Status: " + status);
            if (status) {
                System.out.println("[Setting Page] :: User changed!");
                AppUser old = (AppUser) request.getSession().getAttribute("user");
                System.out.println("Old appuser: " + old);
                Supplier<AppUser> sup = () -> null;
                user = new AppUserDao().get(old.getUserId()).orElseGet(sup);
                System.out.println("New AppUser: " + user);
                request.getSession().setAttribute("user", user);
            }
        } else {
            user = (AppUser) request.getSession().getAttribute("user");
        }
        
        AppUserDao dao = new AppUserDao();
        ArrayList<AppUser> appUsers = dao.getAll();
        ArrayList<Song> songs = new SongDao().getAll();

        if (user != null) {
//            System.out.println(user + ":: accessing admin page");
            ArrayList<Playlist> playlists = new PlaylistDao().getAllPlaylistFromUser(user.getUserId());
            request.setAttribute("users", appUsers);
            request.setAttribute("songs", songs);
            request.setAttribute("playlists", playlists);
            
            request.getRequestDispatcher("Pages/Common/settings.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
