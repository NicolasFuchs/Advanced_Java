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

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TCPServerThread extends Thread {

  private TCPServer ts;
  private Socket so;
  private Integer id;
  private ObjectInputStream ois;
  
  @Override
  public void run() {
	Object o;
    try {
    	while ((o = ois.readObject()) != null) {
    		
    		if(o instanceof String){
    			ts.log(so, id, (String)o);
    		}else if (o instanceof Integer){
    			ts.log(so, id, ((Integer)o).toString());
    		}else{
    			ts.log(so, id, "Unknown received Object !");
    		}
    	}
      
    } catch (EOFException e) {
    	ts.log(so, id, "Client disconnected: " + e.getClass().getName());
    	
    	try {
			so.close();
		} catch (IOException e1) {
			// nothing ....
		}
    	
    } catch (IOException | ClassNotFoundException e) {
    	e.printStackTrace();     
    } 
  }
  
  public TCPServerThread(TCPServer ts, Socket so, Integer id) {
	try {
		ois = new ObjectInputStream(so.getInputStream());
	} catch (IOException e) {
		e.printStackTrace();
	}
    this.ts = ts;
    this.so = so;
    this.id = id;
  }
}
