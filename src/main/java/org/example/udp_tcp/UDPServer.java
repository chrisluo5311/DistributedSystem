package org.example.udp_tcp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) throws Exception {
        try (DatagramSocket serverSocket = new DatagramSocket(9999)) {
            byte[] buffer = new byte[16];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("UDP server is listening on port 9999...");
            serverSocket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received: " + received);

            // Send a response
            String response = "pong";
            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseBytes, responseBytes.length, packet.getAddress(), packet.getPort());
            serverSocket.send(responsePacket);
        }
    }
}
