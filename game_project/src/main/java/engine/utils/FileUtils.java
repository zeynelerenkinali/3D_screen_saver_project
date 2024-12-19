package engine.utils;
// All helper functions

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class FileUtils {
    public static String loadAsString(String path){
        StringBuilder result = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (String line : lines) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Couldn't find the file at " + path);
        }
        return result.toString();
    }
}
