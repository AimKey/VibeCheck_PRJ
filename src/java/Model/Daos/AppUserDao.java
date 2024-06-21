package Model.Daos;

import Database.DatabaseInformation;
import Model.AppUser;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

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
                String proflePicPath = rs.getString("profilePicPath");
                Boolean isAdmin = rs.getBoolean("isAdmin");
                a = new AppUser(id, name, email, password, proflePicPath, dateJoined, isAdmin);
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
                String dateJoined = DateTimeFormatter.ofPattern("yyyy-MM-dd").format((TemporalAccessor) rs.getDate("dateJoined"));
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

}
