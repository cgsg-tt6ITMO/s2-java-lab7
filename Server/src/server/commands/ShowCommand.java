/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;

import java.sql.*;
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
    public Response execute(Arguments args) {
        try {
            Statement statement = conn.createStatement();
            statement.execute("SELECT * FROM s368924_LabaN7");
            ResultSet rs = statement.getResultSet();

            StringBuilder sb = new StringBuilder("SHOW COLLECTION:\n");
            if (stack.size() == 0) {
                sb.append("The collection is empty.");
                return new Response(new String(sb));
            }
            sb.append("id\t\troutename\t\tcoordinates\t\tlocation from\t\tlocation to\t\ttime\t\tdist\t\tauthor").
            append("\n\n");

            while (rs.next()) {
                sb.append(rs.getLong("id")).append("\t\t").append(rs.getString("routeName")).append("\t\t").
                        append(rs.getDouble("coordinatesX")).append("\t\t").
                        append(rs.getFloat("coordinatesY")).append("\t\t").
                        append(rs.getFloat("locFromX")).append("\t\t").
                        append(rs.getFloat("locFromY")).append("\t\t").
                        append(rs.getLong("locFromZ")).append("\t\t").
                        append(rs.getString("locFromName")).append("\t\t").
                        append(rs.getFloat("locToX")).append("\t\t").
                        append(rs.getFloat("locToY")).append("\t\t").
                        append(rs.getLong("locToZ")).append("\t\t").
                        append(rs.getString("locToName")).append("\t\t").
                        append(rs.getDate("creationTime")).append("\t\t").
                        append(rs.getDouble("distance")).append("\t\t").
                        append(rs.getString("author"));
                sb.append("\n\n");
            }
            return new Response(new String(sb));
        } catch (SQLException e) {
            System.err.println("Show command: sql exception");
            e.printStackTrace();
        }
        return new Response("ERROR");
    }
}
