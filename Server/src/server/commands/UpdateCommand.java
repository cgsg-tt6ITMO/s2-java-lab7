/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Response;
import resources.task.Coordinates;
import resources.task.Location;
import resources.utility.Deserializer;

import java.util.Scanner;
import java.util.Stack;

import static java.lang.Math.sqrt;

/**
 * Handle 'update' method.
 */
public class UpdateCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;

    /**
     * Set name and description for 'update' command.
     * @param stack storage of the collection.
     */
    public UpdateCommand(Stack<Route> stack) {
        super("update", "updates element with id inputted;");
        this.stack = stack;
    }

    /**
     * @param id index of element to be shown.
     */
    private StringBuilder show_by_id(Long id) {
        StringBuilder s = new StringBuilder();
        for (var r : stack) {
            if (r.getId().equals(id)) {
                s.append("Route Id:      ").append(r.getId()).append("\nName:          ").append(r.getName())
                        .append("\nCreation date: ").append(r.getCreationTime())
                        .append("\nCoordinates:   ").append(r.getCoordinates().getX()).append(" ").append(r.getCoordinates().getY())
                        .append("\nLocation From: ").append(r.getFrom().getName()).append(" ").append(r.getFrom().getX())
                        .append(" ").append(r.getFrom().getY()).append(" ").append(r.getFrom().getZ())
                        .append("\nLocation To:   ").append(r.getTo().getName()).append(" ").append(r.getTo().getX())
                        .append(" ").append(r.getTo().getY()).append(" ").append(r.getTo().getZ())
                        .append("\nDistance:      ").append(r.getDistance()).append("\n\n");
            }
        }
        return s;
    }

    /**
     * Updates element with id inputted.
     */
    @Override
    public Response execute(String args) {
        Scanner scanner = new Scanner(args);
        String idStr = scanner.nextLine();
        scanner.close();
        Long id = Long.parseLong(idStr);
        StringBuilder json = new StringBuilder();

        for (int i = idStr.length(); i < args.length(); i++) {
            json.append(args.charAt(i));
        }

        boolean exist = false;

        for (var el : stack) {
            if (el.getId().equals(id)) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            return new Response("Element with this id doesn't exist.");
        }

        StringBuilder sb = new StringBuilder(show_by_id(id));

        Route route = Deserializer.readRoute(json.toString());

        String Name = route.getName();
        Coordinates coords = route.getCoordinates();
        Location f = route.getFrom();
        Location t = route.getTo();
        for (var r : stack) {
            if (r.getId().equals(id)) {
                r.setName(Name);
                r.setCoordinates(coords);
                r.setFrom(f);
                r.setTo(t);
                Double dist = sqrt((f.getX()-t.getX()) * (f.getX()-t.getX()) + (f.getY()-t.getY()) * (f.getY()-t.getY())
                        + (f.getZ()-t.getZ()) * (f.getZ()-t.getZ()));
                r.setDistance(dist);
                break;
            }
        }
        sb.append("ELEMENT UPDATED SUCCESSFULLY\n\n");
        return new Response(new String(sb));
    }
}
