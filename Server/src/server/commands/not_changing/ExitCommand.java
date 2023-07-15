/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands.not_changing;

import resources.utility.Arguments;
import resources.utility.Response;
import server.commands.auxilary.AbstractCommand;
import server.commands.auxilary.Command;

/**
 * Handle 'exit' command.
 */
public class ExitCommand extends AbstractCommand implements Command {

    /**
     * Sets name and description of the command.
     */
    public ExitCommand() {
        super("exit", "interrupts the program without saving;");
    }

    /**
     * Aborts the program. And saves data before aborting.
     */
    @Override
    public Response execute(Arguments args) {
        return new Response("EXIT...\n");
    }
}
