package FileTransfer;

import java.io.*;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;
        String filePath = "C:/Users/Morte/IdeaProjects/ArrayListLinkedListSetMap/NetworkCommunication/src/FileTransfer/Server/file.txt";

        File file = new File(filePath);

        try (Socket socket = new Socket(hostname, port)) {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            // Send filnavn til serveren
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println(file.getName());

            // Send filens størrelse til serveren
            printWriter.println(file.length());

            // Send filens data til serveren
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            bufferedInputStream.close();
            System.out.println("File has been sent to client.");

            // Slet filen fra klienten efter overførsel
            file.delete(); // Sletter filen uden at tjekke resultatet

        } catch (IOException ex) {
            System.out.println("Failed to move the file, does the (" + file.getName() + ") exist?");
        }
    }
}