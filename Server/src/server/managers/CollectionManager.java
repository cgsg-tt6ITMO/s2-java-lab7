/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.managers;

import resources.task.Route;
import resources.utility.Request;
import resources.utility.Response;
import server.commands.add.*;
import server.commands.auxilary.*;
import server.commands.not_changing.*;
import server.commands.remove.*;
import server.commands.user.DeleteUserCommand;
import server.commands.user.LoginCommand;
import server.commands.user.RegisterCommand;

import java.sql.Connection;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Stack;

import static resources.utility.Status.ERROR;

/**
 * Stores the collection and commands for it.
 */
public class CollectionManager {
    private static final HashMap<String, Command> commands = new HashMap<>();

    /**
     * Adds instances of all commands to HashMap.
     */
    public CollectionManager() {
        ZonedDateTime creationDate = ZonedDateTime.now();
        DatabaseManager db = new DatabaseManager();
        Connection dbConn = db.start();
        Stack<Route> stack = db.getCollection();

        HelpCommand help = new HelpCommand(commands);
        InfoCommand info = new InfoCommand(creationDate, "Stack", stack.size());
        ShowCommand show = new ShowCommand(stack, dbConn);
        ExitCommand exit = new ExitCommand();
        AddCommand add = new AddCommand(stack, dbConn);
        RemoveByIdCommand removeById = new RemoveByIdCommand(stack, dbConn);
        ClearCommand clear = new ClearCommand(stack, dbConn);
        AddIfMaxCommand addIfMax = new AddIfMaxCommand(stack, dbConn);
        RemoveLowerCommand removeLower = new RemoveLowerCommand(stack, dbConn);
        PrintDescDistCommand descDist = new PrintDescDistCommand(stack);
        FilterGreaterDistCommand filterGreaterDist = new FilterGreaterDistCommand(stack);
        GroupByFromCommand groupByFrom = new GroupByFromCommand(stack);
        UpdateCommand update = new UpdateCommand(stack, dbConn);
        DeleteUserCommand deleteUser = new DeleteUserCommand(stack, dbConn);

        commands.put("login", new LoginCommand(dbConn));
        commands.put("register", new RegisterCommand(dbConn));
        commands.put(deleteUser.getName(), deleteUser);
        commands.put("sort", new SortingCommand(stack));

        commands.put(help.getName(), help);
        commands.put(info.getName(), info);
        commands.put(show.getName(), show);
        commands.put(exit.getName(), exit);
        commands.put(add.getName(), add);
        commands.put(removeById.getName(), removeById);
        commands.put(clear.getName(), clear);
        commands.put(removeLower.getName(), removeLower);
        commands.put(addIfMax.getName(), addIfMax);
        commands.put(descDist.getName(), descDist);
        commands.put(filterGreaterDist.getName(), filterGreaterDist);
        commands.put(groupByFrom.getName(), groupByFrom);
        commands.put(update.getName(), update);
    }

    public Response runCommand(Request r) {
        if (commands.containsKey(r.getCommandName())) {
            return commands.get(r.getCommandName()).execute(r.getArgs());
        } else {
            return new Response(ERROR, "ERROR: " + r.getCommandName() + " command doesn't exist");
        }
    }
}
