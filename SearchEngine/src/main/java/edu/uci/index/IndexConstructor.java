package edu.uci.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swanand on 2/14/2015.
 */
public class IndexConstructor {
    public void construct(List<StemmedTerm> stemTokens) {
        Map<String,List<Posting>> partialIndex = new HashMap<>();

        for(StemmedTerm stem : stemTokens){
            List<Posting> postings;
            if(partialIndex.containsKey(stem.getStem())){
                postings = partialIndex.get(stem.getStem());
            }else{
                postings = new ArrayList<>();
            }
            Posting posting = new Posting(stem.getDocId(), stem.getPositions());
            postings.add(posting);
            partialIndex.put(stem.getStem(),postings);
        }
    }
}
