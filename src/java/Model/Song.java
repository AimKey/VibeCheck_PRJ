/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author phamm
 */
public class Song {

    private int songId, duration;
    // In the model, the song has string of artst, but in DB, it should contain artist
    // id only
    private String artist;
    // Song filePath is songs/sth/sth.mp3
    // Song img path is songs/sth/sth.jpeg;
    private String title, songFilePath, songImagePath;
    private String album;
    
    public Song() {}

    public Song(int songId, int duration, String artist, String title, String songFilePath, String songImagePath, String album) {
        this.songId = songId;
        this.duration = duration;
        this.artist = artist;
        this.title = title;
        this.songFilePath = songFilePath;
        this.songImagePath = songImagePath;
        this.album = album;
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

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
//    Return duration in string format
    public String getDuration() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        String secs = String.valueOf(seconds);
//        If seconds is a digit
        if (seconds / 10 == 0)
            secs = "0" + secs;
        return String.format("%d:%s", minutes, secs);
    }
    
    public int getDurationInNumbers() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" + "songId=" + songId + ", duration=" + duration + ", artist=" + artist + ", title=" + title + ", songFilePath=" + songFilePath + ", songImagePath=" + songImagePath + ", album=" + album + '}';
    }    

}
