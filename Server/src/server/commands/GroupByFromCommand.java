/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Response;

import java.util.HashMap;
import java.util.Stack;

/**
 * Handle 'group_counting_by_from' method.
 */
public class GroupByFromCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;

    /**
     * Set name and description for 'group_counting_by_from' command.
     * @param stack storage of the collection.
     */
    public GroupByFromCommand(Stack<Route> stack) {
        super("group_counting_by_from", "outputs numbers of elements with the same from;");
        this.stack = stack;
    }

    /**
     * Outputs numbers of elements with the same from.
     */
    @Override
    public Response execute(String args) {
        StringBuilder sb = new StringBuilder("GROUP COUNTING BY 'FROM':");
        HashMap<String, Integer> grouped = new HashMap<>();

        for (var el : stack) {
            String from = el.getFrom().toString();
            if (!grouped.containsKey(from)) {
                grouped.put(from, 1);
            }
            else {
                grouped.put(from, grouped.get(from) + 1);
            }
        }
        for (var el : grouped.keySet()) {
            sb.append("Location from: ").append(el).append("\nNumber of elements with this from: ")
                    .append(grouped.get(el)).append("\n");
        }
        return new Response(new String(sb));
    }
}
