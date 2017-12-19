/* File:    Basic.java
 * What:    Implementation of a basic MBean
 * Who:     Rudolf Scheurer (HEIA-FR), rudolf.scheurer@hefr.ch
 * Version: 12/2017
 */
package jmx;

public class Basic implements BasicMBean {

  protected String state     = "initial state";
  protected int    nbChanges = 0;

  public String getState() {
    return state;
  }

  public void setState(String s) {
    state = s;
    attributeChanged("state");
  }

  public int getNbrChanges() {
    return nbChanges;
  }

  public void reset() {
    nbChanges = 0;
    state = "initial state";
  }

  // Use this method to trace attribute changes
  private void attributeChanged(String attributeName) {
    nbChanges++;
	  System.out.println("- attribute '" + attributeName + "' has been changed.");
  }
  
}
