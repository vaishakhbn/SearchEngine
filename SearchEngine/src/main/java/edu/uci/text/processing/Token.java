package edu.uci.text.processing;

@SuppressWarnings("serial")
public class Token implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token1 = (Token) o;

        if (!token.equals(token1.token)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }

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
