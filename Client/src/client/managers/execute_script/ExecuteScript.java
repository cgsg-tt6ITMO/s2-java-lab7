/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers.execute_script;

import client.managers.AskInputManager;
import client.managers.CommandHandler;
import client.managers.execute_script.ExecuteScriptHandler;
import resources.exceptions.ValidateException;
import client.validators.ValidatorManager;
import resources.exceptions.InfiniteLoopException;
import resources.utility.Deserializer;
import resources.utility.Request;
import resources.utility.Serializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This command needs its own class because it just calls other commands.
 * It does not send its own requests, only commands written inside do.
 */
public class ExecuteScript {
    private static AskInputManager inputManager;
    private final ExecuteScriptHandler handler = new ExecuteScriptHandler();

    public ExecuteScript(AskInputManager inpMan) {
        inputManager = inpMan;
    }

    public String makeReq(int stackSize) {
        int numOfCommands = 100;
        Request[] reqs = new Request[numOfCommands];
        ValidatorManager v = new ValidatorManager();

        boolean loop = true, wasErr = false;
        do {
            try {
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

                File file = new File(path);
                Scanner fileScanner = new Scanner(file);
                for (int i = 0; i < numOfCommands; i++) {
                    if (fileScanner.hasNext()) {
                        stackSize += 1;
                        Request st = Deserializer.readReq(new String(
                                new CommandHandler(fileScanner, stackSize).run()));
                        reqs[i] = st;
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

        return Serializer.objSer(reqs);
    }
}
