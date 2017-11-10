/* File:	  AClassWithNativeMethods.java
 * Desc:	  Java program to demonstrate
 *		      a simple native method invocation
 * Author:	Rudolf Scheurer, HEIA-FR (rudolf.scheurer@hefr.ch)
 */

/**
 * AClassWithNativeMethods
 */
public class AClassWithNativeMethods {
    
	public native void theNativeMethod(String str);
	
 	public void aJavaMethod() {
 	    theNativeMethod("Test with string parameter"); // the native method
	}
}