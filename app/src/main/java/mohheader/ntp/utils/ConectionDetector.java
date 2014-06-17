package mohheader.ntp.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by mohheader on 6/17/14.
 */
public class ConectionDetector {
    public static boolean isConneced() {
        try {
            SocketAddress addr = new InetSocketAddress("www.google.com", 80);
            Socket socket = new Socket();
            socket.connect(addr, 5000);
            socket.close();
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
