package analyzer.pattern;

public class FileTypePattern {
    private final byte[] pattern;
    private final int priority;
    private final String fileType;

    public FileTypePattern(byte[] pattern, int priority, String fileType) {
        this.pattern = pattern;
        this.priority = priority;
        this.fileType = fileType;
    }

    public byte[] getPattern() {
        return pattern;
    }

    public int getPriority() {
        return priority;
    }

    public String getFileType() {
        return fileType;
    }
}
