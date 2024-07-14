package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author phamm
 */
public class Playlist {

    private int playlistId, userId;
    private String name;
    private int numberOfSongs;
    private LocalDate creationDate;

    public Playlist(String name, int numberOfSongs) {
        this.name = name;
        this.numberOfSongs = numberOfSongs;
    }

    public Playlist(int playlistId, int userId, String name, int numberOfSongs) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
        this.numberOfSongs = numberOfSongs;
    }
    
    public Playlist(String name, int numSong, String creationDate) {
        this.name = name;
        this.numberOfSongs = numSong;
        this.creationDate = LocalDate.parse(creationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Playlist(int playlistId, int userId, String name, int numSong, String creationDate) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
        this.numberOfSongs = numSong;
        this.creationDate = LocalDate.parse(creationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Playlist(int userId, String name, int numberOfSongs, LocalDate creationDate) {
        this.userId = userId;
        this.name = name;
        this.numberOfSongs = numberOfSongs;
        this.creationDate = creationDate;
    }
    
    public Playlist(int playlistId, int userId, String name, int numberOfSongs, LocalDate creationDate) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
        this.numberOfSongs = numberOfSongs;
        this.creationDate = creationDate;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    public String getCreationDate() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(creationDate);
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Playlist{" + "playlistId=" + playlistId + ", userId=" + userId
                + ", name=" + name + ", numSong=" + numberOfSongs + ", creationDate=" + getCreationDate() + '}';
    }

}
