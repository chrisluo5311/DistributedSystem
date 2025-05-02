package org.example.socketdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Tp execute: java EchoServer.java
public class EchoServer {
    public static void main(String[] args) {
        int port = (args.length == 1) ? Integer.parseInt(args[0]) : 50007;

        System.out.println("🟢 Echo-server listening on port " + port);

        /* try-with-resources auto-closes ServerSocket and client Socket */
        try (ServerSocket server = new ServerSocket(port);
                Socket client      = server.accept();                     // blocks
                BufferedReader in  = new BufferedReader(
                        new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(
                        client.getOutputStream(), /*autoFlush*/ true)) {

            System.out.println("  ↳ Connected by " + client.getRemoteSocketAddress());

            String line;
            while ((line = in.readLine()) != null) {   // EOF ⇒ client closed
                out.println(line);                     // echo straight back
            }

        } catch (IOException e) {
            e.printStackTrace();                       // minimal error handling
        }

        System.out.println("🔴 Server terminated.");
    }
}
