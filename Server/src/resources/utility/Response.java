/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

/**
 * Class of Responses -- objects of server's answer.
 */
public class Response {
    private final String message;

    public Response() {
        this.message = "";
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
