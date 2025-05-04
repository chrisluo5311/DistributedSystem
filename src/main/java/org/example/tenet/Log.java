package org.example.tenet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    private static final String LOG_FILE = "tenet.log";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static synchronized void log(String level, String message) {
        long threadId = Thread.currentThread().getId();
        String timeStamp = LocalDateTime.now().format(DATE_FORMAT);
        String logMessage = String.format("[%s] [%s] [Thread-%d] %s", timeStamp, level, threadId, message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Exception writing log file: " + e.getMessage());
        }
    }

    public static void info(String message) {
        log("INFO", message);
    }

    public static void error(String message, Throwable throwable) {
        log("ERROR", message);
        if (throwable != null) {
            log("ERROR", throwable.toString());
        }
    }

    public static void error(String message) {
        log("ERROR", message);
    }
}