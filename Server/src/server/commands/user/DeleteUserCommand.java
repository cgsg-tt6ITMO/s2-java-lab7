/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.user;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;
import server.commands.auxilary.AbstractCommand;
import server.commands.auxilary.Command;
import server.commands.remove.ClearCommand;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import static resources.utility.Status.*;
import static resources.utility.Status.ERROR;

/**
 * Deletes the user and all their objects.
 */
public class DeleteUserCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final Connection conn;

    public DeleteUserCommand(Stack<Route> stack, Connection connection) {
        super("delete_user", "deletes user account and all their routes;");
        this.stack = stack;
        this.conn = connection;
    }
    @Override
    public Response execute(Arguments args) {
        try {
            // check if the password is correct
            Statement statement1 = conn.createStatement();
            statement1.execute("SELECT COUNT(author) FROM s368924_LabaN7_users " +
                    "WHERE author = '" + args.getAuthor() + "' AND password = '" + args.getData() + "'");
            ResultSet rs = statement1.getResultSet();
            if (rs.next() && rs.getLong(1) != 0) {
                // then delete all their elements from db and from collection
                ClearCommand clear = new ClearCommand(stack, conn);
                clear.execute(new Arguments("", args.getAuthor()));
                // then delete their account
                Statement statement2 = conn.createStatement();
                statement2.execute("DELETE FROM s368924_LabaN7_users " +
                        "WHERE author = '" + args.getAuthor() + "' AND password = '" + args.getData() + "'");
                return new Response(EXIT, "Your account and all your routes were deleted.");
            }
            return new Response(OK, "Login or password is not correct.");
        } catch (SQLException e) {
            return new Response(ERROR, "SQL exception occurred.");
        } catch (RuntimeException e) {
            return new Response(ERROR,"");
        }
    }
}
