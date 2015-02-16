package edu.uci.index;

import edu.uci.text.processing.Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by swanand on 2/14/2015.
 */
public class IndexConstructor {
    private Map<String,List<Posting>> partialIndex;

    public IndexConstructor() {
        this.partialIndex = new HashMap<>();
    }

    public void construct(List<StemmedTerm> stemTokens) {

        LinkedHashMap<String, Integer> stemFrequencies = Utilities.computeStemFrequencies(stemTokens);
        int totalTerms = stemTokens.size();
        for(StemmedTerm stem : stemTokens){
            List<Posting> postings;
            String stemTerm = stem.getStem();

            if(partialIndex.containsKey(stemTerm)){
                List<Posting> posts = partialIndex.get(stemTerm);
                Posting post = buildPostingFor(stem,stemFrequencies,totalTerms);
                if(!posts.contains(post)){
                    posts.add(post);
                }
            }else{
                postings = new ArrayList<>();
                Posting posting = buildPostingFor(stem,stemFrequencies,totalTerms);
                postings.add(posting);
                partialIndex.put(stem.getStem(),postings);
            }

        }
        System.out.println("In Construction: " + partialIndex.keySet().size());
    }

    private Posting buildPostingFor(StemmedTerm stem, LinkedHashMap<String, Integer> stemFrequencies, int totalTerms) {
        float tf = (float)stemFrequencies.get(stem.getStem())/totalTerms;
        Posting posting = new Posting(stem.getDocId(), stem.getPositions());
        posting.setTf(tf);
        return posting;
    }

    public void flush() {
        //@TODO: Write the index to file
        try
        {
            FileOutputStream fos = new FileOutputStream("index.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(partialIndex);
            oos.close();
            fos.close();
            System.out.printf("Serialized HashMap data is saved in hashmap.ser");
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }


    }
}
