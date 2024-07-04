package Model;

import Database.DatabaseInformation;
import Model.Daos.Dao;
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
        try ( Connection con = db.getConnection();  PreparedStatement stmt = con.prepareStatement(
                "SELECT s.songId, s.title, s.artistId, s.album, s.duration, s.songImagePath, s.songFilePath "
                + "FROM Song s "
                + "WHERE s.songId = ?")) {
            stmt.setInt(1, id);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int songId = rs.getInt("songId");
                    String title = rs.getString("title");
                    int artistId = rs.getInt("artistId");
                    String album = rs.getString("album");
                    int duration = rs.getInt("duration");
                    String songImagePath = rs.getString("songImagePath");
                    String songFilePath = rs.getString("songFilePath");

                    song = new Song(songId, duration, artistId, title, songFilePath, songImagePath, album);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(song);
    }

    @Override
    public ArrayList<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        try ( Connection con = db.getConnection();  Statement stmt = con.createStatement();  ResultSet rs = stmt.executeQuery(
                "SELECT s.songId, s.title, s.artistId, s.album, s.duration, s.songImagePath, s.songFilePath, "
                + "a.artistId, a.artistName, a.bio, a.profilePicPath "
                + "FROM Song s "
                + "INNER JOIN Artist a ON s.artistId = a.artistId")) {
            while (rs.next()) {
                int songId = rs.getInt("songId");
                String title = rs.getString("title");
                int artistId = rs.getInt("artistId");
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String songFilePath = rs.getString("songFilePath");
                String songImagePath = rs.getString("songImagePath");
                String artistName = rs.getString("artistName");
                String bio = rs.getString("bio");
                String profilePicPath = rs.getString("profilePicPath");

                Artist artist = new Artist(artistId, artistName, bio, profilePicPath);
                songs.add(new Song(songId, duration, artistId, title, songFilePath, songImagePath, album));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return songs.isEmpty() ? null : songs;
    }

    @Override
    public boolean insert(Song song) {
        boolean result = false;
        try ( Connection con = db.getConnection();  PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO Song (artistId, duration, title, songFilePath, songImagePath, album) VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, song.getArtistId());
            stmt.setInt(2, song.getDuration());
            stmt.setString(3, song.getTitle());
            stmt.setString(4, song.getSongFilePath());
            stmt.setString(5, song.getSongImagePath());
            stmt.setString(6, song.getAlbum());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try ( ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        song.setSongId(generatedKeys.getInt(1));
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean update(Song song, String[] params) {
        boolean result = false;
        try ( Connection con = db.getConnection();  PreparedStatement stmt = con.prepareStatement(
                "UPDATE Song SET title = ?, artistId = ?, duration = ?, songFilePath = ?, songImagePath = ?, album = ? WHERE songId = ?")) {
            stmt.setString(1, params[1]);
            stmt.setInt(2, song.getArtistId());
            stmt.setInt(3, song.getDuration());
            stmt.setString(4, song.getSongFilePath());
            stmt.setString(5, song.getSongImagePath());
            stmt.setString(6, song.getAlbum());
            stmt.setInt(7, song.getSongId());
            result = stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(int songId) {
        boolean result = false;
        try ( Connection con = db.getConnection()) {
            con.setAutoCommit(false);  // Start transaction

            // Delete song from PlaylistSongs
            try ( PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM PlaylistSongs WHERE songId = ?")) {
                stmt.setInt(1, songId);
                stmt.executeUpdate();
            }

            // Delete song from Song table
            try ( PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM Song WHERE songId = ?")) {
                stmt.setInt(1, songId);
                result = stmt.executeUpdate() > 0;
            }

            con.commit();  // Commit transaction
        } catch (Exception e) {
            System.err.println(e.getMessage());
            try ( Connection con = db.getConnection()) {
                con.rollback();  // Rollback transaction in case of error
            } catch (SQLException rollbackEx) {
                System.err.println(rollbackEx.getMessage());
            } catch (ServletException | IOException ex) {
                Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

}
