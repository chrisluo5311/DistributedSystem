package org.example.tenet;

public enum MgrResponseCode {
    SUCCESS("200 OK", "text/html"),
    FORBIDDEN("403 Forbidden", "text/html"),
    NOT_FOUND("404 Not Found", "text/html"),
    BAD_REQUEST("400 Bad Request", "text/html"),
    INTERNAL_SERVER_ERROR("500 Internal Server Error", "text/html");

    private String status;
    private String contentType;

    MgrResponseCode(String status, String contentType) {
        this.status = status;
        this.contentType = contentType;
    }

    public String getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }
}
