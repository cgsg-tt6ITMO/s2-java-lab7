/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Response;

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
    public Response execute(String args) {
        return new Response("EXIT...\n");
    }
}
