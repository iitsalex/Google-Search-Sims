package webcrawler;
import static java.lang.System.exit;

import java.io.IOException;
import java.util.Scanner;


/**
 * HeapSort algorithm adjusted from Thomas H. Cormen textbook.
 * Intended to sort through given WebPages using assigned pageRanks
 * Pageranks subject to change through user intervention
 * @author alex
 *
 */
public class HeapSort {
	
	public int heapsize; // initialize heapsize
	
	/**
	 * Max Heapify splits arrays into two in order to
	 * recursively create heaps which satisfy the max heap property
	 * @param A array of WebPages with given pageRanks
	 * @param i initial start point for maxHeapify
	 */
	public void maxHeapify(WebPage A[], int i)
	{
		int largest = i; 	// initialize largest
		int left = 2*i;		// establish left as 2i
		int right = 2*i+1;	// establish right as 2i + 1 to create right and left sides
		
		if (left <= heapsize && A[left].pageRank > A[i].pageRank) 
			//check location of left & if pageRank of left is greater than pageRank of the startpoint
			largest = left;	 //left has been established as largest
		else
			largest = i;	 //re-establish as i
		if (right <= heapsize && A[right].pageRank > A[largest].pageRank)
			//check location of right & if pageRank of right is greater than pageRank of the largest
			largest = right; // right has been established as largest
		if (largest != i)
		{
			WebPage temp = A[i];	//swap values
			A[i] = A[largest];		//
			A[largest] = temp;		//	
			maxHeapify(A,largest);	// recursive call to maxHeapify the rest of the array.
		}
	}
	
	/**
	 * Builds a maxHeap in correct fashion. 
	 * Parent node > child nodes for each node cluster
	 * @param A array of webpages to pass through and build the heap from, using pageRank
	 */
	public void buildMaxHeap(WebPage A[])
	{
		heapsize = A.length-1;	//establish size to avoid nullPointerExceptions
		for (int i = (int)Math.floor((A.length-1)/2);i>=0;i--)	//establish point of entry & iterate through halves
		{
			maxHeapify(A,i);		// recursive calls to maxHeapify @ i
		}
	}
	/**
	 * Builds an array with ascending value, thus being sorted.
	 * @param A array of webpages to pass through for heapSorting by pageRank
	 */
	public void heapSort(WebPage A[])
	{
		buildMaxHeap(A);					
		for (int i = A.length-1; i>=1;i--)	// avoid nullPointerExceptions
		{
			WebPage temp = A[i]; 	//swap values
			A[i] = A[0];			//swap
			A[0] = temp;			//swap
			heapsize--;				//decrease heapsize for sake of continuity
			maxHeapify(A,0);
		}
	}
	
	/**
	 * Inserts new value into array of WebPages and re-sorts based on new order
	 * @param A array of webpages to sort based on pageRank
	 * @param key new value to insert into array
	 */
	public void maxHeapInsert(WebPage A[], int key)
	{
		heapsize++;	//increase size of heap to accomodate for new value in array
		A[heapsize].pageRank = Integer.MIN_VALUE;	//sets to negative infinity
		heapIncreaseKey(A,heapsize,key);	//Increases key of "non-existent" element to create larger heap
	}
	
	/**
	 * Unused method for sake of testing new method of building max heap
	 * @param A array of webpage built using pagerank
	 */
	/*public void buildMaxHeap2(WebPage A[])
	{
		heapsize = A.length-1;
		for (int i = 1; i>=A.length;i++)
			maxHeapInsert(A,A[i].pageRank); //uses maxHeapInsert instead of maxHeapify
	}*/
	
	/**
	 * Removes and returns the maximum value in a given heap
	 * @param A array of webpages to extract page with largest pageRank
	 * @return WebPage returns WebPage with largest pageRank
	 */
	public WebPage heapExtractMax(WebPage A[])
	{
		if (heapsize < 1)		//if heap is less than one, can't extract non-existent value
		{
			System.err.println("heap underflow"); // TEMP ERROR MESSAGE //
			exit(-1);
		}
		
		WebPage largest = A[0];		//swap
		A[0] = A[heapsize-1];		//swap
		heapsize--;					//decrease size to accomodate smaller array
		maxHeapify(A,0);			//maxHeapify to accomodate for lost element
		return largest;				//return largest value
	}
	/**
	 * Increase value of a given webpage in Array
	 * @param A array of webpages to increase a pageRank value of
	 * @param i location of webpages to increase pageRank value
	 * @param key new number of pageRank to increase to
	 */
	public void heapIncreaseKey(WebPage A[], int i, int key)
	{
		if (key < A[i].pageRank)
		{
			System.err.println("new key is smaller than current key");
			exit(-1);
		}
		A[i].pageRank = key;
		while (i>1 && A[(int)Math.floor(i/2)].pageRank < A[i].pageRank)
		{
			WebPage temp = A[i];
			A[i] = A[(int)Math.floor(i/2)];
			A[(int)Math.floor(i/2)] = temp;
			i = (int)Math.floor(i/2);
		}
	}
	/**
	 * Returns maximum value of heap while keeping unmodified.
	 * @param A array of webpages to take maximum value
	 * @return WebPage returns webpage with largest value
	 */
	public WebPage heapMaximum(WebPage A[])
	{
		return A[heapsize];
	}
	
	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);					//open scanner
		System.out.println("What would you like to search?");	//initial query request
		String query = input.nextLine();						
		WebPage wpArray[] = CrawlerPt2.retrieveLinks(query);	//create array of webpages with randomized pageRank

		HeapSort hS = new HeapSort();							//create heapSort object
		hS.heapSort(wpArray);									//sort array
		hS.heapsize = wpArray.length-1;							//establish heapsize to avoid nullpointer
		/*
		// Print heap sorted Array
		System.out.println("Heap Sorted Array: ");
		for (int i = 0; i < hS.heapsize; i++)
		{
			System.out.print(wpArray[i].pageRank + ", ");
		}
		System.out.println(wpArray[wpArray.length-1].pageRank);
		System.out.println();
		
		hS.buildMaxHeap(wpArray);					//build proper max heap
		
		//Print a built max heap
		System.out.println("Built Max Heap Array: ");
		for (int i = 0; i < hS.heapsize; i++)
		{
			System.out.print(wpArray[i].pageRank + ", ");
		}
		System.out.println(wpArray[wpArray.length-1].pageRank);
		System.out.println();
		

		// Extract Max + tell user what was extracted while printing the new Heap
		System.out.println("Max Heap After Extract Max: Extracted "+ hS.heapExtractMax(wpArray).pageRank);
		for (int i = 0; i < hS.heapsize; i++)
		{
			System.out.print(wpArray[i].pageRank + ", ");
		}
		System.out.println(wpArray[wpArray.length-1].pageRank);
		
		System.out.println();

		hS.maxHeapInsert(wpArray, 9999);				//insert 9999
//		hS.buildMaxHeap2(A); for testing purposes
		hS.buildMaxHeap(wpArray);						//rebuild
		
		//Print built heap and explain what was inserted
		System.out.println("Max Heap After Insert: 9999 Inserted");
		for (int i = 0; i < hS.heapsize; i++)
		{
			System.out.print(wpArray[i].pageRank + ", ");
		}
		System.out.println(wpArray[hS.heapsize].pageRank);
		
		System.out.println();

		
		hS.heapIncreaseKey(wpArray, 4, 1012301923);			//increase key of 5th (4th array element)
		hS.buildMaxHeap(wpArray);							//rebuild heap
		
		// Print new heap and explain what element was increased
		System.out.println("Max Heap After Increase Key: 5th element increased");
		for (int i = 0; i < hS.heapsize; i++)
		{
			System.out.print(wpArray[i].pageRank + ", ");
		}
		System.out.println(wpArray[wpArray.length-1].pageRank);

		*/
		System.out.println();
		
		int entryFixer = 1;			//value to print for pretty user interface
		for (int i = wpArray.length-1;i>=wpArray.length-10;i--)	//give top 10 pages
		{
			System.out.println(entryFixer + ": " + wpArray[i].link + ": PageRank(" + wpArray[i].pageRank + ")");
			entryFixer++;	//increment for list
		}
		
		while (true)
		{
			int number;
			System.out.println("Which entry (1-10) would you like to increase? Or press Q to quit");
			if (input.hasNextInt())				// if actually an int, assign to number value
				number = input.nextInt();
			else								// if anything other than int, break;
				break;
			entryFixer=1;						// reset to use for pretty list
			int pointOfEntry = 30 - number;		// accomodate for offset of heap for user interface
			System.out.println("Which factor would you like to increase? (Age, Money, Keywords, Links)");
			String loljk = input.next();		// which factor to increase the pageRank by
			System.out.println("How many points would you like to increase it's " + loljk + " by?");
			int newValue = wpArray[pointOfEntry].pageRank + input.nextInt();	// adjusting value of key based on user input
			hS.heapIncreaseKey(wpArray, pointOfEntry, newValue);				// increase key
			hS.heapSort(wpArray);												// re-sort
			hS.heapsize = wpArray.length - 1;									// reinitialize heapsize
			
			System.out.println();
			System.out.println("Entry " + number + " adjusted to " + newValue);	// reassuring user of adjusted value
			System.out.println();
			
			for (int i = wpArray.length-1;i>=wpArray.length-10;i--)	//reprint pretty list for user to pick from
			{
				System.out.println(entryFixer + ": " + wpArray[i].link + ": PageRank(" + wpArray[i].pageRank + ")");
				entryFixer++;
			}
			System.out.println();
			System.out.println();
		}
		input.close();
		
	}
}
