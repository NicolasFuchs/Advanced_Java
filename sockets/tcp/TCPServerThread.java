/**
 * File   : TCPServerThread.java
 * Authors: R. Scheurer (HEIA-FR) (Initial Author)
 *          Nicolas Fuchs
 *          Jigé Pont
 * Date   : 25.09.2017
 * 
 * Description - a simple TCP serverthread template
 *
 */
package sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPServerThread extends Thread {

    private TCPServer ts;
    private Socket so;
    private Integer id;

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(so.getInputStream()));
            ts.log(so, id, in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TCPServerThread(TCPServer ts, Socket so, Integer id) {
        this.ts = ts;
        this.so = so;
        this.id = id;
    }

}
