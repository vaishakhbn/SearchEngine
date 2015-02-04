package one;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PartD
{

	public static void main(String[] args) throws IOException
	{
		List<String> palindromeTokens = new ArrayList<String>();
		List<Token> token  = Utilities.tokenizeFile(args[0]);
		List<String> tokens = Utilities.convertTokenObjectToList(token);

		addSinglePalindromeTokens(palindromeTokens, tokens);
		createPalindromeTokens(palindromeTokens, tokens);
	
		
		LinkedHashMap<java.lang.String, java.lang.Integer> pals =
				Utilities.computeWordFrequencies(Utilities.convertListToToken(palindromeTokens));
		Utilities.print(pals);
	}
	private static void createPalindromeTokens(List<String> palindromeTokens,
			List<String> tokens) {
		for(int i = 0; i < tokens.size(); i++)
		{
			for(int j = i+1 ; j < tokens.size(); j++)
			{
				List<String> jthToken = tokens.subList(i+1, j+1);
				String listString = new String();
				String listStringWithSpaces = new String();
				for (String s : jthToken)
				{
				    listString += s;
				    listStringWithSpaces += s+" ";
				}
				if(isPalindrome(tokens.get(i).concat(listString)))
				{
					palindromeTokens.add(tokens.get(i).concat(" "+listStringWithSpaces));
				}
			}
		}
	}
	private static void addSinglePalindromeTokens(
			List<String> palindromeTokens, List<String> tokens) {
		for (String element : tokens) 
		{
			if(isPalindrome(element))
				palindromeTokens.add(element);
		}
	}
	public static boolean isPalindrome(String input)
	{
		if(input.length()==1)
			return false;
		 return input.equals(new StringBuilder(input).reverse().toString());
	}

static String readFile(String path, Charset encoding) 
		  throws IOException 
		{
		  byte[] encoded = Files.readAllBytes(Paths.get(path));
		  return new String(encoded, encoding);
		}
}