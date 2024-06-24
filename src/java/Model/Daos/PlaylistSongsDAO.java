package Model.Daos;

import Database.DatabaseInformation;
import Model.PlaylistSongs;
import Model.Song;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class PlaylistSongsDao implements Dao<PlaylistSongs> {

    private DatabaseInformation db = new DatabaseInformation();

    @Override
    public Optional<PlaylistSongs> get(int id) {
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

    /**
     * This one is a little bit special, we will get all Song that belong to a
     * playlist instead, because get all song from playlistSongs has no value.
     *
     * @param playlistId
     * @return
     */
    public ArrayList<Song> getAllSongFromPlayListSongs(int playlistId) {
        ArrayList<Song> list = new ArrayList<>();
        try (Connection con = db.getConnection()) {

            PreparedStatement stmt = con.prepareStatement("""
                                                          SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songFilePath, s.songImagePath
                                                          FROM PlaylistSongs pl
                                                          INNER JOIN Song s
                                                          ON s.songId = pl.songId
                                                          INNER JOIN Artist a
                                                          ON a.artistId = s.artistId
                                                          WHERE pl.playlistId = ?""");
            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int songId = rs.getInt("songId");
                String title = rs.getString("title");
                String artistName = rs.getString("artistName");
                String album = rs.getString("album");
                int duration = rs.getInt("duration");
                String songFilePath = rs.getString("songFilePath");
                String songImagePath = rs.getString("songImagePath");
                list.add(new Song(songId, duration, artistName, title, songFilePath, songImagePath, album));
            }

            stmt.close();
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insert(PlaylistSongs playlistSongs) {
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
    public ArrayList<PlaylistSongs> getAll() {
        throw new UnsupportedOperationException("Why would you want to call getAll on PlaylistSongs."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
