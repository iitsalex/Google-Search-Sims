package webcrawler;

/**
 * WebPage objects designed to be stored in an Array. 
 * 4 factors of pageRank come together to (max)100 natively
 * Can go past 100 through user intervention
 * @author alex
 *
 */
public class WebPage {
	
	public String link;
	public int pageRank;
	public int numKeywords;
	public int age;
	public int numLinked;
	public int money;
	
	/**
	 * Constructor with randomized pageRank + given link
	 * @param link given URL to assign to each WebPage object
	 */
	public WebPage(String link)
	{
		this.link = link;
		
		this.numKeywords = (int)(Math.random() * 25);
		this.age = (int)(Math.random() * 25);
		this.numLinked = (int)(Math.random() * 25);
		this.money = (int)(Math.random() * 25);
		
		this.pageRank = numKeywords + age + numLinked + money;
	}
	
}
