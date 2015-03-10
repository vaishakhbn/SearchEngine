package edu.uci.ranking;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import edu.uci.index.Porter;
import edu.uci.text.processing.Token;
import edu.uci.text.processing.Utilities;
import org.json.simple.parser.ParseException;

public class RetrieveSearchTerms {
	private String query ="";
    private MongoClient mongo;
    private DB db;
    public RetrieveSearchTerms() throws UnknownHostException {
        mongo = new MongoClient("localhost");
        db = mongo.getDB("Icsmr");
    }
    public static void main(String args[]) throws UnknownHostException
	{
        //retrieveResults(args);
	}

    public Map<String, String> retrieveResults(String[] args) throws IOException, ParseException {
        if(args.length<1)
        {
            System.out.println("Query format : ");
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            result.append( args[i]+" ");
        }
        query = result.toString();
        return queryTerm(getStems(query));
    }

    private ArrayList<String> getStems(String query) {
        StringTokenizer st = new StringTokenizer(query);
        Porter p = new Porter();
        ArrayList<String> stemmedTerms = new ArrayList<String>();
        while(st.hasMoreElements())
        {
            stemmedTerms.add(p.stripAffixes(st.nextToken()));
        }
        return stemmedTerms;
    }

    private Map<String, String> queryTerm(ArrayList<String> stemmedTerms) throws IOException, ParseException {
//        MongoClient mongo = new MongoClient("localhost");
//        DB db = mongo.getDB("Icsmr");
		DBCollection table = db.getCollection("tfidfaggr");
		DBObject match = new BasicDBObject("$match", new BasicDBObject("_id", new BasicDBObject("$in",stemmedTerms.toArray())));
		DBObject unwind = new BasicDBObject("$unwind", "$postings" );
		DBObject fields = new BasicDBObject("doc", "$postings.docId");
		fields.put("term", "$_id");
		fields.put("tfIdf", "$postings._TF_IDF");
		DBObject project = new BasicDBObject("$project", fields );
		DBObject groupFields = new BasicDBObject( "_id", "$doc");
		groupFields.put("terms", new BasicDBObject("$addToSet","$term"));
		groupFields.put("num", new BasicDBObject("$sum",1));
		groupFields.put("score", new BasicDBObject("$sum","$tfIdf"));
		DBObject group = new BasicDBObject("$group", groupFields);
		DBObject sortFields = new BasicDBObject("num",-1);
		sortFields.put("score", -1);
		DBObject sort = new BasicDBObject("$sort", sortFields);
		DBObject limit = new BasicDBObject("$limit",5);
		List<DBObject> pipeline = Arrays.asList(match, unwind, project, group, sort,limit);
		AggregationOutput output = table.aggregate(pipeline);
        List<String> top5 = new ArrayList<String>();
        Map<String,String> resultMap = new HashMap<String,String>();
		for (DBObject result : output.results()) {
		    System.out.println(result);
            String url = String.valueOf(result.get("_id"));
            String snippet = procureSnippet(url,query);
            if(url.charAt(url.length()-1) == '/'){
                url =url.substring(0,url.length()-1);
            }
		    top5.add(url);
            resultMap.put(url,snippet);
        }

    return resultMap;
	}

    private String procureSnippet(String url, String query) throws IOException, ParseException {
        String snippet = "";
        String[] qterms = query.split(" ");
        DBCollection docs = db.getCollection("docs");
        BasicDBObject param = new BasicDBObject();
        param.put("url",url);
        DBObject urlDoc = docs.findOne(param);
        String docText = (String) urlDoc.get("text");
        List<String> words = tokenizeText(docText);
        int firstIndex = words.indexOf(qterms[0]);
        //int lastIndex = words.lastIndexOf(qterms[0]);
        if(firstIndex!=-1){
            for(String snips : words.subList(firstIndex,firstIndex+8)){
                snippet+=snips;
                snippet+=" ";
            }
            snippet+="...";
        }
        return snippet;
    }

    private List<String> tokenizeText(String docText) {
        List<String> input = new ArrayList<>();
        String alphaNumericOnly = docText.replaceAll("[^a-zA-Z0-9,-\\./:]+"," ");
        StringTokenizer st = new StringTokenizer(alphaNumericOnly);
        while(st.hasMoreTokens())
        {
            String a = st.nextToken();
            if(!(a.equalsIgnoreCase(" ")))
            {
                input.add(a.toLowerCase());
            }
        }
        return input;
    }


}


/*
 * 
 * db.tfidfaggr.aggregate([{$match : {_id:{ $in: ["crista","vaishakh"]}}},{$unwind : "$postings"},{$group : {_id : "$postings.docId"}}, {$sort : {"postings._TF_IDF":-1}},  { $limit : 5 }])


db.tfidfaggr.aggregate([{$match : {_id:"crista"}},{$unwind : "$postings"},{$sort : {"postings._TF_IDF":-1}}])


db.tfidfaggr.aggregate([{$match : {_id:"crista"}},{$unwind : "$postings"},{$group : {_id : "$postings.docId",terms:{$addTOSet:"$term"}}}, {$sort : {"postings._TF_IDF":-1}},  { $limit : 10 }])



db.tfidfaggr.aggregate([{"$match": { "_id":{ "$in": ["crista","lope"]}}},
                                     {$unwind:"$postings"},
                                     {"$project":{"doc":"$postings.docId","term":"$_id","tfIdf":"$postings._TF_IDF"}},
                                     {"$group" : {"_id":"$doc","terms" : { "$addToSet" : "$term"},"num":{"$sum":1},"score":{"$sum":"$tfIdf"}}},
                                     {"$sort":{"num":-1,"score":-1}},
                                     {"$limit":5}]).pretty()


db.tfidfaggr.aggregate([{"$match": 
	{ "_id":{ "$in": ["machin","learn"]}}},
                                     {$unwind:"$postings"},
                                     {"$project":{"doc":"$postings.docId","term":"$_id","tfIdf":"$postings._TF_IDF"}},
                                     {"$group" : {"_id":"$doc","terms" : { "$addToSet" : "$term"},"num":{"$sum":1},"score":{"$sum":"$tfIdf"}}},
                                     {"$sort":{"num":-1,"score":-1}}])


 */

