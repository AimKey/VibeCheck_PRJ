package Database;

 // @author phamm
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseInformation {
    private String serverName, dbName, dbUser, dbPassword;
    
    // TODO: Change this to your database configs
    public DatabaseInformation() {
        this("KUUL", "AppMusicDatabase", "sa", "123456");
    }

    public DatabaseInformation(String serverName, String dbName, String dbUser, String dbPassword) {
        this.serverName = serverName;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    // Setup connection
    public Connection getConnection()
            throws ServletException, IOException {
        java.sql.Connection con = null;
        String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = String.format(
                "jdbc:sqlserver://%s;databaseName=%s;encrypt=false;trustServerCertificate=false;loginTimeout=30"
                , serverName, dbName);
        try {
            Class.forName(driverClass);
            //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return con;
    }
}
