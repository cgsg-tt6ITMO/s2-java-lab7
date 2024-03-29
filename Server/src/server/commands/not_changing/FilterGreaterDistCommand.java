/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.not_changing;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;
import server.managers.SerializationManager;
import server.commands.auxilary.AbstractCommand;
import server.commands.auxilary.Command;

import java.util.Stack;

import static resources.utility.Status.OK;

/**
 * Handle 'filter_greater_than_distance' method.
 */
public class FilterGreaterDistCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;

    /**
     * Set name and description for 'filter_greater_than_distance' command.
     * @param stack storage of the collection.
     */
    public FilterGreaterDistCommand(Stack<Route> stack) {
        super("filter_greater_than_distance", "prints elements with distance greater than the inputted one;");
        this.stack = stack;
    }

    /**
     * Shows elements with distance greater than the inputted one.
     */
    @Override
    public Response execute(Arguments args) {
        StringBuilder sb = new StringBuilder("ROUTES WITH DIST GREATER THAN INPUTTED:\n");
        Double distance = SerializationManager.readDouble(args.getData());
        for (var el : stack) {
            if (el.getDistance() > distance) {
                sb.append("ID: \t\t").append(el.getId()).append("\nName: \t\t").append(el.getName())
                        .append("\nDistance: \t").append(el.getDistance()).append("\n\n");
            }
        }
        sb.append('\n');
        return new Response(OK, new String(sb));
    }
}
