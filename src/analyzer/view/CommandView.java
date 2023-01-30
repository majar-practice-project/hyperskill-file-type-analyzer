package analyzer.view;

import java.util.List;

public class CommandView {
    public void showFileType(String fileType){
        System.out.println(fileType);
    }

    public void showTimeTaken(long time){
        System.out.printf("It took %.3f seconds%n", time/1000.0);
    }

    public void showFilesType(String file, String type){
        System.out.printf("%s: %s%n", file, type);
    }
}
