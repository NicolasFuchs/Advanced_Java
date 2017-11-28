package rmi.hello;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class HelloServer extends UnicastRemoteObject implements Hello {

    public HelloServer() throws RemoteException {
        super();
    }

    public String sayHello() throws RemoteException {
        System.out.println("- saying hello to all that ask ...");
        return "Hi Buddy!!!";
    }

    public static void main(String args[]) {
        try {
            HelloServer obj = new HelloServer();
            System.out.println("\nContacting registry on localhost ...");
            Naming.rebind("//localhost/MyHelloServer", obj);
            System.out.println("HelloServer bound in registry.\n\nStop server using Ctrl-C.");
        } catch (Exception e) {
            System.out.println("HelloServer exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
