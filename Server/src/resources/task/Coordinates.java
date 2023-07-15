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

    public Coordinates(Double X, float Y) {
        this.setX(X).setY(Y);
    }

    public Coordinates setX(Double x) {
        this.x = x;
        return this;
    }

    public Coordinates setY(float y) {
        this.y = y;
        return this;
    }

    public Double getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
