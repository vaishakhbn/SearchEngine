package edu.uci.index;

import com.mongodb.*;
import edu.uci.MongoConnector;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;

import java.net.UnknownHostException;

/**
 * Created by swanand on 2/25/2015.
 */
public class IDFSetter {
    public static final int total = 50326;
    public static void main(String args[]) throws UnknownHostException {
        Mongo mongo = MongoConnector.getInstance();
        DB icsIndex = mongo.getDB("Icsmr");
        DBCollection tfidfaggr = icsIndex.getCollection("tfidfaggr");
        DBCursor cursor = tfidfaggr.find();
        while( cursor.hasNext() ){

            DBObject next = cursor.next();
            String key = (String) next.get("_id");
            BasicDBList postings = (BasicDBList) next.get("postings");
            int df = postings.size();
            final float idf = (float) Math.log10(total/df);

            CollectionUtils.forAllDo(postings,new Closure() {
                @Override
                public void execute(Object post) {
                    BasicDBObject posting = (BasicDBObject) post;
                    double tf = (double) posting.get("tf");
                    posting.put("idf",idf);
                    posting.put("_TF_IDF",tf*idf);
                }
            });
            BasicDBObject searchQuery = new BasicDBObject().append("_id", key);
            BasicDBObject updateDocument = new BasicDBObject();
            updateDocument.append("$set", new BasicDBObject().append("postings", postings));
            tfidfaggr.update(searchQuery, updateDocument);
            System.out.println("Updated doc with key" + key);
            postings.clear();
        }
    }
}
