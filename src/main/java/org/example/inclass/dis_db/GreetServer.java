package org.example.inclass.dis_db;

import java.io.*;
// Imports all classes from the java.net package for handling networking and sockets.
import java.net.*;

public class GreetServer { 

    // Declares a private field to represent the server socket that listens for incoming connections.
    private ServerSocket serverSocket;

    // Declares a private field to represent the socket for communicating with a connected client.
    private Socket clientSocket;

    // Declares a private field to send output data to the client.
    private PrintWriter out;

    // Declares a private field to read input data from the client.
    private BufferedReader in;

    public void start(int port) throws IOException {
        // Creates a server socket bound to the given port, allowing the server to accept connections.
        serverSocket = new ServerSocket(port);
        // Waits for a client to connect and accepts the connection, establishing a communication link.
        clientSocket = serverSocket.accept();
        // Initializes the output stream to send data to the client, with auto-flush enabled.
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        // Initializes the input stream to read data from the client.
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // Reads a line of text sent by the client.
        String greeting = in.readLine();

        if ("hello server".equals(greeting)) {
            // Sends the response "hello client" back to the client if the condition is true.
            out.println("hello client");
        } else {
            out.println("unrecognised greeting");
        }
    }

    // Method to stop the server and clean up resources. Throws IOException if any closing operation fails.
    public void stop() throws IOException {
        // Closes the input stream to release system resources.
        in.close();
        // Closes the output stream to release system resources.
        out.close();
        // Closes the socket connected to the client.
        clientSocket.close();
        // Closes the server socket to stop listening for connections.
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException { 
        GreetServer server = new GreetServer();
        // Starts the server on port 6666.
        server.start(6666);
    }
}