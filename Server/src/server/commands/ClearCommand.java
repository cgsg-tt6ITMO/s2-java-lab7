/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Response;

import java.util.Stack;

/**
 * Handle 'clear' method.
 */
public class ClearCommand extends AbstractCommand implements Command{
    private final Stack<Route> stack;

    /**
     * Set name and description for 'clear' command.
     * @param stack storage of the collection.
     */
    public ClearCommand(Stack<Route> stack) {
        super("clear", "deletes all the elements of the collection;");
        this.stack = stack;
    }

    /**
     * Deletes all the elements in collection.
     */
    @Override
    public Response execute(String args) {
        stack.clear();
        return new Response("CLEAR:\nNow the collection is empty.\n");
    }
}
