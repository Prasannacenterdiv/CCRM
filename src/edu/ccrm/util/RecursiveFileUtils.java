package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

public class RecursiveFileUtils {

    public static long calculateDirectorySize(Path path) {

        final AtomicLong size = new AtomicLong(0);
        try {
            Files.walk(path)
                    .forEach(p -> {
                        if (Files.isRegularFile(p)) {
                            try {
                                size.addAndGet(Files.size(p));
                            } catch (IOException e) {
                                System.err.println("Cannot get size of file: " + p);
                            }
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error walking the directory: " + path);
            return 0;
        }
        return size.get();
    }
}