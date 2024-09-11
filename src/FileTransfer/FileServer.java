package FileTransfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    public static void main(String[] args) {
        int port = 5000;
        int bufferSize = 1024; // Maksimal filstørrelse

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            try (Socket socket = serverSocket.accept()) {
                System.out.println("Connection successful");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Read the file name from the client
                String fileName = bufferedReader.readLine();

                // Read the file size from the client
                long fileSize = Long.parseLong(bufferedReader.readLine());

                // Check if the file size exceeds the buffer size
                if (fileSize > bufferSize) {
                    System.out.println("File size exceeds the buffer limit of " + bufferSize + " bytes.");
                    return;
                }

                System.out.println("Expected filesize: " + fileSize + " bytes.");

                // Use the received file name to create the output file path
                String outputFilePath = "C:/Users/Morte/IdeaProjects/ArrayListLinkedListSetMap/NetworkCommunication/src/FileTransfer/Client/" + fileName;

                // Receive the file data
                try (InputStream inputStream = socket.getInputStream();
                     FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
                     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

                    byte[] buffer = new byte[bufferSize];
                    int bytesRead;
                    long totalBytesRead = 0;

                    // Read file content from input stream
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        bufferedOutputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;

                        if (totalBytesRead >= fileSize) {
                            break; // Stop reading when expected file size is reached
                        }
                    }

                    // Ensure all data is written to disk
                    bufferedOutputStream.flush();
                    System.out.println("File has been received and saved as " + outputFilePath);
                }

            } catch (IOException ex) {
                System.out.println("Error while receiving the file: " + ex.getMessage());
            }

        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
        }
    }
}