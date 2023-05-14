/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.task.Route;
import resources.utility.Response;
import server.managers.CommandManager;
import resources.utility.IdHandler;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Handle 'insert_at' method.
 */
public class InsertAtCommand extends AbstractCommand implements Command {
    private final Stack<Route> stack;
    private final CommandManager cm;

    /**
     * Set name and description for 'insert_at' command.
     * @param stack storage of the collection.
     */
    public InsertAtCommand(Stack<Route> stack, CommandManager commandManager) {
        super("insert_at", "insert an element into place of inputted id;");
        this.stack = stack;
        this.cm = commandManager;
    }

    /**
     * Inserts an element into a place of index you client.input.
     */
    @Override
    public Response execute(String args) {
        int size = stack.size();
        IdHandler idHandler = new IdHandler((long) size);

        Scanner scanner = new Scanner(args);
        String idStr = scanner.nextLine();
        scanner.close();
        long id = Long.parseLong(idStr);
        StringBuilder json = new StringBuilder();

        for (int i = idStr.length(); i < args.length(); i++) {
            json.append(args.charAt(i));
        }

        int n = stack.size();

        boolean loop = true;
        do {
            try {
                if (id > idHandler.getLastId()) {
                    idHandler.setLastId(id - 1);
                    cm.runCommand("add", json.toString());
                }
                else if (id < 1) {
                    System.err.println("insert_at: TODO надо кинуть Response ошибка Incorrect id: less than 1\n");
                    throw new InputMismatchException();
                } else {
                    boolean exist = false;
                    // check if this index exists
                    for (var el : stack) {
                        if (el.getId().equals(id)) {
                            exist = true;
                        }
                        if (exist) {
                            el.setId(el.getId() + 1);
                        }
                    }
                    cm.runCommand("add", json.toString());
                    stack.peek().setId(id);
                }
                loop = false;
            } catch (NumberFormatException | InputMismatchException exception){
                loop = true;
            }
        } while (loop);

        if (stack.size() > n) {
            cm.runCommand("sort", "");
            return new Response("INSERT AT:\nInserted successfully.\n");
        }
        cm.runCommand("sort", "");
        return new Response("INSERT AT:\n");
    }
}

