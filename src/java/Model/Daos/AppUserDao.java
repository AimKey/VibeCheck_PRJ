package Model.Daos;

import Database.DatabaseInformation;
import Model.AppUser;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.logging.Level;
import java.util.logging.Logger;

// @author phamm
public class AppUserDao implements Dao {

    DatabaseInformation db = new DatabaseInformation();

    @Override
    public Optional get(int id) {
        AppUser a = new AppUser();
        try (Connection con = db.getConnection();) {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM AppUser a WHERE a.userId = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String dateJoined = DateTimeFormatter.ofPattern("yyyy-MM-dd").format((TemporalAccessor) rs.getDate("dateJoined"));
                String profilePicPath = rs.getString("profilePicPath");
                Boolean isAdmin = rs.getBoolean("isAdmin");
                a = new AppUser(id, name, email, password, profilePicPath, dateJoined, isAdmin);
            }

            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(a);
    }

    @Override
    public ArrayList getAll() {
        ArrayList<AppUser> list = new ArrayList<>();
        try (Connection con = db.getConnection();) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AppUser a");
            while (rs.next()) {
                Integer id = rs.getInt("userId");
                String name = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String dateJoined = DateTimeFormatter.ofPattern("yyyy-MM-dd").format((rs.getDate("dateJoined").toLocalDate()));
                String proflePicPath = rs.getString("profilePicPath");
                Boolean isAdmin = rs.getBoolean("isAdmin");
                AppUser a = new AppUser(id, name, email, password, proflePicPath, dateJoined, isAdmin);
                list.add(a);
            }

            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Object t, String[] params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void registerUser(AppUser user) throws ClassNotFoundException, SQLException, ServletException, IOException {
        System.out.println("registerUser: Entering method");

        String query = "INSERT INTO AppUser(username, password, email, isAdmin) OUTPUT inserted.userId VALUES(?,?,?,?)";

        try (Connection con = db.getConnection(); PreparedStatement statement = con.prepareStatement(query)) {

            System.out.println("registerUser: Connection established");

            statement.setString(1, user.getUsername());
            System.out.println("registerUser: Set username = " + user.getUsername());

            statement.setString(2, user.getPassword());
            System.out.println("registerUser: Set password = " + user.getPassword());

            statement.setString(3, user.getEmail());
            System.out.println("registerUser: Set email = " + user.getEmail());

            statement.setBoolean(4, user.getIsAdmin());
            try (ResultSet rs = statement.executeQuery()) {
                System.out.println("registerUser: Executed query");

                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("registerUser: Inserted user ID = " + id);
                } else {
                    System.out.println("registerUser: No ID returned");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppUserDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("registerUser: SQLException - " + ex.getMessage());
            throw ex; // Re-throw the exception if needed
        }
        System.out.println("registerUser: Exiting method");
    }
}
