package one;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PartA
{
	public static void print(List<Token> token)
	{
		if(!token.isEmpty())
			System.out.println("List of Tokens : \n"+Arrays.asList(token));
		System.out.println("Total Number of Tokens : "+token.size());
	}
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		if(Utilities.isValidFileType(args[0]))
		{
			List<Token> token = Utilities.tokenizeFile(args[0]);
			print(token);
		}
		else
		{
			System.out.println("Either empty file or filetype not recognised!");
		}
	}

}
