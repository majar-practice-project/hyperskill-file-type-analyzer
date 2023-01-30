package analyzer.pattern;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KMPPatternFinder implements PatternFinder {
    private final Map<FileTypePattern, int[]> patterns = new LinkedHashMap<>();

    @Override
    public void setPatterns(List<FileTypePattern> patterns) {
        for (FileTypePattern pattern : patterns) {
            this.patterns.put(pattern, computePrefixFunction(pattern.getPattern()));
        }
    }

    private int[] computePrefixFunction(byte[] pattern) {
        int[] prefixFunction = new int[pattern.length];

        for (int i = 1; i < pattern.length; i++) {
            int index = i;
            while (index != 0) {
                index = prefixFunction[index - 1];
                if (pattern[i] == pattern[index]) {
                    prefixFunction[i] = index + 1;
                    break;
                }
            }
        }
        return prefixFunction;
    }

    @Override
    public FileTypePattern matchPattern(byte[] text) {
        if (patterns.isEmpty() || text.length == 0) return null;

        for (FileTypePattern pattern : patterns.keySet()) {
            if (matches(text, pattern.getPattern(), patterns.get(pattern))) return pattern;
        }
        return null;
    }

    private boolean matches(byte[] text, byte[] pattern, int[] prefixFunction) {
        int bound = text.length - pattern.length + 1;
        int i = 0;

        for (int j = 0; j < pattern.length; j++) {
            if (text[i + j] != pattern[j]) {
                if (j == 0) {
                    do {
                        if (++i >= bound) return false;
                    } while (text[i] != pattern[0]);
                } else {
                    i += j - prefixFunction[j - 1];
                    j = prefixFunction[j - 1] - 1;
                    if (i >= bound) return false;
                }
            }
        }

        return true;
    }
}
