package edu.uci;
import java.io.File;
import java.io.IOException;

import com.mongodb.util.JSON;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.mongodb.*;
/**
 * Created by swanand on 2/1/2015.
 */
public class ToJSON {
    private static ToJSON toJSON = new ToJSON();
    private ToJSON(){
    }
    public static ToJSON getInstance(){
        if(toJSON == null){
            return new ToJSON();
        }
        return toJSON;
    }
    public void convert(PageDetails pageDetails) {
    	ObjectMapper mapper = new ObjectMapper();
    	try 
    	{
    		String fileName = pageDetails.getURL().replace("http://", "").replaceAll("/", ".").concat(".json");
            Mongo mongo = MongoConnector.getInstance();
            insertPage(mongo,pageDetails,mapper);
            mapper.writeValue(new File("./data/JSONs/"+fileName), pageDetails);
    	}
    	catch (JsonGenerationException e) 
    	{
    		e.printStackTrace();
    	}
    	catch (JsonMappingException e) 
    	{
    		e.printStackTrace();
    	}
    	catch (IOException e) 
    	{
    		e.printStackTrace();
    	}
    }

    private void insertPage(Mongo mongo, PageDetails pageDetails, ObjectMapper mapper) throws IOException {
        DBCollection pages = mongo.getDB("webpages").getCollection("pages");
        DBObject page = (DBObject) JSON.parse(mapper.writeValueAsString(pageDetails));
        pages.insert(page);
    }
}
