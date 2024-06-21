/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author phamm
 */
public class Song {

    private int songId, artistId, duration;
    // Song filePath is songs/sth/sth.mp3
    // Song img path is songs/sth/sth.jpeg;
    private String title, songFilePath, songImagePath;
    private String album;

    public Song(int artistId, int duration, String tittle, String filePath, String album) {
        this.artistId = artistId;
        this.duration = duration;
        this.title = tittle;
        this.songFilePath = filePath;
        this.album = album;
    }

    public Song(int songId, int artistId, int duration, String title, String songFilePath, String songImagePath, String album) {
        this.songId = songId;
        this.artistId = artistId;
        this.duration = duration;
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

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFilePath() {
        return songFilePath;
    }

    public void setFilePath(String filePath) {
        this.songFilePath = filePath;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

}
