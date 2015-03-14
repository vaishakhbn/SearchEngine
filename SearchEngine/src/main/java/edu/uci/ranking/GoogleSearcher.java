package edu.uci.ranking;

/**
 * Created by swanand on 3/13/2015.
 */
import java.io.File;
import java.io.IOException;
        import java.io.UnsupportedEncodingException;
        import java.net.URLDecoder;
        import java.net.URLEncoder;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.regex.Pattern;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;
/*
 * Retrieves search results (URLs) from Google
 */
public class GoogleSearcher {

    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=[QUERY]&hl=en&start=[START]&btnG=Google+Search&gbv=1";
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz|ico|pfm|c|h|o|py|cc|txt|jnlp|ppt|pptx))$");

    public List<String> getSearchResults(String query, int count) {

        // First check if results cached locally (prevent requesting Google searches too much)
        // Get the search URL
        String encodedQuery = getQueryString(query);
        String searchUrl = GOOGLE_SEARCH_URL.replace("[QUERY]", encodedQuery);

        // Do search until we have enough results
        ArrayList<String> urlResults = new ArrayList<String>();
        try {
            int start = 0;
            while (urlResults.size() < count) {
                // Search the current page
                String currentPageSearchUrl = searchUrl.replace("[START]", start + "");

                // Download the page using jsoup
                // (use Firefox's user agent string so google doesn't block request)
                Document pageDoc = Jsoup.connect(currentPageSearchUrl).userAgent("Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1").get();

                // Get the URLs of the search results
                Elements searchResults = pageDoc.select("li[class=g] a");
                for (Element result : searchResults) {
                    if (result.text().equals("Cached") || result.text().equals("Similar")) // don't include cached or related links
                        continue;

                    String href = result.attr("href");
                    String extractedUrl = extractUrl(href);

                    // Filter URLs with the same types used for crawling
                    if (FILTERS.matcher(extractedUrl).matches())
                        continue;

                    // Filter HTTPS URLs since crawler did not visit those
                    if (extractedUrl.contains("https"))
                        continue;

                    // All good, keep URL for results
                    urlResults.add(extractedUrl);
                    if (urlResults.size() >= count)
                        break;
                }

                if (urlResults.size() < count) {
                    start += 10;
                    try {
                        Thread.sleep(1000); // wait a second to be polite to google
                    }
                    catch (InterruptedException  e) {
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Cache results for future calls
//        addResultsToCache(query, urlResults.toArray(new String[urlResults.size()]));

        return urlResults;
    }

    private String extractUrl(String googleUrl) {
        // Google search result links are not the direct links, have to get it from the "q" parameter
        int start = googleUrl.indexOf("q=");
        if (start > -1) {
            start += "q=".length();

            int end = googleUrl.indexOf("&", start);
            if (end > start) {
                try {
                    return URLDecoder.decode(googleUrl.substring(start, end), "UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    private String getQueryString(String query) {
        try {
            // Limit search to ics.uci.edu
            return URLEncoder.encode("site:ics.uci.edu " + query, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private static final String googleQueryCacheFolder = "googleQueryCache";

    private void addResultsToCache(String query, String[] results) {
        // Save the query results to a file
        String cacheFilename = getCacheFilename(query);
        File cacheFile = new File(cacheFilename);

        try {
            new File(googleQueryCacheFolder).mkdir();
            FileHelper.writeFile(cacheFile, results);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getResultsFromCache(String query) {
        String cacheFilename = getCacheFilename(query);
        File cacheFile = new File(cacheFilename);

        try {
            if (cacheFile.exists()) {
                String results = FileHelper.readFile(cacheFile, 0);
                return results.split(FileHelper.lineSeparator);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getCacheFilename(String query) {
        return googleQueryCacheFolder + "\\" + Math.abs(query.hashCode()) + ".txt";
    }
}


