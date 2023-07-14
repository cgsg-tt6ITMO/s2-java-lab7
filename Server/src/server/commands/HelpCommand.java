/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Arguments;
import resources.utility.Response;

import java.util.HashMap;

/**
 * Handle 'help' method.
 */
public class HelpCommand extends AbstractCommand implements Command {
    private final HashMap<String, Command> commands;

    /**
     * Set name and description for 'help' command.
     * @param commands - HashMap of commands (name of the command, command class).
     */
    public HelpCommand(HashMap<String, Command> commands) {
        super("help", "prints list of all commands and their descriptions;");
        this.commands = commands;
    }

    /**
     * Prints all commands and their descriptions.
     */
    @Override
    public Response execute(Arguments args) {
        StringBuilder sb = new StringBuilder("COMMANDS AVAILABLE:\n");
        for (var key : commands.keySet()) {
            if (commands.get(key) instanceof AbstractCommand) {
                sb.append(commands.get(key)).append("\n");
            }
        }
        sb.append("execute_script - runs input data from a script file.\n\n");
        return new Response(new String(sb));
    }
}
