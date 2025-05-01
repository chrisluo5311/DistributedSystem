package org.example.rpc;

import java.rmi.Naming;

public class ClientRequest {
    public static void main(String args[])
    {
        String answer,value="Breed of Cat";
        try
        {
            // lookup method to find reference of remote object
            Search access =
                    (Search) Naming.lookup("rmi://localhost:1900"+
                            "/meow_meow_kingdom");
            answer = access.query(value);
            System.out.println("Article on " + value +
                    " was " + answer+" at meow_meow_kingdom");
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}
