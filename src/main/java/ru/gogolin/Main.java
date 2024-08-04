package ru.gogolin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        int length = Files.readAllBytes(Path.of("src/main/java/resources/header.txt")).length;
        System.out.println(length);
    }
}
