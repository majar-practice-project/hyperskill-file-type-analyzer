package analyzer.io;

import analyzer.pattern.FileTypePattern;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PatternsReader {
    public List<FileTypePattern> readPatterns(String path){
        List<FileTypePattern> result = new ArrayList<>();
        try(Scanner scanner = new Scanner(Path.of(path))){
            scanner.useDelimiter(Pattern.compile("[;\n]"));
            while(scanner.hasNext()){
                int priority = scanner.nextInt();
                String pattern = removeQuotes(scanner.next());
                String fileType = removeQuotes(scanner.next());
                result.add(new FileTypePattern(pattern.getBytes(), priority, fileType));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        result.sort(Comparator.comparingInt(FileTypePattern::getPriority).reversed());
        return result;
    }

    private static String removeQuotes(String s){
        return s.replaceAll("^\"|\"$", "");
    }
}
