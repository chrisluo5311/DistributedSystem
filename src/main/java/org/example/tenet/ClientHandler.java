package org.example.tenet;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private String docRoot;

    public ClientHandler(Socket socket, String docRoot) {
        this.clientSocket = socket;
        this.docRoot = docRoot;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream outputStream = clientSocket.getOutputStream()) {
            String request;
            while ((request = bufferedReader.readLine()) != null) {
                if (request.isEmpty()) {
                    continue;
                }
                Log.info("Received request: " + request);
                if (request == null || !request.startsWith("GET")){
                    Log.error("Bad Request: request is either null or not a GET request");
                    MgrResponseDto.error(MgrResponseCode.BAD_REQUEST, outputStream, "Bad Request: use GET".getBytes());
                    return;
                }

                // Parse the request like GET /index.html HTTP/1.1
                String[] requestParts = request.split(" ");
                if (requestParts.length < 2) {
                    Log.error("Bad Request: no Request arguments");
                    MgrResponseDto.error(MgrResponseCode.BAD_REQUEST, outputStream, "Bad Request: please specify Request arguments".getBytes());
                    return;
                }
                // translate 'GET /' to 'GET /index.html'
                String requestPath = requestParts[1];
                if (requestPath.equals("/")) {
                    Log.info("Translating request path to /index.html");
                    requestPath = "/index.html";
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
                    Log.error("Forbidden: request file is not readable");
                    MgrResponseDto.error(MgrResponseCode.FORBIDDEN, outputStream, "Forbidden".getBytes());
                } else {
                    // check the file type and send responses
                    String contentType = Files.probeContentType(requestFile.toPath());
                    Log.info("Request file content type: " + contentType);
                    byte[] fileBytes = Files.readAllBytes(requestFile.toPath());
                    MgrResponseDto.success(outputStream, contentType, fileBytes);
                    Log.info("Response sent successfully");
                }
            }
        } catch (IOException e) {
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
