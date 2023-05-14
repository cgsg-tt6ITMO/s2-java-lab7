/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server;

import resources.utility.Deserializer;
import resources.utility.Request;
import resources.utility.Response;
import resources.utility.Serializer;
import server.managers.CommandManager;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.rmi.ConnectIOException;
import java.util.ArrayList;

/**
 * Server. Handles command logic.
 */
public class Server {
    private static byte[] arr = new byte[8192];
    private static final int MAX_NUM_COMMANDS = 100;

    /**
     * Default constructor.
     */
    public Server() {}

    /**
     * Gets requests, handles them and sends responses.
     * Does all the manipulation with the collection.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        int q = MAX_NUM_COMMANDS;
        InetAddress host;
        int port = 6000;
        SocketAddress addr;
        SocketChannel sock;
        ServerSocketChannel serv;
        ByteBuffer buf;
        CommandManager commandManager = new CommandManager();

        boolean loop = true;
        do {
            try {
                host = InetAddress.getLocalHost();
                addr = new InetSocketAddress(host, port);
                serv = ServerSocketChannel.open();
                serv.bind(addr);
                sock = serv.accept();

                while (q > 0) {
                    // get data from client
                    buf = ByteBuffer.wrap(arr);
                    sock.read(buf);
                    // data deserialization & command execution

                    // TODO: сделать так, чтоб без start не начинали
                    if (arr[0] == '[') {
                        Request[] reqs = Deserializer.readArr(new String(arr));
                        ArrayList<Response> response = new ArrayList<>();
                        for (Request r : reqs) {
                            if (r != null) {
                                Response res = commandManager.runCommand(r);
                                response.add(res);
                            }
                        }
                        arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                    } else {
                        Request r = Deserializer.readReq(new String(arr));
                        Response response = commandManager.runCommand(r.name(), r.args());
                        arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                    }

                    // send response to the client
                    buf = ByteBuffer.wrap(arr);
                    sock.write(buf);
                    arr = new byte[8192];
                    q--;
                }
                sock.close();
            } catch (UnknownHostException e) {
                System.err.println(e.getMessage());
            } catch (ConnectIOException e) {
                System.err.println("ConnectIOException");
            } catch (BindException e) {
                port += 1;
            } catch (IOException e) {
                System.out.println("Probably, one client was disconnected...");
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("ERROR...");
                e.printStackTrace();
            }
        } while (loop);
    }
}
