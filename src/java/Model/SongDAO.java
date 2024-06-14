package Model;

import Misc.DatabaseInformation;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class SongDAO implements Dao<Song> {

    private DatabaseInformation db = new DatabaseInformation("KUUL", "TestingUploadFiles");

    @Override
    public Optional<Song> get(long id) {
        Song song = null;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM Song WHERE songId = ?")) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        song = new Song(
                                rs.getInt("songId"),
                                rs.getInt("artistId"),
                                rs.getInt("duration"),
                                rs.getString("title"),
                                rs.getString("filePath"),
                                new ArrayList<>() // Placeholder for genres
                        );
                    }
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
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Song")) {
            while (rs.next()) {
                Song song = new Song(
                        rs.getInt("songId"),
                        rs.getInt("artistId"),
                        rs.getInt("duration"),
                        rs.getString("title"),
                        rs.getString("filePath"),
                        new ArrayList<>() // Placeholder for genres
                );
                songs.add(song);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return songs;
    }

    @Override
    public boolean save(Song song) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Song (artistId, duration, title, filePath) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, song.getArtistId());
                stmt.setInt(2, song.getDuration());
                stmt.setString(3, song.getTittle());
                stmt.setString(4, song.getFilePath());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            song.setSongId(generatedKeys.getInt(1));
                            result = true;
                        }
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
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "UPDATE Song SET title = ?, artistId = ?, duration = ?, filePath = ? WHERE songId = ?")) {
                stmt.setString(1, params[1]);
                stmt.setInt(2, song.getArtistId());
                stmt.setInt(3, song.getDuration());
                stmt.setString(4, song.getFilePath());
                stmt.setInt(5, song.getSongId());
                result = stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(Song song) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM Song WHERE songId = ?")) {
                stmt.setInt(1, song.getSongId());
                result = stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
