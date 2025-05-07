package org.example.tenet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MgrResponseDto {
    private String status;
    private String contentType;
    private OutputStream out;
    private boolean isConnectionClose;
    private byte[] body;

    public static void success(OutputStream out, String contentType, boolean isConnectionClose, byte[] body) {
        PrintWriter writer = new PrintWriter(out);
        writer.print("HTTP/1.1 " + MgrResponseCode.SUCCESS.getStatus() + " " + MgrResponseCode.SUCCESS.getMessage() + "\r\n");
        writer.print("Content-Type:" + contentType + "\r\n");
        writer.print("Content-Length:" + body.length + "\r\n");
        if (isConnectionClose) {
            writer.print("Connection: close\r\n");
        } else {
            writer.print("Connection: keep-alive\r\n");
        }
        writer.print("\r\n");
        writer.flush();
        try {
            out.write(body);
            out.flush();
        } catch (IOException e) {
            Log.error("Error writing response", e);
            throw new RuntimeException(e);
        }
    }

    public static void error(MgrResponseCode code, OutputStream out, byte[] body) {
        PrintWriter writer = new PrintWriter(out);
        writer.print("HTTP/1.1 " + code.getStatus() + " " + code.getMessage() + "\r\n");
        writer.print("Content-Type: text/html\r\n");
        writer.print("Content-Length:" + body.length + "\r\n");
        writer.print("Connection: close\r\n");
        writer.print("\r\n");
        writer.flush();
        try {
            out.write(body);
            out.flush();
        } catch (IOException e) {
            Log.error("Error writing response", e);
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

    public boolean getIsConnectionClose() {
        return isConnectionClose;
    }

    public void setIsConnectionClose(boolean isConnectionClose) {
        this.isConnectionClose = isConnectionClose;
    }
}
