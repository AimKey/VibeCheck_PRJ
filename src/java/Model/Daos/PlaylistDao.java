package Model.Daos;

import Database.DatabaseInformation;
import Model.Playlist;
import java.util.ArrayList;
import java.util.Optional;
import java.sql.*;
import java.time.LocalDate;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
        try (Connection con = new DatabaseInformation().getConnection()) {
            PreparedStatement stmt = con.prepareStatement("""
                                                          DELETE FROM Playlist WHERE playlistId = ?""");
            stmt.setInt(1, id);

            int rowsInserted = stmt.executeUpdate();
            stmt.close();

            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(Playlist t) {
        try (Connection con = new DatabaseInformation().getConnection()) {
            PreparedStatement stmt = con.prepareStatement("""
                                                          INSERT INTO Playlist (userId, name, numberOfSongs, creationDate)
                                                          VALUES (?, ?, ?, ?)""");
            stmt.setInt(1, t.getUserId());
            stmt.setString(2, t.getName());
            stmt.setInt(3, t.getNumberOfSongs());
            stmt.setDate(4, Date.valueOf(t.getCreationDate()));

            int rowsInserted = stmt.executeUpdate();
            stmt.close();

            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * With our current playlist, it is sad to say that only name is changable <br>
     * Params should follow this order: name
     * @param t
     * @param params
     * @return 
     */
    @Override
    public boolean update(Playlist t, String[] params) {
        try (Connection con = new DatabaseInformation().getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE Playlist SET name = ? WHERE playlistId = ?");
            stmt.setString(1, params[0]);
            stmt.setInt(2, t.getPlaylistId());

            int rowsInserted = stmt.executeUpdate();
            stmt.close();

            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
