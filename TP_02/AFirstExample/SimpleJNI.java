/* File:   SimpleJNI.java
 * Desc:   Java program to demonstrating
 *		     a simple native method invocation
 * Author: Rudolf Scheurer, HEIA-FR (rudolf.scheurer@hefr.ch)
 */

/**
 * The SimpleJNI class
 */
public class SimpleJNI {
	static {
		System.loadLibrary("NativeMethodImpl");
	}
	public static void main(String[] args) {
		AClassWithNativeMethods theClass = new AClassWithNativeMethods();
		theClass.aJavaMethod();    // a NON native method
	}
}
