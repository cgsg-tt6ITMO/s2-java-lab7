/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Response;
import resources.utility.Deserializer;

import java.util.Stack;

/**
 * Handle 'add' method.
 */
public class AddCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;

    /**
     * Set name and description for 'add' command.
     * @param stack storage of the collection.
     */
    public AddCommand(Stack<Route> stack) {
        super("add", "adds your element to the collection;");
        this.stack = stack;
    }

    /**
     * Adds one element from console to the collection.
     */
    @Override
    public Response execute(String args) {
        Route r = Deserializer.readRoute(args);
        stack.add(r);
        return new Response("ADD ELEMENT:\nNEW ELEMENT ADDED SUCCESSFULLY\n");
    }
}
