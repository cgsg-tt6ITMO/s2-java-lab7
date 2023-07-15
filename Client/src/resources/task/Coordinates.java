/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.task;

import resources.exceptions.ValidateException;
import client.validators.Validator;

import java.util.Objects;

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

    public Coordinates(Double X, float Y) throws ValidateException {
        this.setX(X).setY(Y);
    }

    public Coordinates setX(Double x) throws ValidateException {
        Validator<Double> notNullVal = new Validator<>(Objects::nonNull, "Coordinates: X is null");
        this.x = notNullVal.validate(x);
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
