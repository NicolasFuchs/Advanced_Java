package rmi.digest;

import java.rmi.Naming;
import java.util.Date;

public class RMIDigestClient {

    public static void main(String[] args) {
        
        // check argument
        if (args.length == 0) {
            System.out.println("\nUsage: java RMIDigestClient <registry-host>\n");
            System.exit(0);
        }
        
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        String username = "fuchs";
        String password = "welcome";
        
        try {
            System.out.println("Performing lookup ...");
            RMIDigestValidator obj = (RMIDigestValidator) Naming.lookup("//" + args[0] + "/RMIDigestServer");
            System.out.println("Object received:\n" + obj);
            
            //Start authentication
            System.out.println("Challenge result received: " + (authentication(username, password, obj)?"OK":"KO"));
            
            Date date1 = obj.getDate(username);
            System.out.println("Date1 received:" + date1);
            Date date2 = obj.getDate2(username);
            System.out.println("Date2 received:" + date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean authentication(String username, String password, RMIDigestValidator obj) {
        boolean result = false;
        try {
            String challenge = obj.getChallenge(username);
            System.out.println("Challenge received: " + challenge);
            MD5Digest md5d = new MD5Digest();
            result = obj.challengeResponse(username, md5d.doHash(md5d.doHash(password), challenge.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
