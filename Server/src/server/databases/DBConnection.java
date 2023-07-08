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
import java.util.logging.Logger;

public class DBConnection {
    public Connection connect() {
        Logger log = Logger.getLogger(DBConnection.class.getName());
        Properties info = new Properties();
        Connection conn = null;
        try (FileInputStream config = new FileInputStream("src/files/db.cfg")) {
            Class.forName("org.postgresql.Driver");
            info.load(config);
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            log.info("Connection to SQL server is successful.");
        } catch (SQLException e) {
            log.warning("DBConnection1: Connect to helios and try again");
        } catch (IOException e) {
            log.warning("DBConnection2: Configuration file not found or something");
        } catch (ClassNotFoundException e) {
            log.warning("DBConnection3: Probably, problems with driver");
        } catch (RuntimeException e) {
            log.warning("DBConnection4: Unknown error");
        }
        return conn;
    }
}
