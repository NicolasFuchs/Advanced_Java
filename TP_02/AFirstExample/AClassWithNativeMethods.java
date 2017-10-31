/* File:	  AClassWithNativeMethods.java
 * Desc:	  Java program to demonstrate
 *		      a simple native method invocation
 * Author:	Rudolf Scheurer, HEIA-FR (rudolf.scheurer@hefr.ch)
 */

/**
 * AClassWithNativeMethods
 */
public class AClassWithNativeMethods {
    
	public native void theNativeMethod();
	
 	public void aJavaMethod() {
 	    theNativeMethod(); // the native method
	}
}

