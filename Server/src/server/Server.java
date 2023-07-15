/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server;

import server.managers.SerializationManager;
import resources.utility.Request;
import resources.utility.Response;
import server.managers.CollectionManager;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_ACCEPT;

/**
 * Server. Handles command logic.
 */
public class Server {
    /**
     * Gets requests, handles them and sends responses.
     * Does all the manipulation with the collection.
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        byte[] arr = new byte[8192];
        InetAddress host;
        int port = 8080;
        SocketAddress addr;
        SocketChannel sock;
        ByteBuffer buf;
        CollectionManager commandManager = new CollectionManager();
        Selector sel;

        try (ServerSocketChannel serv = ServerSocketChannel.open()) {
            host = InetAddress.getLocalHost();
            addr = new InetSocketAddress(host, port);
            serv.bind(addr);
            sel = Selector.open();
            serv.configureBlocking(false);
            boolean serverWorks = true;

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
                        Request r = SerializationManager.readReq(new String(arr));
                        Response response = commandManager.runCommand(r);
                        arr = SerializationManager.objSer(response).getBytes(StandardCharsets.UTF_8);
                        buf = ByteBuffer.wrap(arr);
                        sock.write(buf);
                        arr = new byte[8192];
                        sock.configureBlocking(false);
                        sock.register(sel, SelectionKey.OP_READ);
                    }
                    iter.remove();
                }
            } while (serverWorks);

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
