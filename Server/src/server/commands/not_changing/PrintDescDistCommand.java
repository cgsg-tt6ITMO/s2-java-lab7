/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.not_changing;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;
import server.commands.auxilary.AbstractCommand;
import server.commands.auxilary.Command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

import static resources.utility.Status.OK;

/**
 * Handle 'print_field_descending_distance' method.
 */
public class PrintDescDistCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;

    /**
     * Set name and description for 'print_field_descending_distance' command.
     * @param stack storage of the collection.
     */
    public PrintDescDistCommand(Stack<Route> stack) {
        super("print_field_descending_distance", "prints distances in descending order;");
        this.stack = stack;
    }

    /**
     * Prints distances in descending order.
     */
    @Override
    public Response execute(Arguments args) {
        StringBuilder sb = new StringBuilder("ALL DISTANCES IN DESCENDING ORDER:\n");
        ArrayList<Double> distances = new ArrayList<>();
        for (var el : stack) {
            distances.add(el.getDistance());
        }
        distances.sort(Comparator.comparing(el -> el));
        for (int i = distances.size() - 1; i >= 0; i--) {
            sb.append(distances.get(i)).append("\n");
        }
        sb.append('\n');
        return new Response(OK, new String(sb));
    }
}
