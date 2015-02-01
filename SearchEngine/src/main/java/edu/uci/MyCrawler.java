package edu.uci;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
    private final static String File_Store_Path = "C:/data/parse/";
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            List<WebURL> visited = new ArrayList<WebURL>();
            if(visited.contains(url)){
                return false;
            }
            visited.add(url);
            String href = url.getURL().toLowerCase();

            return !FILTERS.matcher(href).matches() && href.contains("ics.uci.edu") &&!(href.contains("calendar")) &&
                    !(href.contains("archive.ics.uci.edu")) && !(href.contains("drzaius.ics.uci.edu"))&& !(href.contains("flamingo.ics.uci.edu")) &&
                    !(href.contains("fano.ics.uci.edu")) &&  !(href.contains("ironwood.ics.uci.edu")) &&
                    !(href.contains("duttgroup.ics.uci.edu"));
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     * Extracts info from the retrieved web page.
     */
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
            String subDomain = page.getWebURL().getSubDomain();
            System.out.println("URL: " + url);

            if (isHTMLParseData(page)) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    try {
                    File f = File.createTempFile("parse", "data.txt", new File(File_Store_Path));
						PrintWriter out = new PrintWriter(f);
						out.print(text);
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                PageDetails pageDetails = new PageDetails(url);
                pageDetails.setText(text);
                pageDetails.setTextSize(text.length());
                pageDetails.setSubDomain(subDomain);
                ToJSON.getInstance().convert(pageDetails);
                System.out.println("Text length: " + text.length());
                    System.out.println("Html length: " + html.length());
//                    for(WebURL link  :links)
//                        System.out.println(link);
                    System.out.println("Number of outgoing links: " + links.size());
            }
    }

    private boolean isHTMLParseData(Page page){
        return page.getParseData() instanceof HtmlParseData;
    }
}