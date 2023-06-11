package server.databases;

import java.sql.*;

public class DBInit {
    public DBInit() {

    }

    public void initialize() throws SQLException {
        // lecture 5
        // 10:29 create table
        // https://vk.com/video1954483_456239028
        // 32:05

        String dbname = "sys";
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + dbname, "root", "Pudel12345");

        Statement statement1 = connection.createStatement();
        ResultSet resultSet = statement1.executeQuery("SELECT * FROM `routes1`");
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
        resultSet.close();
        statement1.close();
        connection.close();
    }

}
