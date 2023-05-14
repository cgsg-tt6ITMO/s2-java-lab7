/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Response;
import resources.task.Route;
import resources.utility.Deserializer;

import java.util.Stack;

/**
 * Handle 'remove_lower' method.
 */
public class RemoveLowerCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;

    /**
     * Set name and description for 'remove_lower' command.
     * @param stack storage of the collection.
     */
    public RemoveLowerCommand(Stack<Route> stack) {
        super("remove_lower", "removes all elements lower than inputted;");
        this.stack = stack;
    }

    /**
     * Removes all elements lower than inputted.
     */
    @Override
    public Response execute(String args) {
        Route route = Deserializer.readRoute(args);
        int n = stack.size();
        StringBuilder sb = new StringBuilder("REMOVE LOWER:\n");
        stack.removeIf(el -> route.compareTo(el) > 0);
        // analyse results
        if (n > stack.size()) {
            sb.append("SUCCESSFUL REMOVE\n\n");
        } else {
            sb.append("No elements were less than inputted.\n\n");
        }
        return new Response(new String(sb));
    }
}
