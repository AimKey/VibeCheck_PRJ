package Model;

import Database.DatabaseInformation;
import Model.Daos.Dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class PlaylistSongsDAO implements Dao<PlaylistSongs> {

    private DatabaseInformation db = new DatabaseInformation();

    @Override
    public Optional<PlaylistSongs> get(long id) {
        PlaylistSongs playlistSongs = null;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM PlaylistSongs WHERE playlistId = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        playlistSongs = new PlaylistSongs(rs.getInt("playlistId"), rs.getInt("songId"));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(playlistSongs);
    }

    @Override
    public ArrayList<PlaylistSongs> getAll() {
        ArrayList<PlaylistSongs> playlistSongsList = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PlaylistSongs")) {
            while (rs.next()) {
                PlaylistSongs playlistSongs = new PlaylistSongs(rs.getInt("playlistId"), rs.getInt("songId"));
                playlistSongsList.add(playlistSongs);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return playlistSongsList;
    }

    public boolean save(PlaylistSongs playlistSongs) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO PlaylistSongs (playlistId, songId) VALUES (?, ?)")) {
                stmt.setInt(1, playlistSongs.getPlaylistId());
                stmt.setInt(2, playlistSongs.getSongId());
                result = stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean update(PlaylistSongs playlistSongs, String[] params) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "UPDATE PlaylistSongs SET songId = ? WHERE playlistId = ?")) {
                stmt.setInt(1, Integer.parseInt(params[1]));
                stmt.setInt(2, Integer.parseInt(params[0]));
                result = stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public boolean delete(PlaylistSongs playlistSongs) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM PlaylistSongs WHERE playlistId = ? AND songId = ?")) {
                stmt.setInt(1, playlistSongs.getPlaylistId());
                stmt.setInt(2, playlistSongs.getSongId());
                result = stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean insert(PlaylistSongs t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
