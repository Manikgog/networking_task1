package ru.gogolin;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {
    private final int port;
    //private final ExecutorService executorService;
    private final ObjectMapper mapper = new ObjectMapper();

    public HttpServer(int port) {
        this.port = port;
        //executorService = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept()){
                System.out.println("Socket accepted");
                processSocket(socket);
            }catch (IOException e) {
                    e.printStackTrace();
                }
            }

    private void processSocket(Socket socket) {
        try(socket;
            InputStream inputStream = new DataInputStream(socket.getInputStream());
            OutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            byte[] bytes = inputStream.readNBytes(132);
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append((char) aByte);
            }
            int indexOfLength = sb.toString().toLowerCase().indexOf("content-length:") + "content-length: ".length();
            sb = new StringBuilder();
            for(int i = indexOfLength; i < bytes.length; i++) {
                if(bytes[i] == '\r' || bytes[i] == '\n'){
                    break;
                }
                sb.append((char) bytes[i]);
            }
            int contentLength = Integer.parseInt(sb.toString());
            bytes = inputStream.readNBytes(contentLength);
            sb = new StringBuilder();
            boolean isBeginBody = false;
            for (byte aByte : bytes) {
                if (isBeginBody) {
                    sb.append((char) aByte);
                    continue;
                }
                if (aByte == '{') {
                    isBeginBody = true;
                    sb.append((char) aByte);
                }
            }
            SalaryBy salaryBy = mapper.readValue(sb.toString(), SalaryBy.class);

            int totalIncome = salaryBy.getEmployees().stream().mapToInt(Employee::getSalary).sum();
            int totalTax = salaryBy.getEmployees().stream().mapToInt(Employee::getTax).sum();
            int totalProfit = totalIncome - totalTax;

            StringBuilder resultHTMLBody = new StringBuilder(Files.readString(Path.of("src/main/java/resources/example.html")));
            resultHTMLBody.replace(resultHTMLBody.indexOf("${total_income}"), resultHTMLBody.indexOf("${total_income}") + "${total_income}".length(), String.valueOf(totalIncome));
            resultHTMLBody.replace(resultHTMLBody.indexOf("${total_tax}"), resultHTMLBody.indexOf("${total_tax}") + "${total_tax}".length(), String.valueOf(totalTax));
            resultHTMLBody.replace(resultHTMLBody.indexOf("${total_profit}"), resultHTMLBody.indexOf("${total_profit}") + "${total_profit}".length(), String.valueOf(totalProfit));

            byte[] body = resultHTMLBody.toString().getBytes();
            outputStream.write("""
            HTTP/1.1 200 OK
            content-type: text/html
            content-length: %s
            """.formatted(body.length).getBytes()
            );
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(body);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
