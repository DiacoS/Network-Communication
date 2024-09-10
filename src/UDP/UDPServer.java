package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        int port = 5000;
        int bytevalue = 512;

        try{
            DatagramSocket datagramSocket = new DatagramSocket(port);
            byte[] buffer = new byte[bytevalue];

            while (true){
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Recieved: " + message);

                String response = message;
                byte[] responseByte = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseByte, responseByte.length, datagramPacket.getAddress(), datagramPacket.getPort());
                datagramSocket.send(responsePacket);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
