package FileTransfer;

import java.io.*;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;
        int bufferSize = 1024;
        String fileName = "file.txt";
        String filePath = "C:/Users/Morte/IdeaProjects/ArrayListLinkedListSetMap/NetworkCommunication/src/FileTransfer/Server/" + fileName;

        File file = new File(filePath);

        if (file.length() > bufferSize) {
            System.out.println("File size exceeds the buffer limit of " + bufferSize + " bytes.");
            return;
        }

        try (Socket socket = new Socket(hostname, port)) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                 OutputStream outputStream = socket.getOutputStream();
                 PrintWriter printWriter = new PrintWriter(outputStream, true)) {

                // Send file name to the server
                printWriter.println(file.getName());

                // Send file size to the server
                printWriter.println(file.length());

                // Send file data to the server
                byte[] buffer = new byte[bufferSize];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.flush(); // Make sure all data is sent
                System.out.println("File has been sent to the server.");

            }

            // Remove the file
            if (file.delete()) {
                System.out.println("File has been deleted from the server.");
            } else {
                System.out.println("Failed to delete the file from the server.");
            }

        } catch (IOException ex) {
            System.out.println("Failed to move the file, does the (" + file.getName() + ") exist, or has the maximum of bytes been reached");
        }
    }
}