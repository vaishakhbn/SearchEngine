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
            icsQueryResults.add(toStringList(retr.retrieveResults(que)));
        }
        System.out.println("Google:");
        for(List<String> goog : googQueryResults){
            for(String go : goog){
                System.out.println(go);
            }
            System.out.println("=====");
        }
        System.out.println("app");
        for(List<String> ics : icsQueryResults){
            for(String go : ics){
                System.out.println(go);
            }
            System.out.println("=====");
        }
//        List<List<String>> icsResults = new ArrayList<>();
//
//        for(List<SearchResult> srch : icsQueryResults){
//            List<String> queRes = new ArrayList<>();
//            for(SearchResult sres: srch){
//                String url = sres.getUrl();
//                queRes.add(url);
//            }
//            icsResults.add(queRes);
//        }
        List<Double>ndcgs = new ArrayList<>();
        for(int i=0;i<icsQueryResults.size();i++){
            ndcgs.add(ndcg.getNDCG(icsQueryResults.get(i),googQueryResults.get(i),5));
        }
        int i=0;
        for(Double n : ndcgs){

            System.out.println("Ndcg values are:" + n + "for "+ queries.get(i++) );

        }
    }

    private static List<String> toStringList(List<SearchResult> searchResults) {
        List<String> resUrls = new ArrayList<String>();
        for(SearchResult res : searchResults){
            resUrls.add(res.getUrl());
        }
        return resUrls;
    }
}
