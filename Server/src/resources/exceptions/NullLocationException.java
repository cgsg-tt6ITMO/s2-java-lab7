/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.exceptions;

/**
 * Null-Location set in Route.
 * Probably, it should become a Validator...
 */
public class NullLocationException extends RuntimeException {
    /**
     * @param message place (method / class / file) where the exception occurs.
     */
    public NullLocationException(String message) {
        super(message + ": the resourses.Location equals null.");
    }
}
