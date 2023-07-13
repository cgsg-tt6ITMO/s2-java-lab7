/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Arguments;
import resources.utility.Response;

/**
 * Interface of all server.commands.
 */
@FunctionalInterface
public interface Command {
    /**
     * Run the command.
     */
    Response execute(Arguments args);
}
