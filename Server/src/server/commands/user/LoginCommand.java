/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.user;

import resources.utility.Arguments;
import resources.utility.Response;
import server.commands.auxilary.Command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static resources.utility.Status.*;

public class LoginCommand implements Command {
    private final Connection conn;
    public LoginCommand(Connection connection) {
        this.conn = connection;
    }

    @Override
    public Response execute(Arguments args) {
        try {
            Statement statement = conn.createStatement();
            statement.execute("SELECT COUNT(author) FROM s368924_LabaN7_users " +
                    "WHERE author = '" + args.getAuthor() + "' AND password = '"+ args.getData() +"'");
            ResultSet rs = statement.getResultSet();
            if (rs.next() && rs.getLong(1) != 0) {
                return new Response(SUCCESS, "Login success!");
            }
            return new Response(OK,"Login or password is not correct.");
        } catch (SQLException e) {
            return new Response(ERROR, "SQL exception occurred.");
        } catch (RuntimeException e) {
            return new Response(ERROR,"");
        }
    }
}
