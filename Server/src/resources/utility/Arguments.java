/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

/**
 * Class that stores arguments for Request class.
 */
public class Arguments {
    private final String data;
    private final String author;

    public Arguments() {
        this.data = "";
        this.author = "unknown";
    }
    public Arguments(String data, String author) {
        this.data = data;
        this.author = author;
    }

    public String getData() {
        return data;
    }

    public String getAuthor() {
        return author;
    }
}
