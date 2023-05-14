/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.validators;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Storage of frequently used validators.
 */
public class ValidatorManager {

    public ValidatorManager() {
    }

    public Validator<Double> distanceValidator() {
        return new Validator<>((value) -> (value > 1D), "Route: Incorrect distance.");
    }

    public Validator<Long> idValidator() {
        return new Validator<>((id) -> (id > 0), "Id less than zero");
    }
    public Validator<String> stringValidator() {
        return new Validator<>((str) -> (str != null && !str.equals("")), "String is null or empty");
    }
    public Validator<String> pathValidator() {
        return new Validator<>((path) -> (Files.exists(Paths.get(path))), "Incorrect file path");
    }

    // кал
    public Validator<Float> floatNoVal() {
        return new Validator<>((el) -> (true), "");
    }

    public Validator<Long> longNoVal() {
        return new Validator<>((el) -> (true), "");
    }

    public Validator<Float> floatNotNull() {
        return new Validator<>(Objects::nonNull, "Value is null");
    }

    public Validator<Double> doubleNotNull() {
        return new Validator<>(Objects::nonNull, "Value is null");
    }
}
