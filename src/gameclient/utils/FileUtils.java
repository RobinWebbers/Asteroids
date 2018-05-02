package gameclient.utils;

import java.io.*;

public class FileUtils {
    
    private FileUtils(){}    
    
    public static String loadAsString(String file){
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String buffer;
            while((buffer = reader.readLine()) != null){
                result.append(buffer).append("\n");
            }
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
        return result.toString();
    }
}
