/* File:	  JMXMP_Client.java
 * What:	  A management application (client) accessing an JMXMP connector server
 * Who:	    Rudolf Scheurer (HEIA-FR), rudolf.scheurer@hefr.ch
 * Version: 12/2017
 */
package jmx;

import javax.management.*;
import javax.management.remote.*;

public class JMXMP_Client {

  public static void main(String argv[]) {
    try {
      // Create an JMXMP connector and connect it
      System.out.println("\nCreate an JMXMP connector client and " +
                         "connect it to the JMXMP connector server ...");
      // TODO
												 
      // Get an MBeanServerConnection
      System.out.println("\nGet an MBeanServerConnection ...");
      // TODO

      // Create the Basic MBean
      System.out.println("\nCreating MBean ...");
      // TODO
			
      // Access and modify the 'owner' attribute
			System.out.println("- attribute 'owner' = " + /* TODO getAttribute(...) */ );
			// TODO modify attribute 'owner'
			System.out.println("- attribute 'owner' = " + /* TODO getAttribute(...) */ );
      // TODO use the method 'invoke' to reset the MBean

      // Creating a dedicated MBean proxy
      System.out.println("\nAccessing MBean using proxy ...");
      // TODO

      // Use the proxy
      // TODO change attribute 'state'
      System.out.println("- attribute 'state' = " + /* get value of attribute 'state' through proxy*/ );
	  
      // Connector remains active until user presses ENTER
      System.out.println("\nPress ENTER to unregister the MBean and stop the client ...");
      System.in.read();
			
      // Unregister the MBean and close the connector
      System.out.println("Unregistering MBean ...");
      // TODO
      System.out.println("Closing JMXMP connection ...");
      // TODO
      System.out.println("Done.");

		}  
    catch(Exception e) {
      e.printStackTrace();
      return;
    }
  }
}