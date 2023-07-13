/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Deserializer;
import resources.utility.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

/**
 * Handle 'clear' method.
 */
public class ClearCommand extends AbstractCommand implements Command{
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

        //return new Response("CLEAR:\nNow the collection is empty.\n");

        try {
            String user = args.getAuthor();
            Statement statement1 = conn.createStatement();
            boolean doExist = statement1.execute("SELECT id FROM s368924_LabaN7 WHERE author = '" + user + "'");
            ResultSet rs = statement1.getResultSet();
            if (doExist) {
                Statement statement2 = conn.createStatement();
                statement2.execute("DELETE FROM s368924_LabaN7 WHERE author = '" + user + "'");
                while (rs.next()) {
                    stack.removeIf(el -> {
                        try {
                            return el.getId().equals(rs.getLong(1));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return new Response("CLEAR:\nAll your routes were deleted.\n\n");
            }
            return new Response("CLEAR:\nYou didn't add any routes yet.\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("clear: lambda problem");
            e.printStackTrace();
        }
        return new Response("ERROR");

    }
}
