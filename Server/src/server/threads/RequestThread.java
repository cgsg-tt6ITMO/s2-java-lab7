/**
 * @author Troitskaya Tamara (cgsg-tt6)
 */
package server.threads;

import resources.utility.Request;
import resources.utility.Response;
import server.managers.CollectionManager;
import server.managers.SerializationManager;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;

/**
 * Handles requests from several clients and sends responses.
 * Does all the manipulations with the collection.
 */
public class RequestThread extends Thread {
    @Override
    public void run() {
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
            serv.configureBlocking(false);
            sel = Selector.open();
            serv.register(sel, OP_ACCEPT);

            // several clients
            do {
                sel.select();
                Iterator<SelectionKey> iter = sel.selectedKeys().iterator();
                // while command != exit
                while (iter.hasNext()) {
                    SelectionKey k = iter.next();
                    if (k.isAcceptable()) {
                        //System.out.println("Acceptable");
                        sock = serv.accept();
                        sock.configureBlocking(false);
                        sock.register(sel, OP_READ);
                    } else if (k.isReadable() && ((SocketChannel) k.channel()).read(ByteBuffer.wrap(arr)) != -1) {
                        sock = (SocketChannel) k.channel();
                        //System.out.println("Readable");
                        buf = ByteBuffer.wrap(arr);
                        sock.read(buf);
                        Request r = SerializationManager.readReq(new String(arr));
                        Response response = commandManager.runCommand(r);
                        arr = SerializationManager.objSer(response).getBytes(StandardCharsets.UTF_8);
                        buf = ByteBuffer.wrap(arr);
                        sock.write(buf);
                        arr = new byte[8192];
                        sock.configureBlocking(false);
                        sock.register(sel, OP_READ);
                    }
                    iter.remove();
                }
            } while (true);

        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (SocketException e) {
            System.err.println("Client illegally disconnected");
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERROR...");
            e.printStackTrace();
        }
    }
}
