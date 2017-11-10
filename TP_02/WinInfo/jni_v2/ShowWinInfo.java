//-------------------------------------------------
// ShowWinInfo - uses native method (via JNI)
//               to get Windows version information
//-------------------------------------------------
// 2017-10-23  R. Scheurer (HEIA-FR)
//-------------------------------------------------

package osinfo;

public class ShowWinInfo {

  static {
    System.loadLibrary("wininfo");
  }

  // YOU MAY ONLY MODIFY THE FOLLOWING SINGLE LINE ONLY !
  public static native WinInfo getWinInfo();
  
  public static void main(String[] args) {

    // DO NOT MODIFY THE FOLLOWING LINE !
    System.out.println("Operating System:\n" + getWinInfo());

  }

}
