package ru.gogolin.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        try(Socket socket = new Socket("localhost", 8081);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in)) {

            while(scanner.hasNextLine()){
                outputStream.writeUTF(scanner.nextLine());
                System.out.println("Сервер: " + inputStream.readUTF());
            }
        }
    }
}
