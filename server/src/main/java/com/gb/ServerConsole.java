package com.gb;

import com.gb.classes.command.Catalog;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ServerConsole {
    public static void main(String[] args) throws IOException {
        Path root = Paths.get("Root");
//        Files.createDirectory(root);
        Path home = Paths.get(root + "/" + "user1");
//        Files.createDirectory(home);

        Catalog catalog = new Catalog();
        final Path[] path = new Path[1];

        Files.walkFileTree(home, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                catalog.add(dir, path[0]);
                path[0] = dir;
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                catalog.add(file, path[0]);
                return FileVisitResult.CONTINUE;
            }
        });

        catalog.getCatalog().forEach((k, v) -> {
            System.out.println(k + " лежит в " + v);
        });

    }
}
