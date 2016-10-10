package lk.ac.iit.security;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Optional;

public class HandledFileChecker {
    public static void main(String... args) {
        //  test filter 1
        Path downloads = Paths.get("/home/user/Downloads");

        try {
            //  test filter 1 - after certain time
            displayResults(HandledFileFilter.getFilesHandledAfterTime(downloads, 1476123209753L, FileOperation
                    .CREATED));

            //  test filter 2 - before certain time
            displayResults(HandledFileFilter.getFilesHandledAfterTime(downloads, 1476123209753L, FileOperation
                    .CREATED));

            //  test filter 3 - in between times
            // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayResults(List<Path> files) {
        Optional.ofNullable(files)
                .ifPresent(list -> list.stream()
                        .forEach(file -> {
                            try {
                                BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
                                System.out.println(file.toString() + "\tcreation time: " + attributes.creationTime() +
                                        "\tlast access time: " + attributes.lastAccessTime() + "\tlast modified time: " +
                                        attributes.lastModifiedTime());
                            } catch (IOException e) {
                                // ignore the exception
                            }
                        }));
    }
}
