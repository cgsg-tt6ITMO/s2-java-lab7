/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers.execute_script;

import client.managers.AskInputManager;
import client.managers.CommandHandler;
import resources.exceptions.ExecuteScriptException;
import resources.exceptions.ValidateException;
import client.validators.ValidatorManager;
import resources.exceptions.InfiniteLoopException;
import resources.utility.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This command needs its own class because it just calls other commands.
 * It does not send its own requests, only commands written inside do.
 */
public class ExecuteScript {
    private static AskInputManager inputManager;
    private final ExecuteScriptHandler handler = new ExecuteScriptHandler();
    private final String author;

    public ExecuteScript(AskInputManager inpMan, String author) {
        inputManager = inpMan;
        this.author = author;
    }

    public ArrayList<Request> readRequestArray() {
        ArrayList<Request> reqs = new ArrayList<>();
        ValidatorManager v = new ValidatorManager();

        boolean loop, wasErr = false;
        do {
            try {
                // input path
                if (wasErr) {
                    System.err.println("execute script: input filename again:");
                }
                String path = inputManager.inpString("script file name", v.pathValidator());
                // check infinite loop
                if (handler.getFiles().isEmpty()) {
                    handler.fadd(path);
                } else {
                    if (!handler.getFiles().contains(path)) {
                        handler.fadd(path);
                    } else {
                        // if path in files, exception
                        throw new InfiniteLoopException("execute_script");
                    }
                }
                // make Requests
                File file = new File(path);
                Scanner fileScanner = new Scanner(file);
                CommandHandler commandHandler = new CommandHandler(fileScanner, author);
                while (fileScanner.hasNext()) {
                    try {
                        Request st = commandHandler.run();
                        reqs.add(st);
                    }
                    // in execute_script other execute_script met
                    catch (ExecuteScriptException e) {
                        ExecuteScript executeScript = new ExecuteScript(inputManager, author);
                        ArrayList<Request> executeScriptRequests = executeScript.readRequestArray();
                        for (var el : executeScriptRequests) {
                            reqs.add(el);
                        }
                    }
                }
                loop = false;
            } catch (ValidateException | FileNotFoundException e) {
                loop = true;
                wasErr = true;
            }
            catch (InfiniteLoopException e) {
                System.err.println(e.getMessage());
                loop = false;
            }
        } while (loop);
        return reqs;
    }
}
