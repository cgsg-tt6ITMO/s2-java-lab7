/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.add;

import resources.task.Location;
import resources.utility.Arguments;
import resources.utility.Response;
import resources.task.Route;
import server.managers.SerializationManager;
import server.commands.auxilary.AbstractCommand;
import server.commands.auxilary.Command;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import java.util.function.Function;

/**
 * Handle 'add_if_max' method.
 */
public class AddIfMaxCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final Connection conn;

    /**
     * Set name and description for 'add_if_max' command.
     * @param stack storage of the collection.
     */
    public AddIfMaxCommand(Stack<Route> stack, Connection connection) {
        super("add_if_max", "adds the element if it is larger than every element in collection;");
        this.stack = stack;
        this.conn = connection;
    }

    /**
     * Adds the element if it is larger than every element stored in collection.
     */
    @Override
    public Response execute(Arguments args) {
        Route route = SerializationManager.readRoute(args.getData());
        try {
            Statement statement1 = conn.createStatement();
            // compate route.dist with the max dist
            statement1.execute("SELECT MAX(distance) FROM s368924_LabaN7");
            ResultSet rs = statement1.getResultSet();
            double maxDist = -1;
            if (rs.next()) {
                maxDist =  rs.getDouble(1);
            }
            if (route.getDistance() > maxDist) {
                Statement statement2 = conn.createStatement();
                String sql = "INSERT INTO s368924_LabaN7 (routeName,  coordinatesX, coordinatesY, creationTime, " +
                        "locFromX, locFromY, locFromZ, locFromName, locToX, locToY, locToZ, locToName, distance, author)\n" +
                        "VALUES (";
                var d = route.getCreationTime();
                Function<Integer, String> intFormat = (num) -> (num > 9 ? "" + num : "0" + num);
                Function<Location, String> locFormat = (loc) ->
                        (loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", '" + loc.getName() + "', ");
                String time = "'" + d.getYear() + "-" + intFormat.apply(d.getMonthValue()) + "-" +
                        intFormat.apply(d.getDayOfMonth()) + " " +
                        intFormat.apply(d.getHour()) + ":" +
                        intFormat.apply(d.getMinute()) + ":" +
                        intFormat.apply(d.getSecond()) + "'";
                sql += "'" + route.getName() + "', " + route.getCoordinates().getX() + ", " + route.getCoordinates().getY() + ", " + time + ", "
                        + locFormat.apply(route.getFrom()) + locFormat.apply(route.getTo()) + route.getDistance();
                sql += ", '" + args.getAuthor() + "');";
                statement2.execute(sql);

                stack.add(route);
                return new Response("ADD IF MAX:\nNEW ELEMENT ADDED SUCCESSFULLY\n");
            }
            return new Response("ADD IF MAX:\nThe element is not max, so it was not added.\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response("ERROR\n");
    }
}
