package webcrawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Crawler which parses through top X amount of google searches and returns list of WebPages
 * @author alex
 *
 */
public class CrawlerPt2{

    public static final String USER_AGENT = "Chrome/45.0.2454.101"; // Agent to not get kicked from google search
    public static int querySize = 30; // static query size for demonstration purposes
    public static WebPage wpArray[] = new WebPage[querySize]; // 30 element array of webpages
    public static int i = 0; // counter for array creation during enhanced for loop
    
    /**
     * Retrieves links from a given query with given number of links requested from google.
     * @param query phrase to search
     * @return WebPage[] array of webPages with randomized pageRank factors
     * @throws IOException if query invalid
     */
    public static WebPage[] retrieveLinks(String query) throws IOException{
    	
		
        if (query.isEmpty())
        	throw new IllegalArgumentException("invalid query");
        query = query.replaceAll("\\s", "+");			// fix strings with spaces to prepare for search
        
        //retrieves full page of links
    	final Document doc = Jsoup.connect("https://google.com/search?q=" + query + "&num=" + querySize).userAgent(USER_AGENT).get();
        

        //Traverse the results
        for (Element result : doc.select("h3.r a")){
        	
            final String title = result.text();
            final String url = result.attr("href");
            
            //Fix url's to display more user friendly
            wpArray[i] = new WebPage((title + ": " + url.substring(7,url.indexOf("&"))));
            i++;
        }
        
        return wpArray;
    }
}