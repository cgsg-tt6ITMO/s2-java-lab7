/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.remove;

import resources.utility.Arguments;
import resources.utility.Response;
import resources.task.Route;
import server.managers.SerializationManager;
import server.commands.auxilary.AbstractCommand;
import server.commands.auxilary.Command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import static resources.utility.Status.*;

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
        Route route = SerializationManager.readRoute(args.getData());
        try {
            String user = args.getAuthor();
            Statement statement1 = conn.createStatement();
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
                return new Response(SUCCESS, "REMOVE LOWER:\nSUCCESSFUL REMOVE\n\n");
            }
            return new Response(OK, "REMOVE LOWER:\nNo elements were less than inputted.\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response(ERROR, "");
    }
}
