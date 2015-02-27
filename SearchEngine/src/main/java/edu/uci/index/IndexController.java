package edu.uci.index;

import edu.uci.text.processing.Utilities;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
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
    public static void main (String[] args) throws IOException, ParseException {
        IndexConstructor indexConstructor = new IndexConstructor();


        List<String>filenames = getFileListFrom("../../data/JSONs/");
        int size = filenames.size();
//        List<String> fileList1 = filenames.subList(0, size / 5);
//        List<String> fileList2 = filenames.subList(size / 5, (2 * size / 5));
//        List<String> fileList3 = filenames.subList((2 * size / 5),(3 * size / 5));
//        List<String> fileList4 = filenames.subList((3 * size / 5), (4 * size / 5));
//        List<String> fileList5 = filenames.subList((4 * size / 5), size);
//        List<String>filenames = getFileListFrom("../../data/TextFiles/");
//        List<String>filenames = getFileListFrom("../testData/");
        JSONParser parser = new JSONParser();
        for(String file: filenames){
            JSONObject parse = (JSONObject)parser.parse(new FileReader(file));
            String inp = parse.get("text").toString();
            String url = parse.get("url").toString();
            List<String> tokenStrs = Utilities.tokenizeText(inp);
            //Get stemmed tokens: All with duplicates.
            List<StemmedTerm> allStemmedTerms = stem(tokenStrs,url);
            List<StemmedTerm> stemmedTerms = stemTokens(allStemmedTerms);
            indexConstructor.construct(stemmedTerms,allStemmedTerms);
            //Clearing Data Structures
            parse.clear();
            allStemmedTerms.clear();
            stemmedTerms.clear();
            tokenStrs.clear();
        }
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
