/**
 * File   : UDPClient.java
 * Author : R. Scheurer (HEIA-FR)
 * Date   : 25.09.2017
 * 
 * Description - a simple UDP client
 * 
 */
package sockets.udp;

import java.net.*;

public class UDPClient {

  private static int clientPort = 6767;			// Client port to use by default
  private static int clientPortMax = 6769;		// Client port maximum (clientPortMax - clientPort = max instances of client)
  private int clientId;							// Identifier for one client (port number effective)
  DatagramSocket ds;
  DatagramPacket dp;
  byte[] buffer;
  
  public UDPClient() throws SocketException{
	  this.clientId = clientPort++;
	  this.init();
  }
  
  private void init() throws SocketException {
	  try {
		  ds = new DatagramSocket(clientId);
	  } catch (SocketException e) {
		  if(clientPort < clientPortMax) {
			  this.clientId = clientPort++;
			  this.init();
		  }else {
			  throw new SocketException("No more port availables");
		  }		  
	  }
  }
    
  public void sendMsg(InetSocketAddress isa, String msg) throws Exception {
	  dp = new DatagramPacket(
			  msg.getBytes(), 		// data buffer (of type byte[])
			  msg.length(), 		// size of data buffer
			  isa.getAddress(), 	// destination address
			  isa.getPort()); 		// integer

	  // send
	  ds.send(dp);
  }
  
  public int getPort() {
	  return ds.getLocalPort();
  }
  
  public void closeSocket() {
	  ds.close();
  }
}
