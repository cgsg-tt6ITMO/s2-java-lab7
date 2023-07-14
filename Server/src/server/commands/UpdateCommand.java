/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;
import resources.task.Coordinates;
import resources.task.Location;
import resources.utility.Deserializer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import static java.lang.Math.sqrt;

/**
 * Handle 'update' method.
 */
public class UpdateCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final Connection conn;

    /**
     * Set name and description for 'update' command.
     * @param stack storage of the collection.
     */
    public UpdateCommand(Stack<Route> stack, Connection connection) {
        super("update", "updates element with id inputted;");
        this.stack = stack;
        this.conn = connection;
    }

    /**
     * Updates element with id inputted.
     */
    @Override
    public Response execute(Arguments args) {
        try {
            Scanner scanner = new Scanner(args.getData());
            String idStr = scanner.nextLine();
            scanner.close();
            Long id = Long.parseLong(idStr);
            StringBuilder json = new StringBuilder();

            for (int i = idStr.length(); i < args.getData().length(); i++) {
                json.append(args.getData().charAt(i));
            }
            Route route = Deserializer.readRoute(json.toString());

            String Name = route.getName();
            Coordinates coords = route.getCoordinates();
            Location f = route.getFrom();
            Location t = route.getTo();

            Statement statement1 = conn.createStatement();
            boolean doExist = statement1.execute("SELECT id FROM s368924_LabaN7 " +
                    "WHERE id = " + id + " AND author = '" + args.getAuthor() + "'");
            if (doExist) {
                var d = route.getCreationTime();
                Function<Integer, String> intFormat = (num) -> (num > 9 ? "" + num : "0" + num);
                String time = "'" + d.getYear() + "-" + intFormat.apply(d.getMonthValue()) + "-" +
                        intFormat.apply(d.getDayOfMonth()) + " " +
                        intFormat.apply(d.getHour()) + ":" +
                        intFormat.apply(d.getMinute()) + ":" +
                        intFormat.apply(d.getSecond()) + "', ";

                Statement statement2 = conn.createStatement();
                statement2.execute("UPDATE s368924_LabaN7 " +
                        "SET routeName = '" + route.getName() + "', coordinatesX = " + coords.getX() +
                        ", coordinatesY = " + coords.getY() + ", creationTime = " + time +
                        "locFromX = " + f.getX() + ", locFromY = " + f.getY() +
                        ", locFromZ = " + f.getZ() + ", locFromName = '" + f.getName() + "', " +
                        "locToX = " + f.getX() + ", locToY = " + f.getY() +
                        ", locToZ = " + f.getZ() + ", locToName = '" + f.getName() + "', " +
                        "distance = " + route.getDistance() + ", author = '" + args.getAuthor() +
                        "' WHERE id = " + id);

                for (var r : stack) {
                    if (r.getId().equals(id)) {
                        r.setName(Name);
                        r.setCoordinates(coords);
                        r.setFrom(f);
                        r.setTo(t);
                        Double dist = sqrt((f.getX() - t.getX()) * (f.getX() - t.getX()) + (f.getY() - t.getY()) * (f.getY() - t.getY())
                                + (f.getZ() - t.getZ()) * (f.getZ() - t.getZ()));
                        r.setDistance(dist);
                        break;
                    }
                }
                return new Response("UPDATE:\nElement was successfully updated.\n\n");
            }
            return new Response("UPDATE:\nElement with this id doesn't exist or you have no rights to modify it.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response("ERROR");
    }
}
