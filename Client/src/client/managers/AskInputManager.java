/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers;

import client.validators.*;
import resources.exceptions.ValidateException;
import resources.task.Coordinates;
import resources.task.Location;
import resources.task.Route;
import resources.utility.IdHandler;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Inputs data, gently reminding the client about what variable they are supposed to input,
 *   and it's type and providing re-input in case of wrong input.
 */
public class AskInputManager {
    private Scanner sc;
    private final IdHandler idHandler;
    ValidatorManager v = new ValidatorManager();

    /**
     * Input manager initialization.
     * @param scanner - scanner from which to input (console, file, etc).
     */
    public AskInputManager(Scanner scanner, IdHandler idHandler) {
        setScanner(scanner);
        this.idHandler = idHandler;
    }

    /**
     * Changes the scanner from which we input.
     * @param scanner - new scanner.
     */
    public void setScanner(Scanner scanner) {
        this.sc = scanner;
    }

    /**
     * Inputs Route.
     * @return the Route inputted.
     */
    public Route inpRoute() {
        System.out.println("Input route data");
        idHandler.setLastId(idHandler.getLastId() + 1);
        try {
            return new Route()
                    .setId(idHandler.getLastId())
                    .setName(inpString("Name", v.stringValidator()))
                    .setCoordinates(inpCoordinates("Coordinates (Double X, Float Y)"))
                    .setFrom(inpLocation("Location from (Float X, Float Y, Long Z, String name)"))
                    .setTo(inpLocation("Location to (Float X, Float Y, Long Z, String name)"))
                    .setDistance();
        } catch (ValidateException validateException) {
            return inpRoute();
        }
    }

    /**
     * @param variableName name of variable.
     * @return inputted Coordinates.
     */
    public Coordinates inpCoordinates(String variableName) {
        System.out.println(variableName);
        try {
            return new Coordinates()
                    .setX(inpDouble("X", v.doubleNotNull()))
                    .setY(inpFloat("Y", v.floatNoVal()));
        } catch (ValidateException validateException) {
            return inpCoordinates("Input " + variableName + " again:");
        }
    }

    /**
     * @param variableName name of variable.
     * @return inputted Location.
     */
    public Location inpLocation(String variableName) {
        System.out.println(variableName);
        try {
            return new Location()
                    .setX(inpFloat("X", v.floatNoVal()))
                    .setY(inpFloat("Y", v.floatNotNull()))
                    .setZ(inpLong("Z", v.longNoVal()))
                    .setName(inpString("name", v.stringValidator()));
        } catch (ValidateException | NumberFormatException | InputMismatchException e) {
            return inpLocation("Input " + variableName + " again:");
        }
    }

    public Double inpDouble(String variableName, Validator<Double> validator) {
        System.out.println(variableName + " (Float)");
        try {
            return validator.validate(Double.parseDouble(sc.nextLine()));
        } catch (ValidateException | NumberFormatException | InputMismatchException e) {
            return inpDouble("Input " + variableName + " again", validator);
        }
    }

    public Float inpFloat(String variableName, Validator<Float> validator) {
        System.out.println(variableName + " (Float)");
        try {
            return validator.validate(Float.parseFloat(sc.nextLine()));
        } catch (ValidateException | NumberFormatException | InputMismatchException e) {
            return inpFloat("input again" + variableName, validator);
        }
    }

    public Long inpLong(String variableName, Validator<Long> validator) {
        System.out.println(variableName + " (Long)");
        try {
            return validator.validate(Long.parseLong(sc.nextLine()));
        } catch (ValidateException | NumberFormatException | InputMismatchException e) {
            return inpLong("Input " + variableName + " again", validator);
        }
    }

    public String inpString(String variableName, Validator<String> validator) {
        System.out.println(variableName + " (String)");
        try {
            return validator.validate(sc.nextLine());
        } catch (ValidateException validateException) {
            return inpString("Input " + variableName + " again", validator);
        }
    }

    /**
     * @return description for the AskInputManager.
     */
    @Override
    public String toString() {
        return "Input manager which prints tips for inputting data";
    }
}
