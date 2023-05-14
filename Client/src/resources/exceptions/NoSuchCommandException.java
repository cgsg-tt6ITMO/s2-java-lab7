/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.exceptions;

/**
 * Thrown while unknown command was called.
 */
public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
