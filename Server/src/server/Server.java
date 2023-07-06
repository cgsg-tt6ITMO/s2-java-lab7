/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server;

import resources.utility.Deserializer;
import resources.utility.Request;
import resources.utility.Response;
import resources.utility.Serializer;
import server.databases.DBConnection;
//import server.databases.DBInitialization;
import server.managers.CommandManager;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_ACCEPT;

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

    public static void main(String[] args) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection dbConn = dbConnection.connect();
        //DBInitialization dbInit = new DBInitialization(dbConn);
        //dbInit.initialize();
    }

    /**
     * Gets requests, handles them and sends responses.
     * Does all the manipulation with the collection.
     * @param args cmd arguments.
     */
    public static void main1(String[] args) {
        int q = MAX_NUM_COMMANDS;
        InetAddress host;
        int port = 8080;
        SocketAddress addr;
        ServerSocketChannel serv;
        SocketChannel sock;
        ByteBuffer buf;
        CommandManager commandManager = new CommandManager();
        Selector sel;

        try {
            host = InetAddress.getLocalHost();
            addr = new InetSocketAddress(host, port);
            serv = ServerSocketChannel.open();
            serv.bind(addr);
            sel = Selector.open();
            serv.configureBlocking(false);
            boolean loop = true;

            do {
                serv.register(sel, OP_ACCEPT);
                sel.select();
                Iterator<SelectionKey> iter = sel.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey k = iter.next();
                    if (k.isAcceptable()) {
                        System.out.println("Acceptable");
                        sock = serv.accept();
                        sock.configureBlocking(false);
                        sock.register(sel, SelectionKey.OP_READ);
                    }
                    else if (k.isReadable() && ((SocketChannel)k.channel()).read(ByteBuffer.wrap(arr)) != -1) {
                        sock = (SocketChannel) k.channel();
                        System.out.println("Readable");
                        buf = ByteBuffer.wrap(arr);
                        sock.read(buf);
                        if (arr[0] == '[') {
                            Request[] reqs = Deserializer.readArr(new String(arr));
                            ArrayList<Response> response = new ArrayList<>();
                            for (Request r : reqs) {
                                Response res = commandManager.runCommand(r);
                                response.add(res);
                            }
                            arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                        } else {
                            Request r = Deserializer.readReq(new String(arr));
                            Response response = commandManager.runCommand(r.name(), r.args());
                            arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                        }
                        buf = ByteBuffer.wrap(arr);
                        //System.out.println(new String(arr));
                        sock.write(buf);
                        arr = new byte[8192];
                        sock.configureBlocking(false);
                        sock.register(sel, SelectionKey.OP_READ);
                    }
                    iter.remove();
                }
            } while (loop);

        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR...");
            e.printStackTrace();
        }
    }
}
