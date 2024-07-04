package Model;

public class Song {

    private int songId;
    private int duration;
    private int artistId;
    private String title;
    private String songFilePath;
    private String songImagePath;
    private String album;

    // Constructor
    public Song(int songId, int duration, int artistId, String title, String songFilePath, String songImagePath, String album) {
        this.songId = songId;
        this.duration = duration;
        this.artistId = artistId;
        this.title = title;
        this.songFilePath = songFilePath;
        this.songImagePath = songImagePath;
        this.album = album;
    }

    // Getters and setters
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSongFilePath() {
        return songFilePath;
    }

    public void setSongFilePath(String songFilePath) {
        this.songFilePath = songFilePath;
    }

    public String getSongImagePath() {
        return songImagePath;
    }

    public void setSongImagePath(String songImagePath) {
        this.songImagePath = songImagePath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
