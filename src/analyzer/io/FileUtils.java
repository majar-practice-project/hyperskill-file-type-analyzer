package analyzer.io;

import analyzer.pattern.FileTypePattern;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileUtils {
    public static List<Path> getAllFiles(Path dir) {
        try (Stream<Path> pathStream = Files.list(dir)) {
            return pathStream.toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static byte[] getBytes(Path file) {
        try (FileInputStream stream = new FileInputStream(file.toString())) {
            return stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
