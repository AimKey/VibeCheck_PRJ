package Model.Daos;

import Database.DatabaseInformation;
import Model.Song;
import jakarta.servlet.ServletException;
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SongDAO implements Dao<Song> {

    private DatabaseInformation db = new DatabaseInformation();

    @Override
    public Optional<Song> get(int id) {
        Song song = null;
        String query = "SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songImagePath, s.songFilePath " +
                       "FROM Song s " +
                       "INNER JOIN Artist a ON s.artistId = a.artistId " +
                       "WHERE s.songId = ?";
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int songId = rs.getInt("songId");
                    String title = rs.getString("title");
                    String artistName = rs.getString("artistName");
                    String album = rs.getString("album");
                    int duration = rs.getInt("duration");
                    String songImagePath = rs.getString("songImagePath");
                    String songFilePath = rs.getString("songFilePath");
                    song = new Song(songId, duration, artistName, title, songFilePath, songImagePath, album);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving song with ID " + id + ": " + e.getMessage());
        } catch (ServletException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.ofNullable(song);
    }

    @Override
    public ArrayList<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        String query = "SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songImagePath, s.songFilePath " +
                       "FROM Song s " +
                       "INNER JOIN Artist a ON s.artistId = a.artistId";
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("songId");
                String title = rs.getString("title");
                String artist = rs.getString("artistName");
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String songFilePath = rs.getString("songFilePath");
                String songImagePath = rs.getString("songImagePath");
                songs.add(new Song(id, duration, artist, title, songFilePath, songImagePath, album));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all songs: " + e.getMessage());
        } catch (ServletException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songs.isEmpty() ? null : songs;
    }

    @Override
    public boolean insert(Song song) {
        boolean result = false;
        String query = "INSERT INTO Song (artistId, duration, title, songFilePath, songImagePath, album) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, getArtistIdByName(song.getArtist()));
            stmt.setInt(2, song.getDurationInNumbers());
            stmt.setString(3, song.getTitle());
            stmt.setString(4, song.getSongFilePath());
            stmt.setString(5, song.getSongImagePath());
            stmt.setString(6, song.getAlbum());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        song.setSongId(generatedKeys.getInt(1));
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting song: " + e.getMessage());
        } catch (ServletException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean update(Song song, String[] params) {
        boolean result = false;
        String query = "UPDATE Song SET title = ?, artistId = ?, duration = ?, songFilePath = ?, songImagePath = ?, album = ? " +
                       "WHERE songId = ?";
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, params[0]); // Assuming params[0] is title
            stmt.setInt(2, getArtistIdByName(song.getArtist()));
            stmt.setInt(3, song.getDurationInNumbers());
            stmt.setString(4, song.getSongFilePath());
            stmt.setString(5, song.getSongImagePath());
            stmt.setString(6, song.getAlbum());
            stmt.setInt(7, song.getSongId());
            result = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating song with ID " + song.getSongId() + ": " + e.getMessage());
        } catch (ServletException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean delete(int songId) {
        boolean result = false;
        String query = "DELETE FROM Song WHERE songId = ?";
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, songId);
            result = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting song with ID " + songId + ": " + e.getMessage());
        } catch (ServletException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private int getArtistIdByName(String artistName) throws SQLException {
        String query = "SELECT artistId FROM Artist WHERE artistName = ?";
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, artistName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("artistId");
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new SQLException("Artist not found with name: " + artistName);
    }
}
