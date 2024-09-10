package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;

        try{
            DatagramSocket datagramSocket = new DatagramSocket();
            byte[] buffer = "Hello Server".getBytes();
            InetAddress address = InetAddress.getByName(hostname);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            datagramSocket.send(packet);

            byte[] responseBuffer = new byte[512];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            datagramSocket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Server response: " + response);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
