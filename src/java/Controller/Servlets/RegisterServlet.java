package Controller.Servlets;

import Model.AppUser;
import Model.Daos.AppUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RegisterServlet: Entering doPost method");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        boolean isAdmin = false;

        AppUser user = new AppUser(username, email, password, isAdmin);
        AppUserDao userDao = new AppUserDao();

        try {
            System.out.println("RegisterServlet: Attempting to register user");
            userDao.registerUser(user);
            System.out.println("RegisterServlet: User registered successfully");
            response.sendRedirect(request.getContextPath());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("RegisterServlet: Exception occurred - " + e.getMessage());
            response.sendRedirect("login.jsp?status=fail");
        }

        System.out.println("RegisterServlet: Exiting doPost method");
    }

}
