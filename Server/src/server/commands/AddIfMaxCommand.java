/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Response;
import resources.task.Route;
import resources.utility.Deserializer;

import java.util.Stack;

/**
 * Handle 'add_if_max' method.
 */
public class AddIfMaxCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;

    /**
     * Set name and description for 'add_if_max' command.
     * @param stack storage of the collection.
     */
    public AddIfMaxCommand(Stack<Route> stack) {
        super("add_if_max", "adds the element if it is larger than every element in collection;");
        this.stack = stack;
    }

    /**
     * Adds the element if it is larger than every element stored in collection.
     */
    @Override
    public Response execute(String args) {
        Route route = Deserializer.readRoute(args);

        boolean flag = true;
        for (var el : stack) {
            if (route.compareTo(el) != 1) {
                flag = false;
                break;
            }
        }
        if (flag) {
            stack.add(route);
            return new Response("ADD IF MAX:\nNEW ELEMENT ADDED SUCCESSFULLY\n");
        }
        return new Response("ADD IF MAX:\nThe element is not max, so it was not added.\n");

    }
}
