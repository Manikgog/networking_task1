package ru.gogolin.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(8081);
        Socket socket = serverSocket.accept();
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in)) {
            String request = inputStream.readUTF();
            while (!request.equals("exit")) {
                System.out.println("Клиент: " + request);
                outputStream.writeUTF(scanner.nextLine());
                request = inputStream.readUTF();
            }
        }
    }
}
