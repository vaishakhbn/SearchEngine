package one;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PartC
{

	public static void main(String[] args) throws IOException
	{
		List<Token> token = Utilities.tokenizeFile(args[0]);
		List<String> twoGrams = createTwoGrams(Utilities.convertTokenObjectToList(token));
		LinkedHashMap<java.lang.String, java.lang.Integer> twoGramFrequencies = Utilities.computeWordFrequencies(Utilities.convertListToToken(twoGrams));
		Utilities.print(twoGramFrequencies);  
	}
	private static List<String> createTwoGrams(List<String> token)
	{
		List<String> twoGrams = new ArrayList<String>();
		for(int i = 0; i<token.size()-1;i++)
		{
			twoGrams.add(token.get(i)+" "+token.get(i+1));
		}
		return twoGrams;
	}
}
