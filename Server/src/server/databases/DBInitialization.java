/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.databases;

import resources.task.Coordinates;
import resources.task.Location;
import resources.task.Route;
import server.handlers.Loader;

import java.sql.*;
import java.util.Stack;
import java.util.function.Function;
import java.util.logging.Logger;

public class DBInitialization {
    private Logger log;
    private final Connection connection;

    public DBInitialization(Connection connection) {
        this.connection = connection;
    }
    private void printResSet(ResultSet resultSet) {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            System.err.println("error while writing results");
        } catch (NullPointerException e) {
            System.err.println("the result set you try to output is null");
        }
    }

    private void createIdSequence() {
        try {
            Statement createSequence = connection.createStatement();
            createSequence.execute("CREATE SEQUENCE IF NOT EXISTS id_sequence START WITH 1 INCREMENT BY 1;");
            log.info("Id sequence now exists");
        } catch (SQLException e) {
            log.warning("Error while creating an id sequence");
            e.printStackTrace();
        } catch (RuntimeException e) {
            log.warning("Unknown error while creating an id sequence");
        }
    }

    private Stack<Route> getCollection() {
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
                Route r = new Route(id, name, coords, from, to);
                routes.add(r);
            }
        } catch (SQLException e) {
            log.warning("Error while getting a collection out of database");
            e.printStackTrace();
        }
        return routes;
    }

    public Stack<Route> initialize() {
        log = Logger.getLogger(DBInitialization.class.getName());
        createIdSequence();
        createCollectionTable();
        //defaultFill();
        //allTable();
        log.info("Data base initialization is successful.");
        return getCollection();
    }

    private void createCollectionTable() {
        try {
            Statement statement1 = connection.createStatement();
            //statement1.execute("DROP TABLE IF EXISTS s368924_LabaN7");
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

    private void defaultFill() {
        try {
            Stack<Route> stack = new Loader(System.getenv("JAVA_LABA_6")).load();
            for (int i = 0; i < stack.size(); i++) {
                var el = stack.get(i);
                if (el != null && el.getCreationTime() != null) {
                    Statement statement = connection.createStatement();
                    String sql = "INSERT INTO s368924_LabaN7 (routeName,  coordinatesX, coordinatesY, creationTime, " +
                            "locFromX, locFromY, locFromZ, locFromName, locToX, locToY, locToZ, locToName, distance, author)\n" +
                            "VALUES (";
                    var d = el.getCreationTime();
                    Function<Integer, String> intFormat = (num) -> (num > 9 ? "" + num : "0" + num);
                    Function<Location, String> locFormat = (loc) -> (loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", '" + loc.getName() + "', ");
                    String time = "'" + d.getYear() + "-" + intFormat.apply(d.getMonthValue()) + "-" +
                            intFormat.apply(d.getDayOfMonth()) + " " +
                            intFormat.apply(d.getHour()) + ":" +
                            intFormat.apply(d.getMinute()) + ":" +
                            intFormat.apply(d.getSecond()) + "'";
                    sql += "'" + el.getName() + "', " + el.getCoordinates().getX() + ", " + el.getCoordinates().getY() + ", " + time + ", "
                            + locFormat.apply(el.getFrom()) + locFormat.apply(el.getTo()) + el.getDistance();
                    sql += ", 'default user');";
                    System.out.println(sql);
                    statement.execute(sql);
                    statement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void allTable() {
        try {
            Statement statement1 = connection.createStatement();
            statement1.execute("SELECT * FROM s368924_LabaN7");
            ResultSet resultSet = statement1.getResultSet();
            printResSet(resultSet);
            if (resultSet != null) {
                resultSet.close();
            }
            statement1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
