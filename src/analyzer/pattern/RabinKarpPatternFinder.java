package analyzer.pattern;

import java.util.List;

public class RabinKarpPatternFinder implements PatternFinder{
    private List<FileTypePattern> patterns;

    @Override
    public void setPatterns(List<FileTypePattern> patterns) {
        this.patterns = patterns;
    }

    @Override
    public FileTypePattern matchPattern(byte[] text) {
        for(FileTypePattern pattern: patterns){
            if(patternMatches(text, pattern.getPattern())) return pattern;
        }
        return null;
    }

    private boolean patternMatches(byte[] text, byte[] pattern){
        if(text.length < pattern.length) return false;

        HashingExecutor hashingExecutor = new HashingExecutor(pattern.length);
        int patternHashCode = hashingExecutor.hash(pattern);

        int textHashCode = hashingExecutor.hash(text, pattern.length);
        if(patternHashCode==textHashCode && stringsTheSame(text, pattern, 0)) return true;

        int bound = text.length - pattern.length + 1;
        int j = pattern.length;
        for(int i=1; i<bound; i++){
            textHashCode = hashingExecutor.roll(textHashCode, text[i-1], text[j]);
            j++;
            if(patternHashCode==textHashCode && stringsTheSame(text, pattern, i)) return true;
        }

        return false;
    }

    private boolean stringsTheSame(byte[] text, byte[] substring, int offset){
        for(int i=0; i<substring.length; i++){
            if(text[offset+i] != substring[i]) return false;
        }
        return true;
    }

    static class HashingExecutor{
        private final int M = 10_007;
        private final int A = 100;
        private final int coeff;

        public HashingExecutor(int length) {
            int temp = 1;
            for(int i=1; i<length; i++){
                temp *= A;
                temp %= M;
            }
            coeff = temp;
        }

        public int hash(byte[] bytes){
            return hash(bytes, bytes.length);
        }

        public int hash(byte[] bytes, int end){
            int res = 0;
            for(int i=0; i<end; i++){
                res*=A;
                res += bytes[i];
                res %= M;
            }
            return res;
        }

        public int roll(int hashValue, byte prev, byte next){
            hashValue -= prev * coeff;
            hashValue*=A;
            hashValue += next;
            hashValue%=M;
            if(hashValue<0) hashValue+=M;
            return hashValue;
        }
    }
}
