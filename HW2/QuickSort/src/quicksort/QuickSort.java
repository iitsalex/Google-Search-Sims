package quicksort;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class QuickSort {

	private static int counter = 0; // Counter to print out array 1 -> n

	/**
	 * Quicksort algorithm which recursively calls through partition method
	 * 
	 * @param A
	 *            WebPage array in which pageRank is compared
	 * @param p
	 *            pivot initial pivot point
	 * @param r
	 *            endpoint to begin initial partitioning
	 */
	public static void quickSort(WebPage[] A, int p, int r) {
		if (p < r) {
			int q = partition(A, p, r); // q = new pivot location
			quickSort(A, p, q - 1); // recursively iterate through left side (q-1)
			quickSort(A, q + 1, r); // through right side
		}
	}

	/**
	 * Splits WebPage Array by swapping values comparitively and being called
	 * recursively in Quicksort method
	 * 
	 * @param A
	 *            WebPage array in which pageRank is compared
	 * @param p
	 *            pivot initial pivot point
	 * @param r
	 *            endpoint to begin initial partitioning
	 * @return
	 */
	public static int partition(WebPage[] A, int p, int r) {
		int x = A[r].pageRank;
		int i = p - 1;
		for (int j = p; j < r; j++) { // use j as a point of entry to maneuver data
			if (A[j].pageRank <= x) { // swap checks
				i++;
				WebPage temp = A[i];
				A[i] = A[j];
				A[j] = temp;
			}
		}
		WebPage temp = A[i + 1]; // swaps
		A[i + 1] = A[r];
		A[r] = temp;
		return i + 1;
	}

	/**
	 * Randomizer left over for larger input sizes.
	 * 
	 * @param A
	 *            WebPage array in which pageRank is compared
	 * @param p
	 *            pivot initial pivot point
	 * @param r
	 *            endpoint to begin initial partitioning
	 */
	public static void randomizedQuickSort(WebPage[] A, int p, int r) {
		if (p < r) {
			int q = randomizedPartition(A, p, r); // q = new pivot location
			randomizedQuickSort(A, p, q - 1); // recursively iterate through left side (q-1)
			randomizedQuickSort(A, q + 1, r); // through right side
		}
	}

	/**
	 * Left over to use randomizedQuickSort
	 * 
	 * @param A
	 *            WebPage array in which pageRank is compared
	 * @param p
	 *            pivot initial pivot point
	 * @param r
	 *            endpoint to begin initial partitioning
	 * @return
	 */
	public static int randomizedPartition(WebPage[] A, int p, int r) {
		int random = p + (int) (Math.random() * (r - p));
		WebPage temp = A[random];
		A[random] = A[r];
		A[r] = temp;
		return partition(A, p, r);
	}

	/**
	 * Main directory where user can play with values and edit things as they wish
	 */
	public static void main(String[] args) throws IOException {

		HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
		Scanner input = new Scanner(System.in); // open scanner
		System.out.println("What would you like to search?"); // initial query request
		String query = input.nextLine();
		hashmap.putIfAbsent(query, 0);
		hashmap.put(query, hashmap.get(query) + 1);
		WebPage wpArray[] = WebCrawler.retrieveLinks(query); // create array of webpages with randomized pageRank
		quickSort(wpArray, 0, wpArray.length - 1);

		System.out.println();

		int entryFixer = 1; // value to print for pretty user interface
		for (int i = wpArray.length - 1; i >= 0; i--) // give top 30 pages
		{
			System.out.println(
					"PageRank: " + entryFixer + ") " + wpArray[i].link + ": TotalScore(" + wpArray[i].pageRank + ")");
			entryFixer++; // increment for list
		}

		System.out.println(
				"What would you like to do? (Options include: Increase, Search, BST (Binary Search Tree), or Quit");
		while (true) {
			int number;
			String answer = input.nextLine();

			if (answer.contains("increase") || answer.contains("Increase")) {
				System.out.println("Which entry (1-30) would you like to increase? Or press Q to quit");
				if (input.hasNextInt()) // if actually an int, assign to number value
				{
					number = input.nextInt();
					entryFixer = 1; // reset to use for pretty list
					int programSideIndex = wpArray.length - number;
					System.out.println("Which factor would you like to increase? (Age, Money, Keywords, Links)");
					String loljk = input.next(); // which factor to increase the pageRank by
					System.out.println("How many points would you like to increase it's " + loljk + " by?");
					int newValue = wpArray[programSideIndex].pageRank + input.nextInt(); // adjusting value of key based
																							// on user input
					wpArray[programSideIndex].pageRank = newValue;

					System.out.println();
					System.out.println("Entry " + number + " adjusted to " + newValue); // reassuring user of adjusted
																						// value
					System.out.println();

					quickSort(wpArray, 0, wpArray.length - 1);

					for (int i = wpArray.length - 1; i >= 0; i--) // reprint pretty list for user to pick from
					{
						System.out.println("PageRank: " + entryFixer + ") " + wpArray[i].link + ": TotalScore("
								+ wpArray[i].pageRank + ")");
						entryFixer++;
					}
					System.out.println();
					System.out.println();
					input.nextLine();
				} else // if anything other than int, break;
					break;
			} else if (answer.contains("Search") || answer.contains("search")) {
				System.out.println("What would you like to search? ");
				String searchTerm = input.nextLine();
				hashmap.putIfAbsent(searchTerm, 0);
				hashmap.put(searchTerm, hashmap.get(searchTerm) + 1);
				wpArray = WebCrawler.retrieveLinks(searchTerm);
				quickSort(wpArray, 0, wpArray.length - 1);
				entryFixer = 1; // value to print for pretty user interface
				System.out.println();
				System.out.println();
				for (int i = wpArray.length - 1; i >= 0; i--) // give top 30 pages
				{
					System.out.println("PageRank: " + entryFixer + ") " + wpArray[i].link + ": TotalScore("
							+ wpArray[i].pageRank + ")");
					entryFixer++; // increment for list
				}
				System.out.println();
				System.out.println();
			} else if (answer.contains("quit") || answer.contains("Quit")) {
				break;
			} else if (answer.contains("BST") || answer.contains("Binary Search Tree")) {
				System.out.println("What would you like to Search? (BST Method) "); // User has entered BST Mode
				String toSearch = input.nextLine(); // intended to be a loop for BST commands
				hashmap.putIfAbsent(toSearch, 0); // maintain keywords by placing new value of 0 for any new keys
				hashmap.put(toSearch, hashmap.get(toSearch) + 1); // add 1 to value of key if it already exists
				wpArray = WebCrawler.retrieveLinks(toSearch); // create wpArray by searching given query
				BST temp = new BST();
				temp.BSTSort(wpArray); // Create initial BST tree
				System.out.println();
				while (true) {
					counter = 0;		// counter to manage indexing for user interface
					System.out.println(
							"What BST methods would you like to proceed with? (Search (For TotalScore), Insert, Delete, Sort, Quit)");
					String response = input.nextLine();
					if (response.contains("Delete") || response.contains("delete")) {		//delete subdirectory
						System.out.println("Which would you like to delete?");
						int toDelete = input.nextInt();
						input.nextLine();
						temp.deleteIndex(temp, toDelete);
						counter = 0;
						temp.inOrderTreeWalk(wpArray[0]);		// reprint Tree for user
					} else if (response.contains("Sort") || response.contains("sort")) {		//sort subdirectory
						temp.inOrderTreeWalk(wpArray[0]);		// reprint Tree
					} else if (response.contains("Search") || response.contains("search")) { 	// search subdirectory
						System.out.println("What TotalScore would you like to search? ");
						int numberResponse = input.nextInt();
						input.nextLine();
						System.out.println(temp.treeSearch(wpArray[0], numberResponse).link); 	// print out the given link of a searched pagerank
					} else if (response.contains("Insert") || response.contains("insert")) { 	// insert subdirectory
						System.out.println("What is the title of the WebPage? ");
						String title = input.nextLine();
						System.out.println("What is the URL of the WebPage? ");
						String URL = input.nextLine();
						String link = title + ": " + URL;
						System.out.println("What is the TotalScore? ");
						int totalScore = input.nextInt();
						input.nextLine();

						WebPage toInsert = new WebPage(link);
						toInsert.pageRank = totalScore;
						temp.insert(temp, toInsert);
					} else if (response.contains("quit") || response.contains("Quit"))			// breaks out of BST mode
						break;
				}

			}

			System.out.println(
					"What would you like to do? (Options include: Increase, Search, BST (Binary Search Tree), Quit to View Top 30 searches of your most frequent keyword");

		}
		System.out.println();
		System.out.println();

		System.out.println("Displaying Top 10 Keywords");
		String maxOverall = null;		//initial maxOverall string
		for (int i = 0; i < 10; i++) {
			String maxEntry = null;
			for (String entry : hashmap.keySet()) {
				if (hashmap.getOrDefault(entry, 0) > hashmap.getOrDefault(maxEntry, 0)) // check if entry has larger value than maxEntry
					maxEntry = entry;		// keep largest of the 10 keywords if new entry > maxEntry
			}
			if (maxEntry == null)
				break;
			if (maxOverall == null)
				maxOverall = maxEntry;		// keep largest of the 10 keywords
			System.out.println(maxEntry + " : " + hashmap.remove(maxEntry));
		}
		System.out.println();
		System.out.println();

		wpArray = WebCrawler.retrieveLinks(maxOverall);	//make 30 URL list based on the top keyword of the hashmap
		int n = wpArray.length;
		String[] stringArray = new String[n];
		int i = 0;
		for (WebPage a : wpArray) {
			stringArray[i++] = a.link;
		}
		System.out.println("Displaying 30 alphabetized URLs based on most frequent word:");
		System.out.println();
		BucketSort.bucketSort(stringArray);
		for (String c : stringArray)
			System.out.println(c);
		System.out.println();
		System.out.println();

		input.close();

	}

	/**
	 * BST Tree Class used as private class For sake of consistency, differences
	 * between native BST code and my code
	 * 
	 * @author alex
	 *
	 */
	private static class BST {
		private WebPage root; // initializing root node

		public void inOrderTreeWalk(WebPage x) {
			if (x != null) {
				inOrderTreeWalk(x.getRight());
				// rather than print the values in order, print w/ a counter and using actual
				// pageranks/indices to create
				// a nicely laid out table
				System.out.println("PageRank: " + (++counter) + ") " + x.link + " TotalScore(" + x.pageRank + ")");
				x.index = counter; // assigns x indexes as the counter in order to keep an indexing system for
									// display
				inOrderTreeWalk(x.getLeft());
			}
		}

		/**
		 * Searches for node which has key value of k, starting from webpage x, returns
		 * a WebPage with said value k
		 * 
		 * @param x
		 *            webpage to begin search from
		 * @param k
		 *            key value to search for
		 * @return WebPage with value K as pageRank value
		 */
		public WebPage treeSearch(WebPage x, int k) {
			if (x == null || k == x.pageRank)
				return x;
			if (k < x.pageRank)
				return treeSearch(x.getLeft(), k); // recursively iterate through left if smaller
			else
				return treeSearch(x.getRight(), k); // recursively iterate through right if larger
		}

		/**
		 * Legacy methods, not necessary. Same usage as treeSearch
		 */
		public WebPage iterativeTreeSearch(WebPage x, int k) {
			while (x != null && k != x.pageRank)
				if (k < x.pageRank)
					x = x.getLeft();
				else
					x = x.getRight();
			return x;
		}

		/**
		 * Legacy method Worth keeping for sake of finding max
		 */
		public WebPage max(WebPage x) {
			while (x.getRight() != null)
				x = x.getRight();
			return x;
		}

		/**
		 * Gives minimum value pageRank/WebPage
		 * 
		 * @param x
		 *            Webpage to begin from
		 * @return the minimum WebPage (in this case, minimum pagerank)
		 */
		public WebPage min(WebPage x) {
			while (x.getLeft() != null)
				x = x.getLeft();
			return x;
		}

		/**
		 * Insert method used to insert a WebPage z into the given BST T
		 * 
		 * @param T
		 *            given BST
		 * @param z
		 *            WebPage to insert
		 */
		public void insert(BST T, WebPage z) {
			if (root == null)
				root = z; // initialize root as the webpage if root is null
			WebPage y = null;
			WebPage x = T.root;
			while (x != null) { // while x isn't null, use pagerank to compare and traverse tree left or right
				y = x;
				if (z.pageRank < x.pageRank)
					x = x.getLeft();
				else
					x = x.getRight();
			}
			z.setParent(y);
			if (y == null)
				T.root = z;
			else if (z.pageRank < y.pageRank) // same concept, however while y is null
				y.setLeft(z);
			else
				y.setRight(z);
		}

		/**
		 * Transplant will essentially uproot a node and replace it with the new node (
		 * in this case, Webpage)
		 * 
		 * @param T
		 *            BST tree to modify
		 * @param u
		 *            to be uprooted
		 * @param v
		 *            the uproot-ee
		 */
		public void transplant(BST T, WebPage u, WebPage v) {
			if (u.getParent() == null)
				T.root = v;
			else if (u == u.getParent().getLeft())
				u.getParent().setLeft(v); // uses setting methods to essentially sever ends
			else // same throughout rest of code
				u.getParent().setRight(v);
			if (v != null)
				v.setParent(u.getParent());
		}

		/**
		 * Deletes a given WebPage from a BST T
		 * 
		 * @param T
		 *            BST to modify
		 * @param z
		 *            WebPage to be deleted
		 */
		public void delete(BST T, WebPage z) {
			if (z.getLeft() == null)
				transplant(T, z, z.getRight()); // uses transplant to sever ends and reattach correctly on right side
			else if (z.getRight() == null)
				transplant(T, z, z.getLeft()); // uses transplant to sever ends and reattach correctly on left side
			else {
				WebPage y = min(z.getRight()); // otherwise transplant y and y's right, then transplant z and y
				if (y.getParent() != z) {
					transplant(T, y, y.getRight());
					y.setRight(z.getRight());
					y.getRight().setParent(y);
				}
				transplant(T, z, y);
				y.setLeft(z.getLeft());
				y.getLeft().setParent(y);
			}
		}

		/**
		 * Will delete using the original delete method, however keeps the indexing
		 * using a stack
		 * 
		 * @param T
		 *            BST to modify
		 * @param i
		 *            integer index of what is to be deleted
		 */
		public void deleteIndex(BST T, int i) {
			Stack<WebPage> stack = new Stack<WebPage>();
			WebPage node;
			stack.push(T.root);
			while (!stack.isEmpty()) {
				node = stack.pop();
				if (node != null) {
					if (node.index == i) {
						delete(T, node); // using original delete
						break;
					}
					stack.push(node.getLeft()); // maintaining original indexing
					stack.push(node.getRight()); // maintaining original indexing
				}
			}
		}

		/**
		 * Pushes sorted array based on inOrderTreeWalk using a new BST and inserted
		 * Pages
		 * 
		 * @param A
		 *            base webpage array to work with and insert
		 */
		public void BSTSort(WebPage A[]) {
			BST T = new BST();
			for (WebPage page : A) // insert all webpages in A
				insert(T, page); // ^
			inOrderTreeWalk(T.root); // print out results
		}
	}
}
