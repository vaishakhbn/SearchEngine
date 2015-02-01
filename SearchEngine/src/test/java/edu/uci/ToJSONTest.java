package edu.uci;

import org.junit.Test;

import junit.framework.TestCase;

public class ToJSONTest extends TestCase
{
	@Test
	public void testConvertToJSON()
	{
		ToJSON toJSonTest = ToJSON.getInstance();
		toJSonTest.convert((new PageDetails("http://www.ics.uci.edu/vaishakh")));
	}

}
