/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

import resources.utility.Arguments;
import resources.utility.Response;

import java.time.ZonedDateTime;

/**
 * Handle 'info' method.
 */
public class InfoCommand extends AbstractCommand implements Command{
    private final ZonedDateTime date;
    private final String type;
    private final int size;

    /**
     * Set name and description for 'info' command.
     * @param creationDate - date of creation.
     * @param collectionType - type of the collection.
     * @param collectionSize - size of the collection.
     */
    public InfoCommand(ZonedDateTime creationDate, String collectionType, int collectionSize) {
        super("info", "prints info about the collection;");
        this.date = creationDate;
        this.type = collectionType;
        this.size = collectionSize;
    }

    /**
     * Prints to screen all the attributes of the collection.
     */
    @Override
    public Response execute(Arguments args) {
        return new Response(("COLLECTION INFO:\n"
                + "creation time: " + date.getHour() + ":" + (date.getMinute() > 9 ? date.getMinute() : "0" + date.getMinute())
                + "\ntype of storage: " + type
                + "\nnumber of elements: " + size + "\n\n"));
    }
}
