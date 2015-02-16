package edu.uci;

import com.mongodb.Mongo;

import java.net.UnknownHostException;

public class MongoConnector {
    private static Mongo mongo;
    private MongoConnector() throws UnknownHostException {
        mongo = new Mongo();
    }
    public static Mongo getInstance() throws UnknownHostException {
        if(mongo == null){
            return new Mongo();
        }
        return mongo;
    }
}
