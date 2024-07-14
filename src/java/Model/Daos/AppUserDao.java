package Model.Daos;

import Database.DatabaseInformation;
import Model.AppUser;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

// @author phamm
public class AppUserDao implements Dao<AppUser> {

    DatabaseInformation db = new DatabaseInformation();

    @Override
    public Optional<AppUser> get(int id) {
        AppUser a = new AppUser();
        try (Connection con = db.getConnection();) {

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM AppUser a WHERE a.userId = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Date date = rs.getDate("dateJoined");
                String dateJoined = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date.toLocalDate());
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
    public ArrayList<AppUser> getAll() {
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
    public boolean delete(int id) {
        try (Connection con = db.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM AppUser WHERE userId = ?");
            stmt.setInt(1, id);
            int row = stmt.executeUpdate();

            stmt.close();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean promoteUser(int id) {
        try (Connection con = db.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE AppUser SET isAdmin = 1 WHERE userId = ?");
            stmt.setInt(1, id);
            int row = stmt.executeUpdate();
            
            stmt.close();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(AppUser t) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * This is normal update (Not allow to update admin), dateJoined is not
     * update-able <br>
     * Params should follow this: <br>
     * userId, username, email ,password, profilePicPath
     *
     * @param t
     * @param params
     * @return
     */
    @Override
    public boolean update(AppUser t, String[] params) {
        boolean r = false;
        try (Connection con = db.getConnection();) {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE AppUser SET username = ?, email = ?, password = ?, profilePicPath = ? WHERE userId = ?");
            stmt.setString(1, params[1].isEmpty() ? t.getUsername() : params[1]);
            stmt.setString(2, params[2].isEmpty() ? t.getEmail() : params[2]);
            stmt.setString(3, params[3].isEmpty() ? t.getPassword() : params[3]);
            stmt.setString(4, params[4].isEmpty() ? t.getProfilePicPath() : params[4]);
            stmt.setInt(5, Integer.parseInt(params[0]));
            int status = stmt.executeUpdate();
            if (status > 0) {
                r = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

}
