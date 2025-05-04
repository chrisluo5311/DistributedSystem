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
            System.out.println("New connection from: " + clientSocket.getInetAddress());
            String request = bufferedReader.readLine();
            if (request == null || !request.startsWith("GET")){
                MgrResponseDto.error(MgrResponseCode.BAD_REQUEST, outputStream, "Bad Request".getBytes());
                return;
            }

            // Parse the request like GET /index.html HTTP/1.1
            String[] requestParts = request.split(" ");
            if (requestParts.length < 2) {
                MgrResponseDto.error(MgrResponseCode.BAD_REQUEST, outputStream, "Bad Request".getBytes());
                return;
            }
            // translate 'GET /' to 'GET /index.html'
            String requestPath = requestParts[1];
            if (requestPath.equals("/")) {
                requestPath = "/index.html";
            }

            // check if the request file path is traversing outside the docRoot
            File requestFile = new File(docRoot, requestPath);
            if (!requestFile.getCanonicalFile().toPath().startsWith(new File(docRoot).getCanonicalPath())) {
                MgrResponseDto.error(MgrResponseCode.FORBIDDEN, outputStream, "Forbidden".getBytes());
                return;
            }

            // check if the file exists
            if (!requestFile.exists()) {
                MgrResponseDto.error(MgrResponseCode.NOT_FOUND, outputStream, "Not Found".getBytes());
                return;
            } else if (!requestFile.canRead()) {
                MgrResponseDto.error(MgrResponseCode.FORBIDDEN, outputStream, "Forbidden".getBytes());
            } else {
                // check the file type and send response
                String contentType = Files.probeContentType(requestFile.toPath());
                byte[] fileBytes = Files.readAllBytes(requestFile.toPath());
                MgrResponseDto.success(outputStream, fileBytes);
            }

        } catch (IOException e) {
            System.err.println("Error handling response:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
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
