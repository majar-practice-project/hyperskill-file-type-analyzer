package analyzer.pattern;

public class PatternFinderFactory {
    public PatternFinder getPatternFinder(String name){
        return switch (name){
            case "KMP" -> new KMPPatternFinder();
            case "naive" -> new NaivePatternFinder();
            case "RK" -> new RabinKarpPatternFinder();
            default -> throw new RuntimeException("Invalid pattern finder");
        };
    }
}
