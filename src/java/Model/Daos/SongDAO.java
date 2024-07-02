package Model.Daos;

import Database.DatabaseInformation;
import Model.Daos.Dao;
import Model.Song;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class SongDAO implements Dao<Song> {

    private DatabaseInformation db = new DatabaseInformation();

    @Override
    public Optional<Song> get(int id) {
        Song song = null;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement("""
                                                               SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songImagePath, s.songFilePath
                                                                FROM Song s
                                                                INNER JOIN Artist a
                                                                ON s.artistId = a.artistId
                                                               WHERE s.songId = ?""")) {
                stmt.setLong(1, id);
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
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(song);
    }

    /**
     * Return an Arraylist of Song, null if empty
     *
     * @return
     */
    @Override
    public ArrayList<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        try (Connection con = db.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("""
                                                                                                                           SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songImagePath, s.songFilePath
                                                                                                                           FROM Song s
                                                                                                                           INNER JOIN Artist a
                                                                                                                           ON s.artistId = a.artistId""")) {
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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (songs.isEmpty()) {
            return null;
        }
        return songs;
    }

    
    @Override
    public boolean insert(Song song) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Song (artistId, duration, title, filePath) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
//                TODO: This one will actually have artist name instead, so handle it in SQL
//                stmt.setInt(1, song.getArtistId());
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
//                TODO: Fix me because now, song only contains artist name, not artist ID
//                stmt.setInt(2, song.getArtistId());
                stmt.setInt(3, song.getDurationInNumbers());
                stmt.setInt(5, song.getSongId());
                result = stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(int songId) {
        boolean result = false;
        try (Connection con = db.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM Song WHERE songId = ?")) {
//                stmt.setInt(1, song.getSongId());
                result = stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
