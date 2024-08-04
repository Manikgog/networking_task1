package ru.gogolin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class SocketExample {
    public static void main(String[] args) throws IOException {
        try(Socket socket = new Socket("google.com", 80);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
            outputStream.writeUTF("Hello from client");
            byte[] response = inputStream.readAllBytes();
            System.out.println(Arrays.toString(response) + "\n");
            System.out.println(response.length);
        }
    }
}
