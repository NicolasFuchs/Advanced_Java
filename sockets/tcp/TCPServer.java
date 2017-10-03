/**
 * File   : TCPServer.java
 * Authors: R. Scheurer (HEIA-FR) (Initial Author)
 *          Nicolas Fuchs
 *          Jigé Pont
 * Date   : 25.09.2017
 * 
 * Description - a simple TCP server template
 *
 */
package sockets.tcp;

import java.net.*;
import java.io.*;

public class TCPServer {

  private static TCPServer instance = null;                 // Instance of Server
  private static boolean isRunning = true;                  // Variable to stop ServerSocket
  private static final int SERVER_PORT = 7676;              // Server port to use
  private static Integer id = 0;                            // Client id
  private static ServerSocket ss;                           // ServerSocket to accept connections

  public static void main(String[] args) {
    try {
      instance = new TCPServer();
      ss = new ServerSocket(SERVER_PORT);
      launch();
    } catch (IOException e) {
      System.out.println("Création du serveur impossible.");
      e.printStackTrace();
    }
  }
  
  private static void launch() {
    TCPServerThread st;
    Socket so;
    log("Listening on TCP Port " + SERVER_PORT + " ...");
    while(isRunning) {
      try {
        so = ss.accept();
        st = new TCPServerThread(instance, so, id);
        st.run();
        log("New connection request from (" + so.getInetAddress().getHostAddress() + ":" + so.getPort() + ")");
        id++;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void log(Socket so, Integer id, String msg) {
    System.out.println("Client(" + id + ") from (" + so.getInetAddress().getHostAddress() + ":" + so.getPort() + ") : " + msg);
  }
  
  public static void log(String msg) {
    System.out.println(msg);
  }
}
