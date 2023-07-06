/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.databases;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public Connection connect() {
        Properties info = new Properties();
        Connection conn = null;
        try (FileInputStream config = new FileInputStream("src/files/db.cfg")) {
            Class.forName("org.postgresql.Driver");
            info.load(config);
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.err.println("DBConnection1: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("DBConnection2: Configuration file not found or something");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("DBConnection3: Probably, problems with driver");
        }
        return conn;
    }
}
