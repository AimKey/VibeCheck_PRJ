/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Servlets;

import Model.AppUser;
import Model.Daos.PlaylistDao;
import Model.Daos.SongDao;
import Model.Playlist;
import Model.Song;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class Main extends HttpServlet {

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
        Object o = request.getAttribute("curPlaylist");
        Integer curPlaylistId = null;
        if (o != null) {
            curPlaylistId = (Integer) o;
        }
        o = request.getSession().getAttribute("user");
        if (o == null) response.sendRedirect("login");
        AppUser a = (AppUser) o;
        // Set attribute
        ArrayList<Song> abc = new SongDao().getAll();
        ArrayList<Playlist> pls = new PlaylistDao().getAllPlaylistFromUser(a.getUserId());
        if (curPlaylistId != null) {
            // Set to the session so that we wil come back to it or use cookie later
            request.getSession().setAttribute("curPlaylist", new PlaylistDao().get(curPlaylistId));
        } else {
            // No playlist, default to the system
            Playlist system = new Playlist(0, 0, "System", abc.size());
            request.getSession().setAttribute("curPlaylist", system);
        }

        request.setAttribute("songs", abc);
        request.setAttribute("playlists", pls);
        request.setAttribute("systemSongsCount", abc.size());
        request.getRequestDispatcher("Pages/Common/main.jsp").forward(request, response);
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
