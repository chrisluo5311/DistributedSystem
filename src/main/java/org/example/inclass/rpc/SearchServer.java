package org.example.inclass.rpc;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class SearchServer {
    public static void main(String args[])
    {
        try
        {
            // Create an object of the interface
            // implementation class
            Search search = new SearchQuery();

            // RMI registry within the server JVM with
            // port number 1900
            LocateRegistry.createRegistry(1900);

            // Binds the remote object by the name
            // meow_meow_kingdom
            Naming.rebind("rmi://localhost:1900"+
                    "/meow_meow_kingdom",search);
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}
