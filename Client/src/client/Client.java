/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client;

import client.managers.CommandHandler;
import client.managers.DisplayResponse;
import resources.exceptions.NoSuchCommandException;
import resources.utility.Deserializer;
import resources.utility.Request;
import resources.utility.Response;
import resources.utility.Serializer;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class handles the commands that the client inputs in loop.
 */
public class Client {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Sends requests and gets responses from the server.
     * Displays server Responses.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        InetAddress host;
        // need to change the number after client disconnection
        int port = 6000;
        SocketAddress addr;
        byte[] arr;
        ByteBuffer buf;
        CommandHandler commandHandler = new CommandHandler(sc, 0);

        try (SocketChannel sock = SocketChannel.open()) {
            host = InetAddress.getLocalHost();
            addr = new InetSocketAddress(host, port);
            sock.connect(addr);
            System.out.println("Type 'start' to begin...");

            if (sc.nextLine().equals("start")) {
                Request r = new Request("start", "");
                arr = Serializer.objSer(r).getBytes(StandardCharsets.UTF_8);
                buf = ByteBuffer.wrap(arr);
                sock.write(buf);

                buf.clear();
                buf = ByteBuffer.allocate(8192);
                sock.read(buf);

                Response resp = Deserializer.readResp(new String(buf.array()));
                try {
                    commandHandler = new CommandHandler(sc, Integer.parseInt(resp.getMessage()));
                    System.out.println("Start successful");
                } catch (NumberFormatException numberFormatException) {
                }
                buf.clear();
            }
            while (sc.hasNext()) {
                try {
                    arr = commandHandler.run();
                    buf = ByteBuffer.wrap(arr);
                    sock.write(buf);
                    buf.clear();
                    buf = ByteBuffer.allocate(8192);
                    sock.read(buf);
                    DisplayResponse.display(buf.array());
                    buf.clear();
                } catch (UnknownHostException e) {
                    System.err.println(e.getMessage());
                } catch (ConnectException e) {
                    sock.close();
                    throw new ConnectException("Client activity was terminated...");
                } catch (NoSuchCommandException e) {
                    System.err.println(e.getMessage() + " (try again)");
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException | ConnectException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchCommandException e) {
            System.err.println(e.getMessage() + " (re-input)");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
