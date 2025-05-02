package org.example.udp_tcp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TransportDemo {
    public static void main(String[] args) throws Exception {

        /* ---------- UDP one-shot ---------- */
        // New a socket and send a packet to the local host
        try (DatagramSocket s = new DatagramSocket()) {
            byte[] msg = "ping".getBytes(); // string "ping" converts to a byte array
            // destination address (InetAddress.getLoopbackAddress()) points to the local host
            DatagramPacket p = new DatagramPacket(msg, msg.length,
                    InetAddress.getLoopbackAddress(), 9999);
            s.send(p);
            byte[] buf = new byte[16];
            DatagramPacket r = new DatagramPacket(buf, buf.length);
            s.receive(r);
            System.out.println("UDP got  " + new String(r.getData(), 0, r.getLength()));
        }
        


        /* ---------- TCP HTTP ---------- */
        var client = HttpClient.newHttpClient();
        var req = HttpRequest.newBuilder(URI.create("https://example.com")).GET().build();
        // HttpResponse.BodyHandlers.discarding() ignores the response body and only returns the status code
        System.out.println("TCP HTTP status " + client.send(req, HttpResponse.BodyHandlers.discarding()).statusCode());
        System.out.println("TCP HTTP Headers " + client.send(req, HttpResponse.BodyHandlers.discarding()).headers());
    }
}
