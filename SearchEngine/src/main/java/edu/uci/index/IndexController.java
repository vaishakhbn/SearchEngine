package edu.uci.index;

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
 * Created by swanand on 2/15/2015.
 */
public class IndexController {
    public static void main (String[] args) throws IOException {
        IndexConstructor indexConstructor = new IndexConstructor();
        List<String>filenames = getFileListFrom("../../data/TextFiles/");
        for(String file: filenames){
            List<Token> tokens = Utilities.tokenizeFile(file);
            List<StemmedTerm> stemmedTerms = stemTokens(Utilities.convertToTokenList(tokens), file);
            indexConstructor.construct(stemmedTerms);
        }
        indexConstructor.addIDF(filenames.size());
        indexConstructor.sortIndex();
        //@TODO: Compression <Can use util.Zip>
        indexConstructor.flush();
        System.out.println("Over with Indices for now");
    }

    private static List<StemmedTerm> stemTokens(List<String> tokens, String fileId) {
        List<StemmedTerm>stemToks = new ArrayList<StemmedTerm>();
       // @TODO: Stemmers Algorithm. Will Return a stemmed term/list of stemmed terms.
        for(String strTok: tokens){
           stemToks.add(new StemmedTerm(fileId,strTok));
        }
        return stemToks;
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
