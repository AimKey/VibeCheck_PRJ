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
    private String tittle, filePath;
    private ArrayList<String> genres;
    
    public Song(int duration, String tittle, String filePath, ArrayList<String> genres) {
        this.duration = duration;
        this.tittle = tittle;
        this.filePath = filePath;
        this.genres = genres;
    }

    public Song(int songId, int artistId, int duration, String tittle, String filePath, ArrayList<String> genres) {
        this.songId = songId;
        this.artistId = artistId;
        this.duration = duration;
        this.tittle = tittle;
        this.filePath = filePath;
        this.genres = genres;
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

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
    
    
}
