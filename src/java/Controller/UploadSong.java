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

/**
 *
 * @author lethimcook
 */
public class UploadSong extends HttpServlet {
    
    private DatabaseInformation db;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        db = new DatabaseInformation("127.0.0.1", "AppMusicDatabase");
        
        String artistName = request.getParameter("artistName");
        String songTitle = request.getParameter("songTitle");
        String filePath = request.getParameter("filePath");
        int duration = Integer.parseInt(request.getParameter("duration"));
        String genresString = request.getParameter("genres");
        
    }
    
}
