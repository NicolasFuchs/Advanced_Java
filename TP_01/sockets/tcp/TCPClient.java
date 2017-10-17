/**
 * File   : TCPClient.java
 * Authors: R. Scheurer (HEIA-FR) (Initial Author)
 *          Nicolas Fuchs
 *          Jigé Pont
 * Date   : 25.09.2017
 * 
 * Description - a simple TCP client
 *
 */
package sockets.tcp;

import java.net.*;
import java.io.*;

public class TCPClient {

    private Socket s;
    private ObjectOutputStream oos;

    public TCPClient(InetSocketAddress isa) throws IOException {
        s = new Socket(isa.getAddress(), isa.getPort());
        oos = new ObjectOutputStream(s.getOutputStream());
    }

    public void sendMsg(String msg) throws IOException {
    	MyMessage m = new MyMessage(msg);
    	oos.writeObject(m);
        //oos.writeObject(msg);
    }

    public void closeSocket() throws IOException {
        oos.close();
        s.close();
    }

}
