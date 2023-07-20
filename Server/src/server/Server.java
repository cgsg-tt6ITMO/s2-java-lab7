/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server;

import server.threads.RequestThread;
import server.threads.ServerThread;

/**
 * Server. Handles command logic.
 */
public class Server {
    /**
     * Makes threads.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        RequestThread requestThread = new RequestThread();
        ServerThread serverThread = new ServerThread();
        requestThread.start();
        serverThread.start();
    }
}
