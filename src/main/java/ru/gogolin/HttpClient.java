package ru.gogolin;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpClient {
    private final int port;

    public HttpClient(int port) {
        this.port = port;
    }


    public void run() {
        try( ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept()) {
           processSocket(socket);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocket(Socket socket) {
        try(socket;
            InputStream inputStream = new DataInputStream(socket.getInputStream());
            OutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            System.out.println(new String(inputStream.readAllBytes()));
            writeFile(inputStream, "response.txt");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(InputStream inputStream, String fileName) throws IOException {
        File file = new File(System.getProperty("user.dir"), fileName);
        Path path = Paths.get(String.valueOf(file));
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Files.write(path, inputStream.readAllBytes());
    }
}
