package edu.uci.ranking;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by swanand on 3/14/2015.
 */
public class Scorer {
    public static double TFIDF_WT = 0.3;
    public static double TITLE_WT =10;
    public static double POS_WT = 0.3;
    private MongoClient mongo;
    private DB db;
    public Scorer() throws UnknownHostException {
        mongo = new MongoClient("localhost");
        db = mongo.getDB("Icsmr");
    }
    public Map<String,Double> score(Iterable<DBObject> results, String query) {

        LinkedHashMap<String,Double> rankedUrls = new LinkedHashMap<>();

            for(DBObject result : results){
                double score = 0;
                String url = String.valueOf(result.get("_id"));
                double tfidf = (double) result.get("score");
                ArrayList<ArrayList<Integer>> positions = (ArrayList<ArrayList<Integer>>) result.get("pos");
                int positionRanking = getPositionRanking(positions);
                int titleScore = checkIfInTitle(Arrays.asList(query.split(" ")), url);
                score = tfidf*TFIDF_WT + positionRanking*POS_WT + titleScore*TITLE_WT;
                rankedUrls.put(url,score);
            }
            return sortByValue(rankedUrls);
    }

    private int checkIfInTitle(List<String> query, String url) {
        int titleScore=0;
        DBCollection titleData = db.getCollection("titleData");
        DBObject dboQuery = new BasicDBObject();
        dboQuery.put("url",url);
        DBObject dboProj = new BasicDBObject();
        dboProj.put("title",1);
        DBObject dbObject = titleData.findOne(dboQuery,dboProj);
        if(dbObject!=null){
            String title = String.valueOf(dbObject.get("title")).toLowerCase();
            if(title!=null){
                for(String que : query) {
                    if (title.contains(que)) {
                        titleScore+=1;
                    }
                }
            }
        }
        return titleScore;
    }

    private int getPositionRanking(ArrayList<ArrayList<Integer>> positions) {
        int sum = 0;
        if(positions.size()>2)
        {

        }
        else if (positions.size()==1)
        {
            return 0;
        }

        else
        {
            ArrayList<Integer> second;
            ArrayList<Integer> first;
            ArrayList<Integer> delta = new ArrayList<Integer>();
    		/*if( positions.get(0).size()<positions.get(1).size())
    		{
    			second = new ArrayList<Integer>(positions.get(1).subList(0, positions.get(0).size()));
    			first = positions.get(0);
    		}
    		else
    		{
    			second = new ArrayList<Integer>(positions.get(0).subList(0, positions.get(1).size()));
    			first = positions.get(1);
    		}*/
            for(int i = 0; i < Math.min(positions.get(0).size(),positions.get(1).size()); i++)
            {
                delta.add(Math.abs(positions.get(0).get(i)-positions.get(1).get(i)));
            }

            for(int i : delta)
                sum+=i;
            sum=sum/delta.size();
            if(Math.min(positions.get(0).get(0),positions.get(0).get(1))>300)
                System.out.println("Not in the heading");
            else
                System.out.println("In the heading");
            System.out.println("The sum of the delta's is " +sum);
        }


        return sum;
    }

    public static <String, Double extends Comparable<? super Double>> LinkedHashMap<String, Double>
    sortByValue( LinkedHashMap<String, Double> map )
    {
        LinkedList<Map.Entry<String, Double>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, (Comparator<? super Map.Entry<String, Double>>) new Comparator<Map.Entry<String, Double>>()
        {
            @Override
            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        });

        LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
