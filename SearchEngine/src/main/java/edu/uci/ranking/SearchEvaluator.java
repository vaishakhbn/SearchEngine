package edu.uci.ranking;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by swanand on 3/13/2015.
 */
public class SearchEvaluator {

    public static void main(String[] args) throws IOException, ParseException {
        GoogleSearcher googleSearcher = new GoogleSearcher();
        RetrieveSearchTerms retr = new RetrieveSearchTerms();
        List<List<String>> googQueryResults = new ArrayList<List<String>>();
        List<List<String>> icsQueryResults = new ArrayList<>();
        NDCG ndcg = new NDCG();

        List<String> queries = new ArrayList<String>(Arrays.asList(new String[]{"mondego","machine learning","software engineering","security","student affairs","graduate courses","Crista Lopes","REST","computer games","information retrieval"}));
        for(String query : queries){
            String[] que = new String[]{query};
            googQueryResults.add(googleSearcher.getSearchResults(query, 5));
            icsQueryResults.add(retr.getTop5UrlsForNdcg(que));
        }

        List<Double>ndcgs = new ArrayList<>();
        for(int i=0;i<icsQueryResults.size();i++){
            ndcgs.add(ndcg.getNDCG(icsQueryResults.get(i),googQueryResults.get(i),5));
        }
        int i=0;
        for(double n : ndcgs){
            System.out.println("For query: " + queries.get(i)+" : "+n);
            i++;
        }
    }
}
