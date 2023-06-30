/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server;

import resources.utility.Deserializer;
import resources.utility.Request;
import resources.utility.Response;
import resources.utility.Serializer;
import server.databases.DBInit;
import server.managers.CommandManager;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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

    public static void main2(String[] args) throws SQLException {
        DBInit dbInit = new DBInit();
        dbInit.initialize();
    }

    /**
     * Gets requests, handles them and sends responses.
     * Does all the manipulation with the collection.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        int q = MAX_NUM_COMMANDS;
        InetAddress host;
        int port = 8080;
        SocketAddress addr;
        ServerSocketChannel serv;
        SocketChannel sock = null;
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
            boolean loop = true, nonExit;

            // while количество клиентов по идее меньше какого-то фикс. числа
            do {
                System.out.println("new do");
                serv.register(sel, OP_ACCEPT);
                // новый сокет под нового клиента
                sock = serv.accept();
                nonExit = true;
                sel.select();
                Set<SelectionKey> selectedKeys = sel.selectedKeys();
                Iterator<SelectionKey> iter = sel.selectedKeys().iterator();
                //for (Iterator<SelectionKey> it = iter; it.hasNext(); ) {
                //for (SelectionKey k : selectedKeys) {
                while (iter.hasNext()) {
                    SelectionKey k = iter.next();
                    if (k.isAcceptable()) {
                        System.out.println("Acceptable");
                        sock = serv.accept();
                        //System.out.println(sock == null);
                        sock.configureBlocking(false);
                        sock.register(sel, SelectionKey.OP_READ);

                    }
                    //System.out.println(sock == null);
                    else if (k.isReadable()) {// && sock != null) {
                        sock = (SocketChannel) k.channel();
                        System.out.println("Readable");
                        //while (q > 0 && nonExit) {
                            buf = ByteBuffer.wrap(arr);
                            sock.read(buf);
                            if (arr[0] == '[') {
                                Request[] reqs = Deserializer.readArr(new String(arr));
                                ArrayList<Response> response = new ArrayList<>();
                                for (Request r : reqs) {
                                    if (r.name().equals("exit")) {
                                        nonExit = false;
                                    }
                                    Response res = commandManager.runCommand(r);
                                    response.add(res);
                                }
                                arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                            } else {
                                Request r = Deserializer.readReq(new String(arr));
                                if (r.name().equals("exit")) {
                                    nonExit = false;
                                }
                                Response response = commandManager.runCommand(r.name(), r.args());
                                arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                            }

                            buf = ByteBuffer.wrap(arr);
                        System.out.println(new String(arr));
                            sock.write(buf);
                            arr = new byte[8192];
                        sock.configureBlocking(false);
                        sock.register(sel, SelectionKey.OP_READ);
                            //q--;
                        //}
                        //sock.close();
                    }
                    else if (k.isWritable()) {
                        System.out.println("Writable");
                    }
                    else if (k.isConnectable()) {
                        System.out.println("Connectable");
                    }
                    iter.remove();
                }
                // нужно сделать пробежку по всем элементам iter если он acceptable, делать следующую строчку
                // и вообще надо разибть код на acceptable и readable, но write должен сразу туда куда и read
                //sock = serv.accept();
                // while количество команд меньше какого-то числа и не было exit
                /*
                while (q > 0 && nonExit) {
                    buf = ByteBuffer.wrap(arr);
                    sock.read(buf);
                    if (arr[0] == '[') {
                        Request[] reqs = Deserializer.readArr(new String(arr));
                        ArrayList<Response> response = new ArrayList<>();
                        for (Request r : reqs) {
                            if (r.name().equals("exit")) {
                                nonExit = false;
                            }
                            Response res = commandManager.runCommand(r);
                            response.add(res);
                        }
                        arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                    } else {
                        Request r = Deserializer.readReq(new String(arr));
                        if (r.name().equals("exit")) {
                            nonExit = false;
                        }
                        Response response = commandManager.runCommand(r.name(), r.args());
                        arr = Serializer.objSer(response).getBytes(StandardCharsets.UTF_8);
                    }

                    buf = ByteBuffer.wrap(arr);
                    sock.write(buf);
                    arr = new byte[8192];
                    q--;
                }
                sock.close();*/
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
