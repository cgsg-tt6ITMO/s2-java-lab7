/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

/**
 * Class of Responses -- objects of server's answer.
 */
public class Response {
    private final Status status;
    private final String message;

    public Response() {
        this.status = Status.ERROR;
        this.message = "";
    }

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
}
