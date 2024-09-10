package FileTransfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            Socket socket = serverSocket.accept();
            System.out.println("Connection successful");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Læs filnavnet fra klienten
            String fileName = bufferedReader.readLine();
            // Opdateret sti til serverens mappe
            String outputFilePath = "C:/Users/Morte/IdeaProjects/ArrayListLinkedListSetMap/NetworkCommunication/src/FileTransfer/Client/file.txt";

            // Læs filens størrelse fra klienten
            long fileSize = Long.parseLong(bufferedReader.readLine());
            System.out.println("Expected filesize: " + fileSize + " bytes.");

            // Modtag filens data
            InputStream inputStream = socket.getInputStream();
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalBytesRead = 0;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    if (totalBytesRead >= fileSize) {
                        break;
                    }
                }

                System.out.println("File has been received and saved as " + outputFilePath);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
