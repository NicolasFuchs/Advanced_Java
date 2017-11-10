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
	    System.out.println("Line 01");
		System.loadLibrary("NativeMethodImpl");
		System.out.println("Line 02");
	}
	public static void main(String[] args) {
	    try {
	    System.out.println("Line 1");
		AClassWithNativeMethods theClass = new AClassWithNativeMethods();
		System.out.println("Line 2");
		theClass.aJavaMethod();    // a NON native method
		System.out.println("Line 3");
	    } catch (Exception e) {
	        System.err.println("Test");
	        e.printStackTrace();
	    }
	}
}
