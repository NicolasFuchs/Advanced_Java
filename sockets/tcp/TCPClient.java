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
  private PrintWriter out;
  
  public TCPClient(InetSocketAddress isa) throws IOException {
    s = new Socket(isa.getAddress(), isa.getPort());
    out = new PrintWriter(s.getOutputStream(), false);
  }
  
  public void sendMsg(String msg) {
    out.println(msg);
    out.flush();
  }
  
  public void closeSocket() {
    
  }

}
