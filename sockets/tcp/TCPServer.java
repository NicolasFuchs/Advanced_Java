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

  private boolean isRunning = true;             // Variable to stop ServerSocket (for future improvement of program)
  private static int serverPort = 7676;			// Server port to use by default
  private static int serverPortMax = 7686;		// Server port maximum (serverPortMax - serverPort = max instances of server) 
  private int serverId;							// identifier for one server
  private Integer id = 0;                		// Client id
  private ServerSocket ss;               		// ServerSocket to accept connections

  public static void main(String[] args) {
	  if(serverPort < serverPortMax) {
		  new TCPServer();
	  }else {
		  System.out.println("Max number of  TCPServer or max range of ports reached, program exit");
	  }	  
  }
  
  private TCPServer() {
	  this.serverId = serverPort++;
	  this.createAndLaunch();
  }
    
  private void createAndLaunch() {
	  try {
		  ss = new ServerSocket(serverId);
	  } catch (IOException e) {
		  if(serverPort < serverPortMax) {
			  log("Création du serveur sur port ("+serverId+") impossible, tentative sur port ("+(serverPort+1)+")");
			  this.serverId = serverPort++;
			  this.createAndLaunch();
		  }else {
			  log("Max number of TCPServer or max range of ports reached, program exit");
		  }		  
	  }
	  
	  // only if instantiation of ServerSocket is a success
	  if(ss instanceof ServerSocket) {
		  log("Listening on TCP Port " + serverId + " ...");
		  while(isRunning) {
		      try {
				  TCPServerThread st;
				  Socket so;
				  so = ss.accept();
				  
				  log("New connection request from (" + so.getInetAddress().getHostAddress() + ":" + so.getPort() + ")");
		        
				  st = new TCPServerThread(this, so, id);
				  st.start();
		        
				  id++;
		      } catch (IOException e) {
		    	  e.printStackTrace();
		      }
		  }
	  }
  }
  
  public void log(Socket so, Integer id, String msg) {
    log("Client(" + id + ") from (" + so.getInetAddress().getHostAddress() + ":" + so.getPort() + ") : " + msg);
  }
  
  public void log(String msg) {
    try {
		System.out.println("Server("+InetAddress.getLocalHost().getHostAddress()+":"+serverId+"): "+msg);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
