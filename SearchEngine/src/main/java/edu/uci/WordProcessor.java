package edu.uci;


import edu.uci.text.processing.Token;
import edu.uci.text.processing.TwoGram;
import edu.uci.text.processing.Utilities;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by swanand on 2/4/2015.
 */
public class WordProcessor {
    public static final String TEXT_DIR_PATH =  "./data/TextFiles/";
    public static final String STOP_FILE = "./stopwords.txt";
    public static LinkedHashMap<String,Integer> wordFreq = new LinkedHashMap<String, Integer>();
    public static LinkedHashMap<TwoGram, Integer> twoGramsFreq = new LinkedHashMap<TwoGram, Integer>();
    public static void main(String[] args) throws IOException {
            int maxTokens =0;
            String longestFile=null;
            List<Token> stopwords = Utilities.tokenizeFile(STOP_FILE);
            List<String> fileList = getFileListFrom(TEXT_DIR_PATH);
            List<Token> tokens = null;
            for(String filename : fileList){
                 tokens = Utilities.tokenizeFile(filename);
                 int tokenSize = tokens.size();
                 if(tokenSize > maxTokens){
                     maxTokens = tokenSize;
                     longestFile = filename;
                 }

                CollectionUtils.filter(tokens, new Predicate<Token>() {
                    @Override
                    public boolean evaluate(Token token) {
                        if(token.getTokenLength() < 2){
                            return false;
                        }
                        return true;
                    }
                });
                manageTwoGrams(tokens, stopwords, twoGramsFreq);
                tokens.removeAll(stopwords);
                maintainFrequency(tokens);

                tokens.clear();
            }

        System.out.println("The longest file in number of words is "+ longestFile + " with total words = "+ maxTokens);

        LinkedHashMap<String, Integer> tokenFreqs = Utilities.sortByValue(wordFreq);
        System.out.println("The 500 most occurring words are: ");
        int count = 1;
        for (Map.Entry<String, Integer> entry : tokenFreqs.entrySet())
        {
            System.out.println(entry.getKey() + " - " + entry.getValue());
            if(count++ > 500) break;
        }

        Map<TwoGram, Integer> twoGramTokFreq = Utilities.sortByValue(twoGramsFreq);
        System.out.println("The 20 most commonly occurring 2 grams are: ");
        count =1;
        for (Map.Entry<TwoGram, Integer> entry : twoGramTokFreq.entrySet())
        {
            System.out.println(entry.getKey().getFirstGram() + " - " +entry.getKey().getSecondGram()+" : "+ entry.getValue());
            if(count++ > 20) break;
        }
    }

    private static void manageTwoGrams(List<Token> tokens, List<Token> stopwords, LinkedHashMap<TwoGram, Integer> twoGramsFreq) {
        List<String> stringTokens = Utilities.convertTokenObjectToList(tokens);
        List<String> stringStopwords = Utilities.convertTokenObjectToList(stopwords);
        twoGramsFreq = build(stringTokens,stringStopwords, twoGramsFreq);
    }

    private static LinkedHashMap<TwoGram, Integer> populateTwoGramFrequency(TwoGram twoGram, LinkedHashMap<TwoGram, Integer> twoGramsFreq) {

        if(twoGramsFreq.containsKey(twoGram)){
            twoGramsFreq.put(twoGram, (twoGramsFreq.get(twoGram)) + 1);
        }else {
            twoGramsFreq.put(twoGram, 1);
        }
        return twoGramsFreq;
    }

    private static LinkedHashMap<TwoGram,Integer> build(List<String> tokens, List<String> stringStopwords, LinkedHashMap<TwoGram, Integer> twoGramsFreq) {
        String firstGram = "";
        String secondGram = "";
        for(ListIterator<String>listIterator = tokens.listIterator(); listIterator.hasNext();){
            firstGram = listIterator.next();
            if(listIterator.hasNext())
                secondGram = listIterator.next();
            if(!stringStopwords.contains(firstGram) && !stringStopwords.contains(secondGram)){
                twoGramsFreq = populateTwoGramFrequency(new TwoGram(firstGram, secondGram), twoGramsFreq);
            }
            if(listIterator.hasNext())
                listIterator.previous();
        }
        return twoGramsFreq;
    }
    private static void maintainFrequency(List<Token> tokens) {

            List<String> stringTokens = Utilities.convertToTokenList(tokens);
            for(String token : stringTokens){
                if(wordFreq.containsKey(token)){
                    wordFreq.put(token, (wordFreq.get(token)) + 1);
                }else {
                    wordFreq.put(token, 1);
                }
            }
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
