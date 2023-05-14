/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package resources.task;

import resources.exceptions.ValidateException;
import client.validators.Validator;

import java.util.Objects;

/**
 * Stores Location data.
 */
public class Location {
    private float x;
    private Float y; // not null
    private long z;
    private String name; // not null, not empty

    /**
     * For 'group_counting_by_from' method.
     */
    @Override
    public String toString() {
        return name + " " + x + " " + y + " " + z;
    }

    /**
     * Default Location constructor.
     */
    public Location() {
        this(0, (float)0.010, 0, "default location");
    }

    /**
     * Location constructor of 4 arguments.
     * First three arguments are coordinates.
     * @param X - abscissa;
     * @param Y - ordinate; (not null)
     * @param Z - applicate;
     * @param nm - name of the location. (not null)
     */
    public Location(float X, Float Y, long Z, String nm) throws ValidateException {
        setX(X);
        setY(Y);
        setZ(Z);
        setName(nm);
    }

    public Location setX(float x) {
        this.x = x;
        return this;
    }

    public float getX() {
        return x;
    }

    /**
     * Safe setting Y.
     */
    public Location setY(Float y) throws ValidateException {
        Validator<Float> notNullVal = new Validator<>(Objects::nonNull, "Location: Y is null");
        this.y = notNullVal.validate(y);
        return this;
    }

    public Float getY() {
        return y;
    }

    public Location setZ(long z) {
        this.z = z;
        return this;
    }

    public long getZ() {
        return z;
    }

    /**
     * In case of incorrect input offers you to re-client.input.
     */
    public Location setName(String name) throws ValidateException {
        Validator<String> strVal = new Validator<>((str) -> (str != null && !str.equals("")), "String is null or empty");
        this.name = strVal.validate(name);
        return this;
    }

    public String getName() {
        return name;
    }
}
