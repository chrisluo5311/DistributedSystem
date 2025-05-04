package org.example.inclass.socketdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// To execute:  java EchoClient.java 127.0.0.1
public class EchoClient {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || args.length > 2) {
            System.err.println("Usage: java EchoClient <host> [<port>]");
            return;
        }
        String host = args[0];
        int    port = (args.length == 2) ? Integer.parseInt(args[1]) : 50007;

        try (Socket socket = new Socket(host, port);
            BufferedReader in  = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), /*autoFlush*/ true)) {


            out.println("Hello, world");          // send exactly one line
            String reply = in.readLine();         // blocks until echo comes back
            System.out.println("Server replied: " + reply);
        }
    }
}
