package TCPwTimeout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;
        int timeout = 10000;

        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(hostname, port), timeout);
            socket.setSoTimeout(timeout);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter.println("Hello Server");

            try {
                String response = bufferedReader.readLine();
                System.out.println("Server response: " + response);
            } catch (SocketTimeoutException e) {
                System.out.println("Read timed out");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}