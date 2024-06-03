package Model;

/**
 *
 * @author phamm
 */
public class Artist {
    private int artistId;
    private String artistName, bio, picturePath;

    public Artist(int artistId, String artistName, String bio, String picturePath) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.bio = bio;
        this.picturePath = picturePath;
    }

    
    
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    
    
}
