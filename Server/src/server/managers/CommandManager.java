/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.managers;

import resources.task.Route;
import resources.utility.Request;
import resources.utility.Response;
import server.commands.*;
import server.databases.DBConnection;
import server.databases.DBInitialization;
import server.handlers.GetDefaultCollectionSize;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Stack;

/**
 * Storage for all commands.
 */
public class CommandManager {
    private static final HashMap<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;

    /**
     * Adds instances of all commands to list.
     */
    public CommandManager() {
        DBConnection dbConnection = new DBConnection();
        Connection dbConn = dbConnection.connect();
        DBInitialization dbInit = new DBInitialization(dbConn);
        Stack<Route> defaultStack = dbInit.initialize();
        collectionManager = new CollectionManager(defaultStack);
        Stack<Route> stack = collectionManager.stack();

        GetDefaultCollectionSize defaultSize = new GetDefaultCollectionSize(stack);
        HelpCommand help = new HelpCommand(this);
        InfoCommand info = new InfoCommand(collectionManager);
        ShowCommand show = new ShowCommand(stack, dbConn);
        ExitCommand exit = new ExitCommand();
        AddCommand add = new AddCommand(stack, dbConn);
        RemoveByIdCommand removeById = new RemoveByIdCommand(stack, dbConn);
        ClearCommand clear = new ClearCommand(stack, dbConn);
        RemoveLowerCommand removeLower = new RemoveLowerCommand(stack, dbConn);
        //PrintDescDistCommand descDist = new PrintDescDistCommand(stack);
        //FilterGreaterDistCommand filterGreaterDist = new FilterGreaterDistCommand(stack);
        //AddIfMaxCommand addIfMax = new AddIfMaxCommand(stack);
        //GroupByFromCommand groupByFrom = new GroupByFromCommand(stack);
        //InsertAtCommand insertAt = new InsertAtCommand(stack, this);
        //UpdateCommand update = new UpdateCommand(stack);

        commands.put("start", defaultSize);
        commands.put("sort", new SortingCommand(collectionManager));
        commands.put(help.getName(), help);
        commands.put(info.getName(), info);
        commands.put(show.getName(), show);
        commands.put(exit.getName(), exit);
        commands.put(add.getName(), add);
        commands.put(removeById.getName(), removeById);
        commands.put(clear.getName(), clear);
        commands.put(removeLower.getName(), removeLower);
        /*
        commands.put(descDist.getName(), descDist);
        commands.put(filterGreaterDist.getName(), filterGreaterDist);
        commands.put(addIfMax.getName(), addIfMax);
        commands.put(groupByFrom.getName(), groupByFrom);
        commands.put(insertAt.getName(), insertAt);
        commands.put(update.getName(), update);
         */
    }

    /**
     * @return ArrayList of all commands (for 'help').
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public Response runCommand(String name, String args, String author) {
        return runCommand(new Request(name, args, author));
    }

    public Response runCommand(Request r) {
        if (commands.containsKey(r.getCommandName())) {
            return commands.get(r.getCommandName()).execute(r.getArgs());
        } else {
            return new Response("ERROR: " + r.getCommandName() + " command doesn't exist");
        }
    }
}
