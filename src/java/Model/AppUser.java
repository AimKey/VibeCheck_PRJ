package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author phamm
 */
public class AppUser {

    private int userId;
    private String username, email, password, profilePicPath;
    private LocalDate dateJoined;
    private boolean isAdmin;

    public AppUser(String username, String email, String password, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public AppUser(int userId, String username, String email, String profilePicPath, boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.profilePicPath = profilePicPath;
        this.isAdmin = isAdmin;
    }
    
    
    public AppUser(int userId, String username, String profilePicPath, boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.profilePicPath = profilePicPath;
        this.isAdmin = isAdmin;
    }

    // Because SQL and HTML use yyyy-MM-dd format, we will also try to do so.
    public AppUser(int userId, String username, String email, String password, String profilePicPath, String dateJoined, Boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicPath = profilePicPath;
        this.dateJoined = LocalDate.parse(dateJoined, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.isAdmin = isAdmin;
    }

    public AppUser() {
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

    public String getDateJoined() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(dateJoined);
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }
    public String getProfilePicPath() {
        return profilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "AppUser{" + "userId=" + userId + ", username=" + username + ", email=" + email + ", password=" + password + ", profilePicPath=" + profilePicPath + ", dateJoined=" + dateJoined + ", isAdmin=" + isAdmin + '}';
    }
    
    
}
