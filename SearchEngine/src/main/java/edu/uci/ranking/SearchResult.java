package edu.uci.ranking;

/**
 * Created by swanand on 3/12/2015.
 */
public class SearchResult {

    private final String url;
    private  String title;
    private  String snippet;

    public SearchResult(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTitle() {

        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getSnippet() {
        return snippet;
    }

}
