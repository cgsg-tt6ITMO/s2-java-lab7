/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

/**
 * Class that makes an object of request.
 * @param name - name of command.
 * @param args - arguments for the command.
 */
public record Request(String name, String args) {}
