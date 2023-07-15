/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package client;

import client.managers.AskInputManager;
import client.managers.CommandHandler;
import client.managers.DisplayResponse;
import resources.exceptions.NoSuchCommandException;
import resources.utility.Request;
import resources.utility.Response;
import resources.utility.Serializer;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Function;

/**
 * This class handles the commands that the client inputs in loop.
 */
public class Client {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Auxiliary method that sends request and displays response for one command.
     * @param request request, based on the command.
     * @param sock socket channel that sends and receives data.
     * @throws IOException socket problems, connection lost etc.
     */
    private static Response oneCommand(Request request, SocketChannel sock) throws IOException {
        Function<Object, byte[]> objToByte = (obj) -> (Serializer.objSer(obj).getBytes(StandardCharsets.UTF_8));

        try {
            byte[] arr = objToByte.apply(request);
            ByteBuffer buf = ByteBuffer.wrap(arr);
            sock.write(buf);
            buf.clear();
            buf = ByteBuffer.allocate(8192);
            sock.read(buf);
            return DisplayResponse.display(buf.array());
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
        return new Response("Error...");
    }

    /**
     * Sends requests and gets responses from the server.
     * Displays server Responses.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        InetAddress host;
        // need to change the number after client disconnection
        int port = 8080;
        SocketAddress addr;
        CommandHandler commandHandler;

        try (SocketChannel sock = SocketChannel.open()) {
            host = InetAddress.getLocalHost();
            addr = new InetSocketAddress(host, port);
            sock.connect(addr);

            String author = "";
            boolean isLogin = false;
            /* Register or login until you are not logged in */
            do {
                Request logOrReg = AskInputManager.loginOrRegister(sc);
                Response response = oneCommand(logOrReg, sock);
                if (logOrReg.getCommandName().equals("login")) {
                    if (response.getMessage().equals("Login success!")) {
                        isLogin = true;
                        author = logOrReg.getArgs().getAuthor();
                    }
                }
            } while (!isLogin);

            commandHandler = new CommandHandler(sc, author);
            System.out.println("Type command name...");
            while (sc.hasNext()) {
                try {
                    oneCommand(commandHandler.run(), sock);
                } catch (NoSuchCommandException e) {
                    System.err.println(e.getMessage() + " (re-input)");
                }
            }
        } catch (UnknownHostException | SocketException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Throwable t) {
            System.err.println("ERROR");
        }
    }
}
