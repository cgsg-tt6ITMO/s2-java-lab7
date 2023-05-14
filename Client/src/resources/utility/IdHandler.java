/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.utility;

/**
 * Generates correct id.
 */
public class IdHandler {
    private static Long lastId;

    public IdHandler() {
        lastId = 0L;
    }

    public IdHandler(Long id) {
        lastId = id;
    }

    /**
     * @return id of last element in collection (so that the new id should be lastId + 1).
     */
    public Long getLastId() {
        return lastId;
    }

    /**
     * Changes the id of last element.
     * @param Id id of new last element.
     */
    public void setLastId(Long Id) {
        lastId = Id;
    }
}
