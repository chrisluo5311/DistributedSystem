package org.example.tenet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class MgrResponseDto {
    private String status;
    private String contentType;
    private OutputStream out;
    private byte[] body;

    public static void success(OutputStream out, byte[] body) {
        PrintWriter writer = new PrintWriter(out);
        writer.println("HTTP/1.1" + MgrResponseCode.SUCCESS.getStatus() + "\r\n");
        writer.println("Content-Type:" + MgrResponseCode.SUCCESS.getContentType() + "\r\n");
        writer.println("Content-Length:" + body.length + "\r\n");
        writer.println("\r\n");
        writer.flush();
        try {
            out.write(body);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error writing to output stream: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void error(MgrResponseCode code, OutputStream out, byte[] body) {
        PrintWriter writer = new PrintWriter(out);
        writer.println("HTTP/1.1" + code.getStatus() + "\r\n");
        writer.println("Content-Type:" + code.getContentType() + "\r\n");
        writer.println("Content-Length:" + body.length + "\r\n");
        writer.println("\r\n");
        writer.flush();
        try {
            out.write(body);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error writing to output stream: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public MgrResponseDto() {}

    public MgrResponseDto(String status, String contentType, OutputStream out, byte[] body) {
        this.status = status;
        this.contentType = contentType;
        this.out = out;
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBody() {
        return body;
    }
    public void setBody(byte[] body) {
        this.body = body;
    }

}
