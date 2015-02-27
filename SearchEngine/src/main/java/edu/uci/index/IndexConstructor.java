package edu.uci.index;

import com.mongodb.*;
import com.mongodb.util.JSON;
import edu.uci.MongoConnector;
import edu.uci.text.processing.Utilities;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by swanand on 2/14/2015.
 */
public class IndexConstructor {
    private Map<String, List<Posting>> partialIndex;
    private Mongo mongo;
    private DB icsIndex;
    private DBCollection tfidf;

    public IndexConstructor() throws UnknownHostException {
        Mongo mongo = MongoConnector.getInstance();
        icsIndex = mongo.getDB("Icsmr");
        tfidf = icsIndex.getCollection("tfidf");
        this.partialIndex = new HashMap<>();
   }

    public void construct(List<StemmedTerm> stemTokens, List<StemmedTerm> allTokens) throws IOException {
        LinkedHashMap<String, Integer> stemFrequencies = Utilities.computeStemFrequencies(allTokens);
        int totalTerms = allTokens.size();
        for (StemmedTerm stem : stemTokens) {
            String stemTerm = stem.getStem();
            if (partialIndex.containsKey(stemTerm)) {
                List<Posting> posts = partialIndex.get(stemTerm);
                Posting post = buildPostingFor(stem, stemFrequencies, totalTerms);
                if (!posts.contains(post)) {
                    posts.add(post);
                }
            } else {
                List<Posting> postings = new ArrayList<>();
                Posting posting = buildPostingFor(stem, stemFrequencies, totalTerms);
                postings.add(posting);
                partialIndex.put(stem.getStem(), postings);
            }
        }
        System.out.println("In Construction: " + partialIndex.keySet().size());
        stemFrequencies.clear();
        if(partialIndex.keySet().size() > 50000){
            flush();
            partialIndex.clear();
        }
    }

    private Posting buildPostingFor(StemmedTerm stem, LinkedHashMap<String, Integer> stemFrequencies, int totalTerms) {
        float tf = (float) (1 + Math.log10(stemFrequencies.get(stem.getStem())));
        Posting posting = new Posting(stem.getDocId(), stem.getPositions());
        posting.setTf(tf);

        return posting;
    }

    public void flush() throws IOException {
        //@TODO: Write the index to file
        if(!partialIndex.isEmpty()){
            flushToMongo();
        }
        else{
            System.out.println("Index done");
        }
//        try {
//            FileOutputStream fos = new FileOutputStream("index.txt");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(partialIndex);
//            oos.close();
//            fos.close();
//            System.out.printf("Serialized HashMap data is saved in hashmap.ser");
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
    }

    private void flushToMongo() throws IOException {

        Collection<List<Posting>> allPosts = partialIndex.values();
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<String,List<Posting>> entry: partialIndex.entrySet()){
            BasicDBObject term = new BasicDBObject().append("term", entry.getKey());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(entry.getValue());
            DBObject post = (DBObject) JSON.parse(json);
            term.append("postings",post);
            tfidf.save(term);
        }
    }

//    public void addIDF(final int totalSize) {
//        Collection<List<Posting>> allPostings = partialIndex.values();
//        CollectionUtils.forAllDo(allPostings, new Closure() {
//
//            @Override
//            public void execute(Object postings) {
//                List<Posting> posts = (List<Posting>) postings;
//                final float idf = (float) totalSize / posts.size();
//                CollectionUtils.forAllDo(posts, new Closure() {
//                    @Override
//                    public void execute(Object post) {
//                        Posting posting = (Posting) post;
//                        posting.setIdf(idf);
//                    }
//                });
//            }
//        });
//    }

//    public void sortIndex() {
//        Map<String, List<Posting>> sortedMap = new TreeMap<String, List<Posting>>(partialIndex);
//        partialIndex = sortedMap;
//    }
}