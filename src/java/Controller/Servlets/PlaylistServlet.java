/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Servlets;

import Model.Daos.PlaylistDao;
import Model.Playlist;
import Utils.JSONWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 *
 * @author phamm
 */
public class PlaylistServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * This servlet is meant to return JSON ! This function will filter out the
     * GET to dermine if: GET ALL, GET BY ID
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String param = request.getParameter("action");
        switch (param) {
            case "getJSON" -> {
                String result = null;
                // Get ALL playlist
                if (param.equals("all")) {
                    System.out.println("TODO: Handle get ALL playlist");
                } else if (param.equals("get")) {
                    Integer id = Integer.valueOf(request.getParameter("id"));
                    // Get playlist by id
                    Optional<Playlist> optional = new PlaylistDao().get(id);
                    if (optional.isPresent()) {
                        Playlist p = optional.get();
                        result = new JSONWriter<Playlist>().getJSONString(p);
                    }
                }
                if (result != null) {
                    response.getWriter().write(result);
                }
            }
            default -> {
                System.out.println("Action: " + param);
                throw new AssertionError();
            }
        }

    }

    /**
     * Handle delete action and update action by user
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String param = request.getParameter("action");
        switch (param) {
            case "updatePName" -> {
                String pName = request.getParameter("pName");
                Integer pId = Integer.valueOf(request.getParameter("pId"));
                System.out.println("[Playlist] :: Handle update playlist name: " + pName + ", pId: " + pId);

                response.sendRedirect("settings");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
