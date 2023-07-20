/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.remove;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;
import server.commands.auxilary.AbstractCommand;
import server.commands.auxilary.Command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import static resources.utility.Status.*;

/**
 * Handle 'clear' method.
 */
public class ClearCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final Connection conn;

    /**
     * Set name and description for 'clear' command.
     * @param stack storage of the collection.
     */
    public ClearCommand(Stack<Route> stack, Connection connection) {
        super("clear", "deletes all the elements added to collection by the current user;");
        this.stack = stack;
        this.conn = connection;
    }

    /**
     * Deletes all the elements in collection.
     */
    @Override
    public Response execute(Arguments args) {
        try {
            String user = args.getAuthor();
            Statement statement1 = conn.createStatement();
            statement1.execute("SELECT COUNT(id) FROM s368924_LabaN7 WHERE author = '" + user + "'");
            ResultSet rs1 = statement1.getResultSet();

            if (rs1.next() && rs1.getLong(1) != 0) {
                Statement statement2 = conn.createStatement();
                statement2.execute("SELECT id FROM s368924_LabaN7 WHERE author = '" + user + "'");
                ResultSet rs2 = statement2.getResultSet();
                Statement statement3 = conn.createStatement();
                statement3.execute("DELETE FROM s368924_LabaN7 WHERE author = '" + user + "'");
                while (rs2.next()) {
                    stack.removeIf(el -> {
                        try {
                            return el.getId().equals(rs2.getLong(1));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return new Response(SUCCESS, "CLEAR:\nAll your routes were deleted.\n\n");
            }
            return new Response(OK, "CLEAR:\nYou haven't added any routes yet.\n\n");
        } catch (SQLException e) {
            return new Response(ERROR, "clear: sql problem");
        } catch (RuntimeException e) {
            return new Response(ERROR, "clear: lambda problem");
        }
    }
}
