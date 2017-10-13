/**
 * File   : UDPServer.java
 * Author : R. Scheurer (HEIA-FR)
 * Date   : 25.09.2017
 * 
 * Description - a simple UDP server template
 * 
 */
package sockets.udp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import sockets.tcp.MyMessage;

public class UDPServer {

  private static final int MAX_OBJECT_SIZE=6400;	// Max size in byte for java object send in DatagramPacket
  static final int MAX_SIZE = 100;
  private boolean isRunning = true;             	// Variable to stop Server (for future improvement of this program)
  private static int serverPort = 6767;				// Server port to use by default
  private static int serverPortMax = 6776;			// Server port maximum (serverPortMax - serverPort = max instances of server) 
  private int serverId;								// Identifier for one server
  private DatagramSocket ds;						// Entry point
    
  public static void main(String[] args) {
	  if(serverPort < serverPortMax) {
		  new UDPServer();
	  }else {
		  System.out.println("Max number of  UDPServer or max range of ports reached, program exit");
	  }	    
  }
  
  private UDPServer() {
	  this.serverId = serverPort++;
	  this.createAndLaunch();
  }
  
  private void createAndLaunch() {
	  try {
		  ds = new DatagramSocket(serverId);
	  } catch (SocketException e) {
		  if(serverPort < serverPortMax) {
			  log("Création du serveur UDP sur port ("+serverId+") impossible, tentative sur port ("+(serverPort)+")");
			  this.serverId = serverPort++;
			  this.createAndLaunch();
		  }else {
			  log("Max number of UDPServer or max range of ports reached, program exit");
		  }		  
	  }
	  
	  // only if instantiation of DatagramSocket was a success
	  if(ds instanceof DatagramSocket) {
		  byte[] buffer = new byte[MAX_OBJECT_SIZE];
		  
		  log("Listening on UDP Port " + serverId + " ...");
		  
    	  DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
    	  
		  while(isRunning) {			  
	    	  try {
	    		  // receiving (blocking)
	    		  ds.receive(dp);
	    		  	    		  	    
	    		  // de-serialization
	    		  ByteArrayInputStream baos = new ByteArrayInputStream(buffer);
	    	      ObjectInputStream oos = new ObjectInputStream(baos);
	    	      try {
	    	    	  Object o = oos.readObject();
	    	    	  
	    	    	  if(o instanceof MyMessage) {
	    	    		  MyMessage m = (MyMessage)o;
	    	    		  log(dp, m.getMsgNumber() + " : " + m.getMsg());
	    	    	  }else {
	    	    		  log(dp, "Unknow object !");
	    	    	  }

	    	      } catch (ClassNotFoundException e) {
	    	    	  e.printStackTrace();
	    	      }
	    		    
	    		  /*
	    		  // Cast buffer in String
	    		  String msg = new String( buffer, 0, dp.getLength());
	    		  
	    		  // Console output
	    		  log(dp, msg);
	    		  */
	    	  } catch (IOException e) {
	    		  e.printStackTrace();
	    	  }
		  }
	  }
  }
  
  public void log(DatagramPacket dp, String msg) {
	  log("Client from (" + dp.getAddress() + ":" + dp.getPort() + ") : " + msg);
  }
	  
  public void log(String msg) {
	  try {
		  System.out.println("Server("+InetAddress.getLocalHost().getHostAddress()+":"+serverId+"): "+msg);
	  } catch (UnknownHostException e) {
		  e.printStackTrace();
	  }
  }
}
