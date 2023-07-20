/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.managers;

import resources.task.Coordinates;
import resources.task.Location;
import resources.task.Route;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Handles all actions with databases.
 */
public class DatabaseManager {
    private Logger log;
    private Connection connection;

    public Connection start() {
        log = Logger.getLogger(DatabaseManager.class.getName());
        connection = connect();
        createUserTable();
        createCollectionTable();
        return connection;
    }

    private Connection connect() {
        Properties info = new Properties();
        Connection conn = null;
        try (FileInputStream config = new FileInputStream("src/files/db.cfg")) {
            Class.forName("org.postgresql.Driver");
            info.load(config);
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            log.info("Connection to SQL server is successful.");
        } catch (SQLException e) {
            log.warning("Connect to helios and try again");
            System.exit(-1);
        } catch (IOException e) {
            log.warning("Configuration file not found or something");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            log.warning("Probably, problems with driver");
            System.exit(-1);
        } catch (RuntimeException e) {
            log.warning("Unknown error");
            System.exit(-1);
        }
        return conn;
    }

    private void createUserTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS s368924_LabaN7_users(" +
                    "author VARCHAR(45) UNIQUE NOT NULL," +
                    "password VARCHAR(45) NOT NULL" +
                    ");");
            log.info("Table 's368924_LabaN7_users' now exits");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createCollectionTable() {
        try {
            Statement createSequence = connection.createStatement();
            createSequence.execute("CREATE SEQUENCE IF NOT EXISTS id_sequence START WITH 1 INCREMENT BY 1;");
            Statement statement1 = connection.createStatement();
            statement1.execute("""
                CREATE TABLE IF NOT EXISTS s368924_LabaN7(
                    id bigint PRIMARY KEY DEFAULT nextval('id_sequence'),
                    routeName VARCHAR(45) NOT NULL,
                    coordinatesX DOUBLE PRECISION NULL,
                    coordinatesY FLOAT NULL,
                    creationTime TIMESTAMP WITHOUT TIME ZONE NULL,
                    locFromX FLOAT NULL,
                    locFromY FLOAT NOT NULL,
                    locFromZ INT NULL,
                    locFromName VARCHAR(45) NOT NULL,
                    locToX FLOAT NULL,
                    locToY FLOAT NOT NULL,
                    locToZ INT NULL,
                    locToName VARCHAR(45) NOT NULL,
                    distance DOUBLE PRECISION NULL,
                    author VARCHAR(45) NOT NULL
                );""");
            log.info("table 's368924_LabaN7' now exists");
            statement1.close();
        } catch (SQLException e) {
            log.warning("error while creating a table");
            e.printStackTrace();
        } catch (RuntimeException e) {
            log.warning("unknown error while creating a table");
        }
    }

    /**
     * @return collection out of sql table.
     */
    public Stack<Route> getCollection() {
        Stack<Route> routes = new Stack<>();
        try {
            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM s368924_LabaN7");
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("routeName");
                Coordinates coords = new Coordinates(
                        rs.getDouble("coordinatesX"),
                        rs.getFloat("coordinatesY"));
                Location from = new Location(
                        rs.getFloat("locFromX"),
                        rs.getFloat("locFromY"),
                        rs.getLong("locFromZ"),
                        rs.getString("locFromName"));
                Location to = new Location(
                        rs.getFloat("locToX"),
                        rs.getFloat("locToY"),
                        rs.getLong("locToZ"),
                        rs.getString("locToName"));
                Double dist = rs.getDouble("distance");
                Route r = new Route(id, name, coords, from, to);
                routes.add(r);
            }
        } catch (SQLException e) {
            log.warning("SQLException while getting a collection out of database");
            e.printStackTrace();
        } catch (RuntimeException e) {
            log.warning("Unknown rror while getting a collection out of database");
        }
        return routes;
    }

}
