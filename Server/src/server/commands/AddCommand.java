/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Location;
import resources.task.Route;
import resources.utility.Arguments;
import resources.utility.Response;
import resources.utility.Deserializer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import java.util.function.Function;

/**
 * Handle 'add' method.
 */
public class AddCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final Connection conn;

    /**
     * Set name and description for 'add' command.
     * @param stack storage of the collection.
     */
    public AddCommand(Stack<Route> stack, Connection connection) {
        super("add", "adds your element to the collection;");
        this.stack = stack;
        this.conn = connection;
    }

    /**
     * Adds one element from console to the collection.
     */
    @Override
    public Response execute(Arguments args) {
        Route el = Deserializer.readRoute(args.getData());
        try {
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO s368924_LabaN7 (routeName,  coordinatesX, coordinatesY, creationTime, " +
                    "locFromX, locFromY, locFromZ, locFromName, locToX, locToY, locToZ, locToName, distance, author)\n" +
                    "VALUES (";
            var d = el.getCreationTime();
            Function<Integer, String> intFormat = (num) -> (num > 9 ? "" + num : "0" + num);
            Function<Location, String> locFormat = (loc) ->
                    (loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", '" + loc.getName() + "', ");
            String time = "'" + d.getYear() + "-" + intFormat.apply(d.getMonthValue()) + "-" +
                    intFormat.apply(d.getDayOfMonth()) + " " +
                    intFormat.apply(d.getHour()) + ":" +
                    intFormat.apply(d.getMinute()) + ":" +
                    intFormat.apply(d.getSecond()) + "'";
            sql += "'" + el.getName() + "', " + el.getCoordinates().getX() + ", " + el.getCoordinates().getY() + ", " + time + ", "
                    + locFormat.apply(el.getFrom()) + locFormat.apply(el.getTo()) + el.getDistance();
            sql += ", '" + args.getAuthor() + "');";
            statement.execute(sql);
            // раз не вылетела ошибка, всё успешно, и можно менять коллекцию в памяти
            stack.add(el);
            return new Response("ADD ELEMENT:\nNEW ELEMENT ADDED SUCCESSFULLY\n");
        } catch (SQLException e) {
            System.err.println("Add command: sql exception");
        }
        return new Response("ERROR");
    }
}
