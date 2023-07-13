/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

/**
 * Class that makes an object of request.
 */
public class Request {
    private String commandName;
    private Arguments args;

    public String getCommandName() {
        return commandName;
    }

    public Arguments getArgs() {
        return args;
    }

    public Request() {
        this("error", "", "unknown");
    }

    public Request(String commandName, String data, String author) {
        this.commandName = commandName;
        this.args = new Arguments(data, author);
    }
}
