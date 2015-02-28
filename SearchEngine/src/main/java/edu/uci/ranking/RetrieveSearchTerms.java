package edu.uci.ranking;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import edu.uci.index.Porter;
 
public class RetrieveSearchTerms {
	public static void main(String args[]) throws UnknownHostException
	{
		if(args.length<1)
		{
			System.out.println("Query format : ");
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
		   result.append( args[i]+" ");
		}
		String query = result.toString();
		Porter p = new Porter();
		
		StringTokenizer st = new StringTokenizer(query);
		ArrayList<DBObject> dbObject = new ArrayList<DBObject>();
		ArrayList<String> stemmedTerms = new ArrayList<String>();
		while(st.hasMoreElements())
		{
			
			stemmedTerms.add(p.stripAffixes(st.nextToken()));
		}
		queryTerm(stemmedTerms);

				
	}

	private static void queryTerm(ArrayList<String> stemmedTerms) throws UnknownHostException 
	{
		MongoClient mongo = new MongoClient("localhost");
		DB db = mongo.getDB("IcsmrFinal");
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
		for (DBObject result : output.results()) {
		    System.out.println(result);
		}
		

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

