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

public class SongDao implements Dao<Song> {

    private DatabaseInformation db = new DatabaseInformation();
    private static final int DEFAULT_ARTIST_ID = 1;
    private static final String DEFAULT_SONG_IMAGE_PATH = "songs/fallback.jpg";
    private static final String DEFAULT_ARTIST_IMAGE_PATH = "artists/fallback.jpg";

    @Override
    public Optional<Song> get(int id) {
        Song song = null;
        String query = "SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songImagePath, s.songFilePath "
                + "FROM Song s "
                + "INNER JOIN Artist a ON s.artistId = a.artistId "
                + "WHERE s.songId = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
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
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.ofNullable(song);
    }

    @Override
    public ArrayList<Song> getAll() {
        ArrayList<Song> songs = new ArrayList<>();
        String query = "SELECT s.songId, s.title, a.artistName, s.album, s.duration, s.songImagePath, s.songFilePath "
                + "FROM Song s "
                + "INNER JOIN Artist a ON s.artistId = a.artistId";
        try (Connection con = db.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
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
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songs.isEmpty() ? null : songs;
    }

    @Override
    public boolean insert(Song t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * This one is different because it is returning messages for others to hanlde
     *
     * @param song
     * @return
     * @throws java.lang.Exception
     */
    public String insertWithMsg(Song song) throws Exception {
        String title = song.getTitle();
        String artistName = song.getArtist();
        String album = song.getAlbum();
        String durationStr = song.getDuration();
        String filePath = song.getSongFilePath();
        String coverPictureLink = song.getSongImagePath();
        
        System.out.println("Inserting: " + song);

        // Print received parameters for debugging
//        System.out.println("Title: " + title);
//        System.out.println("Artist: " + artistName);
//        System.out.println("Duration: " + durationStr);
//        System.out.println("File Path: " + filePath);
//        System.out.println("Cover Picture Link: " + coverPictureLink);

        // Validate parameters
        if (title == null || title.isEmpty()
                || artistName == null || artistName.isEmpty()
                || durationStr == null || durationStr.isEmpty()
                || filePath == null || filePath.isEmpty()
                || coverPictureLink == null || coverPictureLink.isEmpty()) {
            System.out.println("Validation failed: one or more parameters are empty");
            throw new Exception ("Validation failed: one or more parameters are empty");
        }

        int duration;
        try {
            // Parse duration from MM:SS format to seconds
            String[] durationParts = durationStr.split(":");
            int minutes = Integer.parseInt(durationParts[0]);
            int seconds = Integer.parseInt(durationParts[1]);
            duration = (minutes * 60) + seconds;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Failed to parse duration: " + e.getMessage());
            // No redirect
            throw new Exception ("An error occured!: " + e.getMessage());
        }

        try (Connection con = db.getConnection()) {
            // Check if artist exists
            int artistId = DEFAULT_ARTIST_ID;
            PreparedStatement stmt = con.prepareStatement("SELECT artistId, profilePicturePath FROM Artist WHERE artistName = ?");
            stmt.setString(1, artistName);
            ResultSet rs = stmt.executeQuery();
            String artistImagePath = DEFAULT_ARTIST_IMAGE_PATH; // TODO: Handle artist img in the future ?
            if (rs.next()) {
                artistId = rs.getInt("artistId");
                artistImagePath = rs.getString("profilePicturePath");
                if (artistImagePath == null || artistImagePath.isEmpty()) {
                    artistImagePath = DEFAULT_ARTIST_IMAGE_PATH;
                }
                System.out.println("Artist found: " + artistName + " with ID " + artistId);
            } else {
                // Insert new artist
                stmt = con.prepareStatement("INSERT INTO Artist (artistName, profilePicturePath) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, artistName);
                stmt.setString(2, DEFAULT_ARTIST_IMAGE_PATH);
                stmt.executeUpdate();
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    artistId = rs.getInt(1);
//                    return ("New artist inserted: " + artistName + " with ID " + artistId);
                }
            }

            // Check if song exists
            stmt = con.prepareStatement("SELECT songId FROM Song WHERE title = ? AND artistId = ?");
            stmt.setString(1, title);
            stmt.setInt(2, artistId);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                // Insert new song
                stmt = con.prepareStatement("INSERT INTO Song (artistId, title, duration, songFilePath, songImagePath, album) VALUES (?, ?, ?, ?, ?, ?)");
                stmt.setInt(1, artistId);
                stmt.setString(2, title);
                stmt.setInt(3, duration);
                stmt.setString(4, filePath);
                stmt.setString(5, (coverPictureLink == null || coverPictureLink.isEmpty()) ? DEFAULT_SONG_IMAGE_PATH : coverPictureLink);
                stmt.setString(6, album); // Album can be null
                stmt.executeUpdate();
                System.out.println("New song inserted: " + title + " by " + artistName);
            } else {
                // Throws for others to receive
                throw new Exception("Song already exists: " + title + " by " + artistName + ", please try again!");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            throw new Exception ("Database error: " + e.getMessage());
        }
        return title + "inserted!";
    }

    @Override
    public boolean update(Song song, String[] params) {
        boolean result = false;
        String query = "UPDATE Song SET title = ?, artistId = ?, duration = ?, songFilePath = ?, songImagePath = ?, album = ? "
                + "WHERE songId = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
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
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean delete(int songId) {
        boolean result = false;
        String query = "DELETE FROM Song WHERE songId = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, songId);
            result = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting song with ID " + songId + ": " + e.getMessage());
        } catch (ServletException ex) {
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private int getArtistIdByName(String artistName) throws SQLException {
        String query = "SELECT artistId FROM Artist WHERE artistName = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, artistName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("artistId");
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SongDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new SQLException("Artist not found with name: " + artistName);
    }
}
