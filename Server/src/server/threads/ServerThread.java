/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.threads;

import java.util.Scanner;

/**
 * Is made to exit server from console.
 */
public class ServerThread extends Thread {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String line = sc.nextLine().trim().toLowerCase();
            if (line.equals("exit")) {
                System.exit(5);
            }
        }
    }
}
