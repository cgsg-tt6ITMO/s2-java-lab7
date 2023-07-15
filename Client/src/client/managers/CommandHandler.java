/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers;

import client.validators.ValidatorManager;
import resources.exceptions.ExecuteScriptException;
import resources.exceptions.NoSuchCommandException;
import resources.utility.Request;

import java.util.Scanner;

/**
 * Class that inputs commands with their arguments and generates requests.
 */
public class CommandHandler {
    private final AskInputManager im;
    private final Scanner sc;
    private final String author;

    /**
     * Default constructor.
     */
    public CommandHandler(Scanner sc, String author) {
        this.im = new AskInputManager(sc);
        this.sc = sc;
        this.author = author;
    }

    /**
     * Method that handles input logic.
     * It handles the number of arguments command needs.
     */
    public Request run() {
        ValidatorManager v = new ValidatorManager();
        Request r;
        String command = sc.nextLine();
        switch (command) {
            case "group_counting_by_from",
                    "help", "info", "show",
                    "print_field_descending_distance",
                    "clear", "exit" -> {
                r = new Request(command, "", author);
            }
            case "add", "remove_lower", "add_if_max" ->
                    r = new Request(command, SerializationManager.objSer(im.inpRoute()), author);
            case "remove_by_id" -> r = new Request(command, ""+im.inpLong("id", v.idValidator()), author);
            case "update", "insert_at" ->
                    r = new Request(command, SerializationManager.longRouteSer(im.inpLong("id", v.idValidator()), im.inpRoute()), author);
            case "filter_greater_than_distance" ->
                    r = new Request(command, ""+im.inpDouble("distance", v.distanceValidator()), author);
            case "execute_script" -> {
                throw new ExecuteScriptException();
            }
            default -> throw new NoSuchCommandException(command + " doesn't exist.");
        }
        return r;
    }
}
