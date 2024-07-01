package Model.Daos;

import Database.DatabaseInformation;
import Model.Playlist;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;
import java.time.LocalDate;

// @author phamm
public class PlaylistDao implements Dao<Playlist> {

    @Override
    public Optional<Playlist> get(int id) {
        Playlist p = null;
        try (Connection con = new DatabaseInformation().getConnection()) {
            PreparedStatement stmt = con.prepareStatement("""
                                                          SELECT p.playlistId, p.name, p.numberOfSongs, p.creationDate
                                                          FROM Playlist p
                                                          WHERE p.playlistId = ?""");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int playlistId = rs.getInt("playlistId");
                String name = rs.getString("name");
                int numberOfSongs = rs.getInt("numberOfSongs");
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                p = new Playlist(playlistId, id, name, numberOfSongs, creationDate);
            }

            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(p);
    }

    @Override
    public ArrayList getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * This one will get all playlist from a user instead
     *
     * @param userId
     * @return
     */
    public ArrayList<Playlist> getAllPlaylistFromUser(int userId) {
        ArrayList<Playlist> list = new ArrayList<>();
        try (Connection con = new DatabaseInformation().getConnection()) {
            PreparedStatement stmt = con.prepareStatement("""
                                                          SELECT p.playlistId, p.name, p.numberOfSongs, p.creationDate
                                                          FROM Playlist p
                                                          WHERE p.userId = ?""");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int playlistId = rs.getInt("playlistId");
                String name = rs.getString("name");
                int numberOfSongs = rs.getInt("numberOfSongs");
                LocalDate creationDate = rs.getDate("creationDate").toLocalDate();
                list.add(new Playlist(playlistId, userId, name, numberOfSongs, creationDate));
            }

            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean insert(Playlist t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean update(Playlist t, String[] params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
