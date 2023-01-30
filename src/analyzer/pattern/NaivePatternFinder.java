package analyzer.pattern;

import java.util.List;

public class NaivePatternFinder implements PatternFinder {
    private List<FileTypePattern> patterns;

    private static boolean hasPattern(byte[] bytes, byte[] pattern) {
        int endBound = bytes.length - pattern.length + 1;
        for (int i = 0; i < endBound; i++) {
            boolean match = true;
            for (int j = 0; j < pattern.length; j++) {
                if (bytes[j + i] != pattern[j]) {
                    match = false;
                    break;
                }
            }
            if (match) return true;
        }
        return false;
    }

    @Override
    public void setPatterns(List<FileTypePattern> patterns) {
        this.patterns = patterns;
    }

    @Override
    public FileTypePattern matchPattern(byte[] text) {
        for (FileTypePattern pattern : patterns) {
            if (hasPattern(text, pattern.getPattern())) return pattern;
        }
        return null;
    }
}
