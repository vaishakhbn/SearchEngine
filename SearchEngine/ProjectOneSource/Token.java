package one;

@SuppressWarnings("serial")
public class Token implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String token;
	
	public Token(String lowerCase)
	{
		this.token = lowerCase;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
	@Override
	public String toString() 
	{
	    return  this.getToken(); 
	}
}
