/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.commands;

/**
 * Every command's parent class.
 */
public class AbstractCommand {
    private final String name;
    private final String description;

    /**
     * Sets name and description of the command.
     * @param name - name of the command.
     * @param description - description of the command.
     */
    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return  name of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * @return description of the command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return information about the command for 'help'.
     */
    @Override
    public String toString() {
        return name + " - " + description;
    }
}
