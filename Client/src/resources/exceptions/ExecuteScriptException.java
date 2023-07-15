/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.exceptions;

/**
 * Is thrown when execute_script command is met.
 */
public class ExecuteScriptException extends RuntimeException {
    public ExecuteScriptException() {
        super("Execute script command met.");
    }
}
