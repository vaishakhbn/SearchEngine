package edu.uci;

/**
 * Created by swanand on 2/1/2015.
 */
public class PageDetails {
    private final String url;
    private String text;
    private int textSize;
    private String subDomain;
    public PageDetails(String url) {
        this.url = url;
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
