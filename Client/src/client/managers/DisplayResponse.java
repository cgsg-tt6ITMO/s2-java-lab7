/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client.managers;

import resources.utility.Deserializer;
import resources.utility.Response;

import java.net.ConnectException;

public class DisplayResponse {

    private static void displaySingleResponse(Response response) throws ConnectException {
        System.out.println(response.getMessage());
        if (response.getMessage().equals("EXIT...\n")) {
            throw new ConnectException("exit");
        }
    }

    public static void display(byte [] arr) throws ConnectException {
        String answ = new String(arr).trim();
        if (answ.charAt(0) == '[') {
            for (Response r : Deserializer.responses(answ)) {
                displaySingleResponse(r);
            }
        } else {
            displaySingleResponse(Deserializer.readResp(answ));
        }
    }
}
