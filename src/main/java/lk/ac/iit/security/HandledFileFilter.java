package lk.ac.iit.security;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class HandledFileFilter {
    public static List<Path> getFilesHandledBeforeTime(Path directory, long time, FileOperation operation) throws
            IOException {
        List<Path> filteredFiles = new ArrayList<>();

        if (directory != null) {
            List<Path> files = listFiles(directory);
            for (Path file : files) {
                BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);

                switch (operation) {
                    case CREATED:
                        if (attributes.creationTime().toMillis() < time) {
                            filteredFiles.add(file);
                        }
                        break;
                    case ACCESSED:
                        if (attributes.lastAccessTime().toMillis() < time) {
                            filteredFiles.add(file);
                        }
                        break;
                    case MODIFIED:
                        if (attributes.lastModifiedTime().toMillis() < time) {
                            filteredFiles.add(file);
                        }
                        break;
                }
            }
        }

        return filteredFiles;
    }

    public static List<Path> getFilesHandledAfterTime(Path directory, long time, FileOperation operation) throws IOException {
        List<Path> filteredFiles = new ArrayList<>();

        if (directory != null) {
            List<Path> files = listFiles(directory);
            for (Path file : files) {
                BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);

                switch (operation) {
                    case CREATED:
                        if (attributes.creationTime().toMillis() > time) {
                            filteredFiles.add(file);
                        }
                        break;
                    case ACCESSED:
                        if (attributes.lastAccessTime().toMillis() > time) {
                            filteredFiles.add(file);
                        }
                        break;
                    case MODIFIED:
                        if (attributes.lastModifiedTime().toMillis() > time) {
                            filteredFiles.add(file);
                        }
                        break;
                }
            }
        }

        return filteredFiles;
    }

    public static List<Path> getFilesHandledBetweenTime(Path directory, long startTime, long endTime, FileOperation operation) throws IOException {
        List<Path> filteredFiles = new ArrayList<>();

        if (directory != null) {
            List<Path> files = listFiles(directory);
            for (Path file : files) {
                BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);

                switch (operation) {
                    case CREATED:
                        long creationTime = attributes.creationTime().toMillis();
                        if ((creationTime > startTime) && (creationTime < endTime)) {
                            filteredFiles.add(file);
                        }
                        break;
                    case ACCESSED:
                        long accessedTime = attributes.lastAccessTime().toMillis();
                        if ((accessedTime > startTime) && (accessedTime < endTime)) {
                            filteredFiles.add(file);
                        }
                        break;
                    case MODIFIED:
                        long modifiedTime = attributes.lastModifiedTime().toMillis();
                        if ((modifiedTime > startTime) && (modifiedTime < endTime)) {
                            filteredFiles.add(file);
                        }
                        break;
                }
            }
        }

        return filteredFiles;
    }

    private static List<Path> listFiles(Path directory) throws IOException {
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    files.addAll(listFiles(entry));
                } else {
                    files.add(entry);
                }
            }
        }
        return files;
    }
}
