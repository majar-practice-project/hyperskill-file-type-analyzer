package analyzer.pattern;

import java.util.List;

public interface PatternFinder {
    void setPatterns(List<FileTypePattern> patterns);

    FileTypePattern matchPattern(byte[] text);
}
