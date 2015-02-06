package edu.uci;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class JSONRetrieval
{
	public File[] getFileNamesAsArray()
	{
		File folder = new File("./data/JSONs/");
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}
	public void getFileName()
	{
		HashMap<String, Integer> subDomainMap = new HashMap<String, Integer>();	
		for (File file : getFileNamesAsArray()) 
		{
			if (file.isFile()) 
			{
				getURL(file.getName(),subDomainMap);
			}
		}
		for (String name: subDomainMap.keySet())
		{
            String key =name.toString();
            int value = subDomainMap.get(name);  
            System.out.println(key + " " + value);  
		} 
	}
	public void getURL(String fileName, HashMap<String, Integer> subDomainMap)
	{
		int count = 0;
		JSONParser parser = new JSONParser();
		try 
		{
			Object obj = parser.parse(new FileReader("./data/JSONs/"+fileName));
			JSONObject jsonObject = (JSONObject) obj;
			String url = (String) jsonObject.get("url");
			String subDomain = (String) jsonObject.get("subDomain");
			if(subDomainMap.containsKey(subDomain))
				subDomainMap.put(subDomain, subDomainMap.get(subDomain)+1);
			else
				subDomainMap.put(subDomain, 1);
			FileWriter fw = new FileWriter("URL.txt",true); 
		    fw.write(url+"\n");
		    fw.close();
			count++;
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
	}
	public static void main(String args[])
	{
		JSONRetrieval j = new JSONRetrieval();
		j.getFileName();
	}
}
