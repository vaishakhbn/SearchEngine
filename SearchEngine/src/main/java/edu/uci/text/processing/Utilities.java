package edu.uci.text.processing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public final class Utilities
{
	private static Scanner in;

	public static List<Token> tokenizeFile(String textFile) throws IOException
	{
	
			List<Token> input = new ArrayList<Token>();
			String inp;
			File inputFile = new File(textFile);
			in = new Scanner(inputFile);
			while(in.hasNextLine())
			{
				inp = in.nextLine();
				String alphaNumericOnly = inp.replaceAll("[^a-zA-Z]+"," ");
				StringTokenizer st = new StringTokenizer(alphaNumericOnly);
				while(st.hasMoreTokens())
				{
					String a = st.nextToken();
					if(!(a.equalsIgnoreCase(" ")) && a.length() >1 )
					{
						input.add(new Token(a.toLowerCase()));
					}
				}
			}
			return input;
		}
		
	
	public static LinkedHashMap<String, Integer> computeWordFrequencies(List<Token> token)
	{
		LinkedHashSet<String> uniqueTokens = new LinkedHashSet<String>();
		
		
		ArrayList<String> tokens = new ArrayList<String>();
		for (Token t  : token) 
		{
			uniqueTokens.add(t.getToken());
			tokens.add(t.getToken());
		}
		Iterator<String> iter = uniqueTokens.iterator();
		LinkedHashMap<String, Integer> t = new LinkedHashMap<String, Integer>();
		while (iter.hasNext()) 
		{
			String a = iter.next();
			int occurrences = Collections.frequency(tokens, a);
			t.put(a,occurrences);
		}
		return t;
	}
	public static List<String> convertTokenObjectToList(List<Token> token)
	{
		List<String> tokens = new ArrayList<String>();
		for (Token t  : token) 
		{
			tokens.add(t.getToken());
		}
		return tokens;
		
	}
	public static void print(LinkedHashMap<String, Integer> t)
	{
		t = sortByValue(t);
		Set<Entry<String, Integer>> set = t.entrySet();
		Iterator<Entry<String, Integer>> i1 = set.iterator();
	    while(i1.hasNext()) 
	    {
	       @SuppressWarnings("rawtypes")
		Entry me = (Entry)i1.next();
	       System.out.print(me.getKey() + "  -  ");
	       System.out.println(me.getValue());
	    }
	}


    @SuppressWarnings("hiding")
    public static <String, Integer extends Comparable<? super Integer>> LinkedHashMap<String, Integer>
    sortByValue( LinkedHashMap<String, Integer> map )
    {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            @Override
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );

        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }








	public static List<Token> convertListToToken(List<String> tokenStringList)
	{
		List<Token> tokens = new ArrayList<Token>();
		for (String s  : tokenStringList) 
		{
			tokens.add(new Token(s));
		}
		return tokens;
	}
	
	public static List<String> convertToTokenList(List<Token> tokenList)
	{
		List<String> tokens = new ArrayList<String>();
		for (Token t  : tokenList) 
		{
			tokens.add(t.getToken());
		}
		return tokens;
	}
	public static boolean isValidFileType(String inputFile) throws IOException
	{
		boolean isNotEmpty = true;
		 boolean isTxtFile = inputFile.toLowerCase().endsWith(".txt");
		 BufferedReader br = new BufferedReader(new FileReader(inputFile));     
		 if (br.readLine() == null) {
			 isNotEmpty = false;
		 }
		 return isNotEmpty&&isTxtFile;
	}
}



