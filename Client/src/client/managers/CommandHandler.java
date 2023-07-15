/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers;

import client.managers.execute_script.ExecuteScript;
import client.validators.ValidatorManager;
import resources.exceptions.NoSuchCommandException;
import resources.utility.IdHandler;
import resources.utility.Request;
import resources.utility.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class that inputs commands with their arguments and generates requests.
 */
public class CommandHandler {
    private final AskInputManager im;
    private final Scanner sc;
    private final IdHandler idHandler;
    private final String author;

    /**
     * Default constructor.
     */
    public CommandHandler(Scanner sc, int size, String author) {
        this.idHandler = new IdHandler((long) size);
        this.im = new AskInputManager(sc, idHandler);
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
                    r = new Request(command, Serializer.objSer(im.inpRoute()), author);
            case "remove_by_id" -> r = new Request(command, Serializer.longSer(im.inpLong("id", v.idValidator())), author);
            case "update", "insert_at" ->
                    r = new Request(command, Serializer.longRouteSer(im.inpLong("id", v.idValidator()), im.inpRoute()), author);
            case "filter_greater_than_distance" ->
                    r = new Request(command, Serializer.doubleSer(im.inpDouble("distance", v.distanceValidator())), author);
            case "execute_script" -> {
                System.out.println("Need to remake this. Not available.");
                System.exit(6);
                return null;
            }
            default -> throw new NoSuchCommandException(command + " doesn't exist.");
        }
        return r;
    }
}
