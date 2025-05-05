package org.example.tenet;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private String docRoot;

    public ClientHandler(Socket socket, String docRoot) {
        this.clientSocket = socket;
        this.docRoot = docRoot;
    }

    // simple heuristic to determine timeout based on active connections
    private int getTimeoutBasedOnRequest(int activeConnections) {
        int defaultTimeout = 5000; // 5 seconds
        int minTimeout = 2000; // 2 seconds
        int maxConnections = 30;

        double ratio = (double) activeConnections / maxConnections;
        int timeout = (int) (defaultTimeout * (1 - ratio));
        return Math.max(minTimeout, timeout);
    }

    @Override
    public void run() {
        MyWebServer.activeConnections.incrementAndGet();
        try {
            int timeout = getTimeoutBasedOnRequest(MyWebServer.activeConnections.get());
            Log.info("Setting timeout to " + timeout + "ms");
            clientSocket.setSoTimeout(timeout);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream outputStream = clientSocket.getOutputStream();

            boolean keepAlive = true;
            String httpVersion = "HTTP/1.0";
            while (keepAlive) {
                String request = bufferedReader.readLine();
                if (request == null) {
                    Log.info("Request is null, closing connection");
                    break;
                }
                if (request.isEmpty()) continue;
                Log.info("Received request: " + request);
                // Parse the request like GET /index.html HTTP/1.1
                String[] requestParts = request.split(" ");
                if (requestParts.length < 2) {
                    Log.error("Bad Request: no other Request arguments");
                    MgrResponseDto.error(MgrResponseCode.BAD_REQUEST, outputStream, "Bad Request: please specify Request arguments".getBytes());
                    return;
                }
                // translate 'GET /' to 'GET /index.html'
                String requestMethod = requestParts[0];
                String requestPath = requestParts[1];
                httpVersion = requestParts[2];

                if (!requestMethod.equals("GET")){
                    Log.error("Bad Request: request is not a GET request");
                    MgrResponseDto.error(MgrResponseCode.BAD_REQUEST, outputStream, "Bad Request: use GET".getBytes());
                    return;
                }

                if (requestPath.equals("/")) {
                    Log.info("Translating request path to /index.html");
                    requestPath = "/index.html";
                }

                // check connection close
                boolean isConnectionClose = false;
                String line;
                while (!(line = bufferedReader.readLine()).isEmpty()) {
                    if (line.toLowerCase().startsWith("connection: close")) {
                        isConnectionClose = true;
                    }
                }

                // check if the request file path is traversing outside the docRoot
                File requestFile = new File(docRoot, requestPath);
                if (!requestFile.getCanonicalFile().toPath().startsWith(new File(docRoot).getCanonicalPath())) {
                    Log.error("Bad Request: request file path is traversing outside the docRoot");
                    MgrResponseDto.error(MgrResponseCode.FORBIDDEN, outputStream, "Forbidden".getBytes());
                    return;
                }

                // check if the file exists
                if (!requestFile.exists()) {
                    Log.error("Not Found: request file '"+ requestFile.getPath() +"' does not exist");
                    MgrResponseDto.error(MgrResponseCode.NOT_FOUND, outputStream, "Not Found".getBytes());
                    return;
                } else if (!requestFile.canRead()) {
                    // check if the file is readable
                    Log.error("Forbidden: request file is not readable");
                    MgrResponseDto.error(MgrResponseCode.FORBIDDEN, outputStream, "Forbidden".getBytes());
                } else {
                    // check the file type and send responses
                    String contentType = Files.probeContentType(requestFile.toPath());
                    Log.info("Request file content type: " + contentType);
                    byte[] fileBytes = Files.readAllBytes(requestFile.toPath());
                    if (httpVersion.equals("HTTP/1.0")) {
                        isConnectionClose = false;
                        keepAlive = false;
                    }
                    MgrResponseDto.success(outputStream, contentType, isConnectionClose, fileBytes);
                    Log.info("Response sent successfully");
                }
            }
        } catch (SocketTimeoutException e) {
            Log.info("Current Thread's Socket timed out, closing connection");
            System.out.println("Current Thread-" + Thread.currentThread().getId() +" Socket timed out");
        }
        catch (IOException e) {
            Log.error("Error handling request: " + e.getMessage());
            System.err.println("Error handling response:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                Log.error("Error closing socket: " + e.getMessage());
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }

    }

    public String getDocRoot() {
        return docRoot;
    }

    public void setDocRoot(String docRoot) {
        this.docRoot = docRoot;
    }
}
