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
//        List<String>filenames = getFileListFrom("../testData/");
        for(String file: filenames){
            List<Token> tokens = Utilities.tokenizeFile(file);
            List<String> tokenStrs =  Utilities.convertToTokenList(tokens);
            //Get stemmed tokens: All with duplicates.
            List<StemmedTerm> allStemmedTerms = stem(tokenStrs,file);
            List<StemmedTerm> stemmedTerms = stemTokens(allStemmedTerms);
            indexConstructor.construct(stemmedTerms,allStemmedTerms);
        }
        indexConstructor.addIDF(filenames.size());
        indexConstructor.sortIndex();
        //@TODO: Compression <Can use util.Zip>
        indexConstructor.flush();
        System.out.println("Over with Indices for now");
    }

    private static List<StemmedTerm> stem(List<String> tokenStrs, String fileId) {
        List<StemmedTerm> allStemWords=new ArrayList<>();
        Porter porterStemmer = new Porter();
        for(String tok: tokenStrs){
            String stem = porterStemmer.stripAffixes(tok);
            allStemWords.add(new StemmedTerm(fileId,stem));
        }
        return allStemWords;
    }

    private static List<StemmedTerm> stemTokens(List<StemmedTerm> stemTtokens) {
        List<StemmedTerm>stemToks = new ArrayList<StemmedTerm>();
        int count=0;
        for(StemmedTerm stemTerm: stemTtokens){
            if(stemToks.contains(stemTerm)){
                StemmedTerm st = stemToks.get(stemToks.indexOf(stemTerm));
                st.getPositions().add(count);
            }else{
                stemTerm.getPositions().add(count);
                stemToks.add(stemTerm);
            }
            count++;
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
