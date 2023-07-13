/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

/**
 * Handle 'show' method.
 */
public class ShowCommand extends AbstractCommand implements Command{
    private final Stack<Route> stack;
    private final Connection conn;

    /**
     * Set name and description for 'show' command.
     * @param stack storage of the collection.
     */
    public ShowCommand(Stack<Route> stack, Connection connection) {
        super("show", "prints the collection to screen;");
        this.stack = stack;
        this.conn = connection;
    }

    /**
     * Prints the collection to screen.
     */
    @Override
    public Response execute(String args) {
        try {
            Statement statement = conn.createStatement();
            statement.execute("SELECT * FROM s368924_LabaN7");

            StringBuilder sb = new StringBuilder("SHOW COLLECTION:\n");
            if (stack.size() == 0) {
                sb.append("The collection is empty.");
                return new Response(new String(sb));
            }
            for (var el : stack) {
                sb.append("ID: \t\t").append(el.getId()).append("\nName: \t\t").append(el.getName())
                        .append("\nDistance: \t").append(el.getDistance()).append("\n\n");
            }
            return new Response(new String(sb));
        } catch (SQLException e) {
            System.err.println("Show command: sql exception");
        }
        return new Response("ERROR");
    }
}
