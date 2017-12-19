/* File:	  BasicAgent.java
 * What:	  A very basic JMX agent
 * Who:  	  Rudolf Scheurer (HEIA-FR), rudolf.scheurer@hefr.ch
 * Version: 12/2017
 */
package jmx;

import javax.management.*;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class BasicAgent {

  public static void main(String argv[]) {
    try {

      // Instantiate the MBean server
      System.out.println("Create the MBean server");
      MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();      

      // Create and start an HTML protocol adaptor
      System.out.println("Create, register and start an HTML Adaptor server");
      HtmlAdaptorServer htmlAdaptor = new HtmlAdaptorServer();
      mBeanServer.registerMBean(htmlAdaptor, null);
      htmlAdaptor.start();
      System.out.println("HTML Adaptor server is running ...");

    }  
    catch(Exception e) {
      e.printStackTrace();
      return;
    }
  }
}