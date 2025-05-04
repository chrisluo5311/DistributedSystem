package org.example.tenet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *  #TODO
 *  You have to go to the root that contains src file to run the command line
 *  1. Compile the code 2. Run the command
 *  java -cp src/main/java org.example.tenet.MyWebServer -document_root "/home/you/files" -port 8888
 *
 * */
public class MyWebServer {

    private static String docRoot;
    private static int port;

    public static void main(String[] args) {
        // parse command line arguments
        // e.g., java MyWebServer -document_root "/home/you/files" -port 8888
        if (args.length != 4) {
            System.err.println("Please use: java MyWebServer -document_root <path> -port <port>");
            System.exit(1);
        }

        for (int i = 0 ; i < args.length ; i += 2) {
            if (args[i].equals("-document_root")) {
                docRoot = args[i + 1];
            } else if (args[i].equals("-port")) {
                try {
                    port = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.err.println("Port must be a number");
                    System.exit(1);
                }
            } else {
                System.err.println("Unknown argument: " + args[i]);
                System.exit(1);
            }
        }

        System.out.println("Document root: " + docRoot);
        System.out.println("Port: " + port);
        System.out.println("Starting web server...");

        // Init socket and start listening
        try (ServerSocket socket = new ServerSocket(port)) {
            while(true) {
                // accept incoming connections
                Socket responseSocket = socket.accept();

                ClientHandler clientHandler = new ClientHandler(responseSocket, docRoot);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error initializing socket: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
