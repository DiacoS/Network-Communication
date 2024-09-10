package TCPwTimeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPServer {
    public static void main(String[] args) {
        int port = 5000;
        int timeout = 10000; // Increased timeout

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            Socket socket = serverSocket.accept();
            socket.setSoTimeout(timeout);
            System.out.println("New client connected");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);


            try {
                String message = bufferedReader.readLine();
                System.out.println("Received: " + message);
                Thread.sleep(10000);
                printWriter.println("Hello Client");
            } catch (SocketTimeoutException e) {
                System.out.println("Read timed out");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}