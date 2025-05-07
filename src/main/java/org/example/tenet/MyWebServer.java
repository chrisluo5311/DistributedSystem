package org.example.tenet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @Author JI-DUNG, LO
 *  Go to the document root that contains the src file and run the below two command lines
 *  1. Compile the code
 *      javac src/main/java/org/example/tenet/*.java
 *  2. Run the command
 *      java -cp src/main/java org.example.tenet.MyWebServer -document_root "./webSource" -port 8888
 *
 * */
public class MyWebServer {

    private static String docRoot;
    private static int port;
    public static AtomicInteger activeConnections = new AtomicInteger(0);

    public static void main(String[] args) {
        // parse command line arguments
        // e.g., java MyWebServer -document_root "/home/you/files" -port 8888
        if (args.length != 4) {
            Log.error("Invalid number of arguments");
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
                    Log.error("Port is not a number");
                    System.err.println("Port must be a number");
                    System.exit(1);
                }
            } else {
                Log.error("Unknown argument: " + args[i]);
                System.err.println("Unknown argument: " + args[i]);
                System.exit(1);
            }
        }

        // Init socket and start listening
        try (ServerSocket socket = new ServerSocket(port)) {
            Log.info("Waiting for connection on port " + port + "...");
            while(true) {
                // accept incoming connections
                Socket clientSocket = socket.accept();
                Log.info("Accepted connection from " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, docRoot);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error initializing socket: " + e.getMessage());
        }

    }
}
