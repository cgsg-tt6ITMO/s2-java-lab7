/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Response;
import server.managers.CommandManager;

/**
 * Handle 'help' method.
 */
public class HelpCommand extends AbstractCommand implements Command {
    private final CommandManager commandManager;

    /**
     * Set name and description for 'help' command.
     * @param cm - CommandManager.
     */
    public HelpCommand(CommandManager cm) {
        super("help", "prints list of all commands and their descriptions;");
        this.commandManager = cm;
    }

    /**
     * Prints all commands and their descriptions.
     */
    @Override
    public Response execute(String args) {
        StringBuilder sb = new StringBuilder("COMMANDS AVAILABLE:\n");

        for (var key : commandManager.getCommands().keySet()) {
            if (commandManager.getCommands().get(key) instanceof AbstractCommand) {
                sb.append(commandManager.getCommands().get(key)).append("\n");
            }
        }
        sb.append("execute_script - runs input data from a script file.\n\n");
        return new Response(new String(sb));
    }
}
