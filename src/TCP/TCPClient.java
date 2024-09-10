package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;

        try{
            Socket socket = new Socket(hostname, port);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter.println("Hello Server");
            String response = bufferedReader.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
