package rmi.digest;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Remote interface for RMIDigestValidator service.
 * 
 * @author Rudolf Scheurer (HEIA-FR)
 * @version 1.07 (11.2017)
 *
 */
public interface RMIDigestValidator extends Remote {

  // used for lab SECTION 3 "Digest Validation using RMI"
  String getChallenge(String username) throws RemoteException;
  boolean challengeResponse(String username, byte[] hash) throws RemoteException;

  // used for lab SECTION 4 "RMI with Class Loading"
  Date getDate(String username) throws RemoteException;
  Date getDate2(String username) throws RemoteException;

}
