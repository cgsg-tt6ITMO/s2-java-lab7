/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.auxilary;

import resources.utility.Arguments;
import resources.utility.Response;
import resources.task.Route;
import server.commands.auxilary.Command;

import java.util.Comparator;
import java.util.Stack;

/**
 * Sorts the collection.
 * (You cannot call it, so it doesn't have description and name)
 */
public class SortingCommand implements Command {
    private final Stack<Route> stack;

    /**
     * @param stack storage of the collection.
     */
    public SortingCommand(Stack<Route> stack) {
        this.stack = stack;
    }

    /**
     * Sorts the Stack by id.
     */
    @Override
    public Response execute(Arguments args) {
        Comparator<Route> routeComparator = Comparator.comparing(Route::getId);
        stack.sort(routeComparator);
        return new Response();
    }
}
