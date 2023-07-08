/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.databases;

import resources.task.Location;
import resources.task.Route;
import server.handlers.Loader;

import java.sql.*;
import java.util.Stack;
import java.util.function.Function;

public class DBInitialization {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        createIdSequence();
        createCollectionTable();
        defaultFill();
        allTable();
    }

    private void createCollectionTable() {
        try {
            Statement statement1 = connection.createStatement();
            statement1.execute("""
                CREATE TABLE IF NOT EXISTS s368924_LabaN7(
                    id bigint PRIMARY KEY DEFAULT nextval('id_sequence'),
                    routeName VARCHAR(45) NOT NULL,
                    coordinatesX VARCHAR(45) NULL,
                    coordinatesY VARCHAR(45) NULL,
                    creationTime TIMESTAMP WITHOUT TIME ZONE NULL,
                    locFromX FLOAT NULL,
                    locFromY FLOAT NOT NULL,
                    locFromZ INT NULL,
                    locFromName VARCHAR(45) NOT NULL,
                    locToX FLOAT NULL,
                    locToY FLOAT NOT NULL,
                    locToZ INT NULL,
                    locToName VARCHAR(45) NOT NULL,
                    distance DOUBLE PRECISION NULL
                );""");
            ResultSet resultSet = statement1.getResultSet();
            //writeResSet(resultSet);
            if (resultSet != null) {
                resultSet.close();
            }
            statement1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void defaultFill() {
        try {
            Statement statement1 = connection.createStatement();
            String sql = "INSERT INTO s368924_LabaN7 (routeName,  coordinatesX, coordinatesY, creationTime, " +
                    "locFromX, locFromY, locFromZ, locFromName, locToX, locToY, locToZ, locToName, distance)" + "\n" +
                    "VALUES (";
            Stack<Route> stack = new Loader(System.getenv("JAVA_LABA_6")).load();
            //for (var el : stack) {
            var el = stack.get(0);
                if (el.getCreationTime() != null) {
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
                }
            //}
            sql += ");";
            System.out.println(sql);
            statement1.execute(sql);

            statement1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void allTable() {
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
