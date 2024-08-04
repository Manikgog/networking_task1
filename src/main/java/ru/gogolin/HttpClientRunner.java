package ru.gogolin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.net.http.HttpRequest.BodyPublishers.ofFile;

/**
 * Hello world!
 *
 */
public class HttpClientRunner
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082"))
                .header("content-type", "application/json")
                .POST(ofFile(Path.of("src/main/java/resources/file.json")))
                .build();

        var response1 = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        StringBuilder sb = new StringBuilder();
        writeFile(sb.append(response1.body()).toString(), "response.html");

    }

    public static void writeFile(String response, String fileName) throws IOException {
        File file = new File(System.getProperty("user.dir"), fileName);
        Path path = Paths.get(String.valueOf(file));
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Files.write(path, response.getBytes());
    }
}
