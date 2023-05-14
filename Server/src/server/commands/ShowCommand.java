/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Response;

import java.util.Stack;

/**
 * Handle 'show' method.
 */
public class ShowCommand extends AbstractCommand implements Command{
    private final Stack<Route> stack;

    /**
     * Set name and description for 'show' command.
     * @param stack storage of the collection.
     */
    public ShowCommand(Stack<Route> stack) {
        super("show", "prints the collection to screen;");
        this.stack = stack;
    }

    /**
     * Prints the collection to screen.
     */
    @Override
    public Response execute(String args) {
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
    }
}
