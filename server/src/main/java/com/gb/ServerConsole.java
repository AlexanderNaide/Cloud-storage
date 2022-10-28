package com.gb;

import com.gb.classes.command.Catalog;

import java.io.File;
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

        Files.walkFileTree(home, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                catalog.add(file);
                return FileVisitResult.CONTINUE;
            }
        });

        catalog.getCatalog().forEach((k) -> {
            Path path1 = k.toPath();
            System.out.println(path1);
            for (int i = 1; i < path1.getNameCount() - 1; i++) {
                System.out.println(path1.getName(i));
            }
/*            for (int i = path1.getNameCount() - 2; i > 0; i--) {
                System.out.println(path1.getName(i));
            }*/
        });

    }
}
