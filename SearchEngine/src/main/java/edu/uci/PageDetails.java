package edu.uci;

import java.util.ArrayList;

/**
 * Created by swanand on 2/1/2015.
 */
public class PageDetails {
    private final String url;
    private String subDomain;
    private int textSize;
    private String text;
    private String title;
    private ArrayList<String> tokenizedURL;
	public ArrayList<String> getTokenizedURL() {
		return tokenizedURL;
	}
	public void setTokenizedURL(ArrayList<String> tokenisedURL) {
		this.tokenizedURL = tokenisedURL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public PageDetails(String url) {
        this.url = url;
    }
    public String getURL()
    {
    	return this.url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }
}
