/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;
import resources.utility.Deserializer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

/**
 * Handle 'remove_by_id' method.
 */
public class RemoveByIdCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final Connection conn;

    /**
     * Set name and description for 'remove_by_id' command.
     * @param stack storage of the collection.
     */
    public RemoveByIdCommand(Stack<Route> stack, Connection connection) {
        super("remove_by_id", "deletes the element with inputted id (if it was added by you);");
        this.stack = stack;
        this.conn = connection;
    }

    /**
     * Deletes the element with inputted id.
     */
    @Override
    public Response execute(Arguments args) {
        StringBuilder sb = new StringBuilder("REMOVE BY ID:\n");
        try {
            Long id = Deserializer.readLong(args.getData());
            Statement statement = conn.createStatement();
            boolean doExist = statement.execute("SELECT * FROM s368924_LabaN7 WHERE id = " + id + "AND author = '" + args.getAuthor() + "'");
            if (doExist) {
                statement.execute("DELETE FROM s368924_LabaN7 WHERE id = " + id + "AND author = '" + args.getAuthor() + "'");
                stack.removeIf(el -> el.getId().equals(id));
                sb.append("An element with id = " + id + " was successfully removed.\n\n");
            }
            else {
                sb.append("An element with this id doesn't exists or you don't have rights to remove it.\n\n");
            }
            return new Response(new String(sb));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response("ERROR");
    }
}
