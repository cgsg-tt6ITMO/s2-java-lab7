/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Arguments;
import resources.utility.Response;
import resources.task.Route;
import resources.utility.Deserializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

/**
 * Handle 'remove_lower' method.
 */
public class RemoveLowerCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final Connection conn;

    /**
     * Set name and description for 'remove_lower' command.
     * @param stack storage of the collection.
     */
    public RemoveLowerCommand(Stack<Route> stack, Connection connection) {
        super("remove_lower", "removes all elements lower than inputted;");
        this.stack = stack;
        this.conn = connection;
    }

    /**
     * Removes all elements lower than inputted.
     */
    @Override
    public Response execute(Arguments args) {
        Route route = Deserializer.readRoute(args.getData());
        try {
            String user = args.getAuthor();
            Statement statement1 = conn.createStatement();
            System.out.println("distances: " + route.getDistance());


            boolean doExist = statement1.execute("SELECT id FROM s368924_LabaN7 WHERE author = '" + user +
                    "' AND distance < " + route.getDistance());
            System.out.println(doExist);
            ResultSet rs = statement1.getResultSet();

            if (rs.next()) {
                Statement statement3 = conn.createStatement();
                statement3.execute("DELETE FROM s368924_LabaN7 WHERE author = '" + user +
                        "' AND distance < " + route.getDistance());
                while (rs.next()) {
                    stack.removeIf(el -> {
                        try {
                            return el.getId().equals(rs.getLong(1));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                return new Response("REMOVE LOWER:\nSUCCESSFUL REMOVE\n\n");
            }
            return new Response("REMOVE LOWER:\nNo elements were less than inputted.\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response("ERROR");
    }
}
