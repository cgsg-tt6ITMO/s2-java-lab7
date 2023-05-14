/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.validators;

import resources.exceptions.ValidateException;

import java.util.function.Predicate;

public class Validator<T> {
    private final Predicate<T> val;
    private final String errorMessage;

    public Validator(Predicate<T> valid, String errorMessage) {
        this.val = valid;
        this.errorMessage = errorMessage;
    }
    public static Predicate<Double> dist = (distance) -> (distance > 1);

    public T validate(T value) {
        if (val.test(value)) {
            return value;
        }
        throw new ValidateException(errorMessage);
    }
}
