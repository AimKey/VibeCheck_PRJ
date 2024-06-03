package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author phamm
 */
public class AppUser {
    private int userId;
    private String username, email, password, pictureLink;
    private LocalDate dateJoined;
    
    // Because SQL and HTML use yyyy-MM-dd format, we will also try to do so.
    public AppUser(int userId, String username, String email, String password, String pictureLink, String dateJoined) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.pictureLink = pictureLink;
        this.dateJoined = LocalDate.parse(dateJoined, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getDateJoined() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(dateJoined);
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Override
    public String toString() {
        return "AppUser{" + "userId=" + userId + ", username=" + username + 
                ", email=" + email + ", password=" + password + 
                ", pictureLink=" + pictureLink + ", dateJoined=" + getDateJoined() + '}';
    }
    
    
    
}
