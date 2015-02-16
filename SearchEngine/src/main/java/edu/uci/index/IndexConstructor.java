package edu.uci.index;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swanand on 2/14/2015.
 */
public class IndexConstructor {
    private Map<String,List<Posting>> partialIndex;

    public IndexConstructor() {
        this.partialIndex = new HashMap<>();
    }

    public void construct(List<StemmedTerm> stemTokens) {
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
//        System.out.println("In Construction: " + partialIndex.keySet().size());
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
