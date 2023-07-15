/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Arguments;
import resources.utility.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class RegisterCommand implements Command {
    private final Connection conn;
    private final Logger log;

    public RegisterCommand(Connection connection) {
        this.conn = connection;
        log = Logger.getLogger(RegisterCommand.class.getName());
    }

    private void createUserTable() {
        try {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS s368924_LabaN7_users(" +
                    "author VARCHAR(45) UNIQUE NOT NULL," +
                    "password VARCHAR(45) NOT NULL" +
                    ");");
            log.info("Table 's368924_LabaN7_users' now exits");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response execute(Arguments args) {
        createUserTable();
        try {
            Statement statement1 = conn.createStatement();
            statement1.execute("INSERT INTO s368924_LabaN7_users (author, password) " +
                    "VALUES ('" + args.getAuthor() + "', '" + args.getData() + "')");
            return new Response("Registration success. Now log in.");
        } catch (SQLException e) {
            return new Response("This user name is not available. Try to register again.");
        } catch (RuntimeException e) {
            return new Response("ERROR");
        }
    }
}
