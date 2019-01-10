package rbtree;

public class WebPage {

	//public boolean isBlack;
	public int originalIndex;
	public boolean isRed;
	public int index;
    public String link;
    public int pageRank;
    public int numKeywords;
    public int age;
    public int numLinked;
    public int money;
    public WebPage right;
    public WebPage left;
    public WebPage p;

    /*
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
        this.isRed = true;
    }


    // returns right Webpage
    WebPage getRight()
    {
        return right;
    }
    // returns left WebPage
    WebPage getLeft()
    {
        return left;
    }
    // returns parent webpage
    WebPage getParent()
    {
        return p;
    }
    // sets right webpage
    void setRight(WebPage right)
    {
        this.right = right;
    }
    // sets left webpage
    void setLeft(WebPage left) 
    {
        this.left = left;
    }
    // sets parent webpage
    void setParent(WebPage parent)
    {
        this.p = parent;
    }
    // sets rank of webpage
    public void setRank(int i)
    {
        this.pageRank = i;
    }
}