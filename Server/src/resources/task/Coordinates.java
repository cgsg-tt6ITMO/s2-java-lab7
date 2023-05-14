/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.task;

/**
 * Coordinates class due to the task.
 */
public class Coordinates {
    private Double x; // not null
    private float y;

    /**
     * Default constructor for jackson.
     */
    public Coordinates() {}

    /**
     * Coordinates constructor.
     * @param X abscissa (!= null);
     * @param Y ordinate.
     */
    public Coordinates(Double X, float Y) {
        setX(X);
        y = Y;
    }

    /**
     * Sets X.
     */
    public Coordinates setX(Double x) {
        this.x = x;
        return this;
    }

    /**
     * @param y - ordinate to set.
     */
    public Coordinates setY(float y) {
        this.y = y;
        return this;
    }

    /**
     * @return abscissa.
     */
    public Double getX() {
        return x;
    }

    /**
     * Needed for jackson.
     * @return ordinate of the point.
     */
    public float getY() {
        return y;
    }
}
