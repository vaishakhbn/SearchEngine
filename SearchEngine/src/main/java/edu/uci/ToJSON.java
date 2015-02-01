package edu.uci;
import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
}
