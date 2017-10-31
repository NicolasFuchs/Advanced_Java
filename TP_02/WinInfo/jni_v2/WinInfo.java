//---------------------------------------------
// WinInfo - class to hold information about
//           the Windows operating system
//---------------------------------------------
// 2017-10-23 R. Scheurer (HEIA-FR)
//---------------------------------------------
//
// JNI Lab: DO NOT MODIFY THIS CLASS !
//
// Design your C code in order to make use of
// this class as efficiently as possible

public class WinInfo {

  String type    = "";
  String edition = "";
  String sp      = "";
  int    build   = -1;
  byte   arch    = -1;

  // NOTE: constructor sets only 3 of 5 attributes !
  public WinInfo(String type, String edition, String sp) {
    this.type = type;
    this.edition = edition;
    this.sp = sp;
  }

  @Override
  public String toString() {
    return type + edition + sp + " (build " + build + ")"
        + (arch > 0 ? ", " + arch + "-bit" : "");
  }

}
