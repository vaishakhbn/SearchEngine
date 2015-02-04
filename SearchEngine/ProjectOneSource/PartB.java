package one;

import java.util.*;
import java.io.IOException;

public final class PartB
{
	public static void main(String[] args) throws IOException
	{
		List<Token> token  = Utilities.tokenizeFile(args[0]);
		LinkedHashMap<java.lang.String, java.lang.Integer> pairs = Utilities.computeWordFrequencies(token);
		Utilities.print(pairs);
	}
}


