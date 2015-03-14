package edu.uci.ranking;

import edu.uci.index.Porter;
import edu.uci.text.processing.Utilities;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

/**
 * Created by swanand on 3/14/2015.
 */
public class Ranker {
    public List<String> rank(List<String> prospectiveUrls, String query) throws IOException, ParseException {
        List<String>finalRanks = new ArrayList<String>();
        Map<String, List<String>> urlMap = mapURLsToStems(prospectiveUrls);
        List<String> queryTerms = Arrays.asList(query.split(" "));
        List<String> stemQueries = stem(queryTerms);
        LinkedHashMap<String,Integer> proxMap = new LinkedHashMap<>();
        for(String stQuery: stemQueries){
            for(Map.Entry<String,List<String>>entry:urlMap.entrySet()){
                List<String> stems = entry.getValue();
                if(stems.contains(stQuery)){
                   proxMap.put(entry.getKey(),stems.indexOf(stQuery));
                }
            }
        }
        LinkedHashMap<String, Integer> sortedMap = Utilities.sortByValue(proxMap);
        LinkedHashMap<String,Integer> fullsort = sortbyLength(sortedMap);
        for(Map.Entry<String,Integer> ent : fullsort.entrySet()){
            finalRanks.add(ent.getKey());
        }
        if(finalRanks.size()<5){
            int diff = 5 - finalRanks.size();
            prospectiveUrls.removeAll(finalRanks);
            for(int i=0;i<diff;i++){
                finalRanks.add(prospectiveUrls.get(i));
            }
        }
        return finalRanks;
    }

    private LinkedHashMap sortbyLength(LinkedHashMap<String, Integer> sortedMap) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>( sortedMap.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            @Override
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                if(o1.getValue() == o2.getValue())
                    return ((Integer)o1.getKey().length()).compareTo(o2.getKey().length());
                else return 1;
            }
        } );

        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;

    }

    private Map<String, List<String>> mapURLsToStems(List<String> prospectiveUrls) throws IOException, ParseException {
        Map<String,List<String>> urlMap = new HashMap<>();
        for(String prospect: prospectiveUrls){
            List<String> tokens = Utilities.tokenizeText(prospect);
            List<String> stems = stem(tokens);
            urlMap.put(prospect,stems);
        }
        return urlMap;
    }

    private List<String> stem(List<String> tokens) {
        Porter porter = new Porter();
        List<String> stems = new ArrayList<String>();
        for(String tok : tokens){
            String stem = porter.stripAffixes(tok);
            stems.add(stem);
        }
        return stems;
    }
}
