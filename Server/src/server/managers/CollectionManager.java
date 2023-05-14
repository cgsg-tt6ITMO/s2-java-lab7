/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.managers;

import resources.task.Route;
import server.handlers.Loader;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Stores the collection and info about it.
 */
public class CollectionManager {
    private static Stack<Route> stack = new Stack<>();
    private final String collectionType;
    private final ZonedDateTime creationDate;
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
    public String getCollectionType() {
        return collectionType;
    }
    public Stack<Route> stack() {
        return stack;
    }

    /**
     * Collection initialization.
     */
    public CollectionManager(Loader loader) {
        collectionType = "Stack";
        creationDate = ZonedDateTime.now();
        stack = loader.load();
    }
}
