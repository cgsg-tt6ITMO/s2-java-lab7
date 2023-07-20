/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.auxilary;

import resources.utility.Arguments;
import resources.utility.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterCommand implements Command {
    private final Connection conn;

    public RegisterCommand(Connection connection) {
        this.conn = connection;
    }

    @Override
    public Response execute(Arguments args) {
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
