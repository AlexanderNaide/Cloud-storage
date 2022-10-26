package com.gb;

import com.gb.classes.Catalog;
import com.gb.classes.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class ServerConsole {
    public static void main(String[] args) throws IOException {
        Path root = Paths.get("Root");
//        Files.createDirectory(root);
        Path home = Paths.get(root + "/" + "user1");
//        Files.createDirectory(home);

        Catalog catalog = new Catalog();

        Files.walkFileTree(home, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                catalog.add(new File(dir.getFileName().toString(), true, dir.toString()), new File(dir.getFileName().toString(), true, dir.toString()));
                System.out.println("File - " + dir.getFileName() + "; address - " + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("File - " + file.getFileName() + "; address - " + file);
                return FileVisitResult.CONTINUE;
            }
        });

    }
}
