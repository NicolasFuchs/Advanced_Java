/* File:    BasicMBean.java
 * What:    MBean interface for class Basic
 * Who:     Rudolf Scheurer (HEIA-FR), rudolf.scheurer@hefr.ch
 * Version: 12/2017
 */
package jmx;

public interface BasicMBean {
  public String getState();
  public void setState(String s);
  public int getNbrChanges();
  public void reset();
}