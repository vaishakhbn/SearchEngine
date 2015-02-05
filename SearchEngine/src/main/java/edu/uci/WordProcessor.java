package edu.uci;


import edu.uci.text.processing.Token;
import edu.uci.text.processing.Utilities;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by swanand on 2/4/2015.
 */
public class WordProcessor {
    public static final String TEXT_DIR_PATH = "./data/TextFiles/";
    public static void main(String[] args) throws IOException {
            int maxTokens =0;
            String longestFile=null;
            List<String> fileList = getFileListFrom(TEXT_DIR_PATH);
            List<Token> tokens = null;
            for(String filename : fileList){
                 tokens = Utilities.tokenizeFile(filename);
                 int tokenSize = tokens.size();
                 if(tokenSize > maxTokens){
                     maxTokens = tokenSize;
                     longestFile = filename;
                 }
            }
            System.out.println("The longest file in number of words is "+ longestFile + " with total words = "+ maxTokens);
        }

    public static List<String> getFileListFrom(String dir) {
        List<String> fileNames = new ArrayList<String>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
            System.out.println("IO Problem accessing files");
            ex.printStackTrace();
        }
        return fileNames;
    }
}
