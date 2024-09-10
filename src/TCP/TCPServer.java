package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        int port = 5000;

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port" + " " + port);
            Socket socket = serverSocket.accept();
            System.out.println("Connection successful");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            String message = bufferedReader.readLine();
            System.out.println("Recieved: " + message);
            printWriter.println(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}