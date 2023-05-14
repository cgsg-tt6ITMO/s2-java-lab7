/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.exceptions;

/**
 * Infinite loop in 'execute_script'.
 */
public class InfiniteLoopException extends RuntimeException {
    /**
     * @param message place (method / class / file) where the exception occurs.
     */
    public InfiniteLoopException(String message) {
        super(message + ": infinite loop.");
    }
}
