/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers.execute_script;

import java.util.ArrayList;

/**
 * Needed to avoid infinite loop of scripts.
 * Stores an array of all used scripts in this script.
 */
public class ExecuteScriptHandler {
    private final ArrayList<String> files = new ArrayList<>();

    /**
     * Default constructor.
     */
    public ExecuteScriptHandler() {}

    /**
     * @return array of paths with scripts we met on our way.
     */
    public ArrayList<String> getFiles() {
        return files;
    }

    /**
     * Adds a path to array of scripts we used for our work.
     * @param path a path to the script and it's name.
     */
    public void fadd(String path) {
        this.files.add(path);
    }

    /**
     * Makes the array of scripts we met empty.
     * Purpose: if we call one script second time while running the program,
     *     it would throw InfiniteLoopException though it should not.
     */
    public void fclear() {
        this.files.clear();
    }

}
