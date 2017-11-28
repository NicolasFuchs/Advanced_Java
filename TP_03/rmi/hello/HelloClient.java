package rmi.hello;

import java.rmi.*;

public class HelloClient {

    public static void main(String args[]) {

        // check argument
        if (args.length == 0) {
            System.out.println("\nUsage: java HelloClient <registry-host>\n");
            System.exit(0);
        }

        try {

            System.out.println("Performing lookup ...");
            Hello obj = (Hello) Naming.lookup("//" + args[0] + "/MyHelloServer");
            System.out.println("Object received:\n" + obj);

            System.out.println("Invoking remote method ...");
            String message = obj.sayHello();
            System.out.println("Remote method returned '" + message
                    + "' and completed successfully.\n");

        } catch (Exception e) {
            System.out.println("HelloClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
