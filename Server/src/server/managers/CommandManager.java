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

    /**
     * Adds instances of all commands to list.
     */
    public CommandManager() {
        DBConnection dbConnection = new DBConnection();
        Connection dbConn = dbConnection.connect();
        DBInitialization dbInit = new DBInitialization(dbConn);
        Stack<Route> defaultStack = dbInit.initialize();
        CollectionManager collectionManager = new CollectionManager(defaultStack);
        Stack<Route> stack = collectionManager.stack();

        GetDefaultCollectionSize defaultSize = new GetDefaultCollectionSize(stack);
        HelpCommand help = new HelpCommand(this);
        InfoCommand info = new InfoCommand(collectionManager);
        ShowCommand show = new ShowCommand(stack, dbConn);
        ExitCommand exit = new ExitCommand();
        AddCommand add = new AddCommand(stack, dbConn);
        //ClearCommand clear = new ClearCommand(stack);
        //PrintDescDistCommand descDist = new PrintDescDistCommand(stack);
        //FilterGreaterDistCommand filterGreaterDist = new FilterGreaterDistCommand(stack);
        //AddIfMaxCommand addIfMax = new AddIfMaxCommand(stack);
        //GroupByFromCommand groupByFrom = new GroupByFromCommand(stack);
        //RemoveByIdCommand deleteById = new RemoveByIdCommand(stack);
        //RemoveLowerCommand removeLower = new RemoveLowerCommand(stack);
        //InsertAtCommand insertAt = new InsertAtCommand(stack, this);
        //UpdateCommand update = new UpdateCommand(stack);

        commands.put("start", defaultSize);
        commands.put("sort", new SortingCommand(collectionManager));
        commands.put(help.getName(), help);
        commands.put(info.getName(), info);
        commands.put(show.getName(), show);
        commands.put(exit.getName(), exit);
        commands.put(add.getName(), add);
        /*
        commands.put(clear.getName(), clear);
        commands.put(descDist.getName(), descDist);
        commands.put(filterGreaterDist.getName(), filterGreaterDist);
        commands.put(addIfMax.getName(), addIfMax);
        commands.put(groupByFrom.getName(), groupByFrom);
        commands.put(deleteById.getName(), deleteById);
        commands.put(removeLower.getName(), removeLower);
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

    public Response runCommand(String name, String args) {
        return runCommand(new Request(name, args));
    }

    public Response runCommand(Request r) {
        if (commands.containsKey(r.name())) {
            return commands.get(r.name()).execute(r.args());
        } else {
            return new Response("ERROR: " + r.name() + " command doesn't exist");
        }
    }

}
