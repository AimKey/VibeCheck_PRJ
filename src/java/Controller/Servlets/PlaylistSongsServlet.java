/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Servlets;

import Model.Daos.PlaylistSongsDao;
import Model.Song;
import Utils.JSONWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author phamm
 */
public class PlaylistSongsServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String param = request.getParameter("playlistId");
        Integer playlistId = -1;
        if (param != null) {
            playlistId = Integer.valueOf(param);
        }
        switch (action) {
            case "get" -> {
                ArrayList<Song> songs = new PlaylistSongsDao().getAllSongsByPlaylistId(playlistId);
                response.setContentType("application/json;charset=UTF-8");

                String json1 = new JSONWriter<ArrayList<Song>>().getJSONString(songs);
                System.out.println("[PlaylistSongs JSON]:: " + json1);
                response.getWriter().write(json1);
            }
            case "getUnique" -> {
                ArrayList<Song> songs = new PlaylistSongsDao().getUniqueSongs(playlistId);
                response.setContentType("application/json;charset=UTF-8");

                String json1 = new JSONWriter<ArrayList<Song>>().getJSONString(songs);
                System.out.println("[PlaylistSongs JSON]:: Get unique songs: " + json1);
                response.getWriter().write(json1);
            }
            default -> {
                System.out.println("Something went wrong! (PlaylistSongs JSON)");
                throw new AssertionError();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String param = request.getParameter("playlistId");
        Integer playlistId = -1;
        if (param != null) {
            playlistId = Integer.valueOf(param);
        }
        switch (action) {
            case "insertSongs" -> {
                System.out.println("Received insert action!");
                String songIds = request.getParameter("songIds");
                if (songIds != null) {
                    String[] songIdSplited = songIds.split(",");
                    for (String string : songIdSplited) {
                        System.out.println(string);
                    }
                }
                System.out.println("[PlaylistSongs] :: TODO: Handle add songs to a playlist");
                response.sendRedirect("settings");
            }

            default -> {
                System.out.println("Something went wrong! (PlaylistSongs)");
                throw new AssertionError();
            }
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
