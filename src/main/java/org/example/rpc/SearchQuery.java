package org.example.rpc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SearchQuery extends UnicastRemoteObject implements Search {

    public SearchQuery() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public String query(String search) throws RemoteException {
        String result = "";
        if (search.equals("Breed of Cat")) {
            return "Cute Cute Breed";
        } else {
            result = "Not Found";
        }
        return result;
    }
}
