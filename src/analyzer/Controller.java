package analyzer;

import analyzer.concurrent.ConcurrentExecutor;
import analyzer.io.FileUtils;
import analyzer.io.PatternsReader;
import analyzer.pattern.FileTypePattern;
import analyzer.pattern.PatternFinder;
import analyzer.pattern.PatternFinderFactory;
import analyzer.util.Timer;
import analyzer.view.CommandView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Controller {
    private final PatternFinderFactory patternFinderFactory = new PatternFinderFactory();
    private final PatternFinder patternFinder = patternFinderFactory.getPatternFinder("RK");
    private final CommandView view = new CommandView();

    public Controller() {
    }

    private static String getFileType(byte[] fileBytes, PatternFinder patternFinder) {
        FileTypePattern pattern = patternFinder.matchPattern(fileBytes);

        return pattern == null ? "Unknown file type" : pattern.getFileType();
    }

    public void start(String[] args) {
        matchPattern(args);
    }

    private void matchPattern(String[] args) {
        List<FileTypePattern> patterns = new PatternsReader().readPatterns(args[1]);
        patternFinder.setPatterns(patterns);

        List<Path> files = FileUtils.getAllFiles(Path.of(args[0]));

        ExecutorService executor = new ConcurrentExecutor();
        files.forEach(file -> executor.submit(() -> {
            String fileName = file.getFileName().toString();
            String fileType = getFileType(FileUtils.getBytes(file), patternFinder);
//            view.showFilesType(fileName, fileType);
        }));
        executor.shutdown();
    }

    public void compareNaiveAndKMP(String[] args) {
        List<FileTypePattern> patterns = new PatternsReader().readPatterns(args[1]);

        try (FileInputStream stream = new FileInputStream(args[1])) {
            byte[] bytes = stream.readAllBytes();
            Timer timer = new Timer();
            timer.start();
            view.showFileType(getFileType(bytes, new PatternFinderFactory().getPatternFinder(args[0].substring(2))));
            view.showTimeTaken(timer.stop());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}