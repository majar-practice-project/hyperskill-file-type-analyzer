package analyzer.util;

public class Timer {
    private long time = -1;
    public void start(){
        time = System.currentTimeMillis();
    }

    public long stop(){
        if(time == -1) throw new RuntimeException("stopping a timer before starting it");
        long newTime = System.currentTimeMillis();
        long duration = newTime - time;
        time = -1;
        return duration;
    }
}
