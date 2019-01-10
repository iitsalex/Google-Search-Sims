package rbtree;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class RBTree {

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

		Scanner input = new Scanner(System.in); // open scanner
		System.out.println("What would you like to search? (QUICKSORT METHOD)"); // initial query request
		String query = input.nextLine();
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
				"What would you like to do? (Options include: Increase, Search (QS Method), BST (RB Binary Search Tree), or Quit)");
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
				// hashmap.putIfAbsent(searchTerm, 0);
				// hashmap.put(searchTerm, hashmap.get(searchTerm) + 1);
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
			} else if (answer.contains("BST") || answer.contains("Binary Search Tree") || answer.contains("bst")) {
				System.out.println("What would you like to Search? (RED-BLACK Method) "); // User has entered BST Mode
				String toSearch = input.nextLine(); // intended to be a loop for BST commands
				wpArray = WebCrawler.retrieveLinks(toSearch); // create wpArray by searching given query
				BST temp = new BST();
				temp.BSTSort(wpArray); // Create initial BST tree
				System.out.println();
				while (true) {
					counter = 0; // counter to manage indexing for user interface
					System.out.println(
							"What BST methods would you like to proceed with? (Search (For TotalScore), Insert, Delete, Sort, Quit)");
					String response = input.nextLine();
					if (response.contains("Delete") || response.contains("delete")) { // delete subdirectory
						System.out.println("Which would you like to delete?");
						int toDelete = input.nextInt();
						input.nextLine();
						temp.deleteIndex(temp, toDelete);
						counter = 0;
						temp.inOrderTreeWalk(temp.root); // reprint Tree for user
					} else if (response.contains("Sort") || response.contains("sort")) { // sort subdirectory
						temp.inOrderTreeWalk(temp.root); // reprint Tree
					} else if (response.contains("Search") || response.contains("search")) { // search subdirectory
						System.out.println("What TotalScore would you like to search? ");
						int numberResponse = input.nextInt();
						input.nextLine();
						WebPage result = temp.treeSearch(temp.root, numberResponse);
						boolean rescolor = temp.treeSearch(temp.root, numberResponse).isRed;
						String color;
						if (rescolor)
							color = "RED";
						else
							color = "BLACK";
						System.out.println(result.link + " Color: " + color); // print out the given link of a searched
																				// pagerank
					} else if (response.contains("Insert") || response.contains("insert")) { // insert subdirectory
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
						temp.RBInsert(temp, toInsert);
						temp.inOrderTreeWalk(temp.root);
					} else if (response.contains("quit") || response.contains("Quit")) // breaks out of BST mode
						break;
				}

			}

			System.out.println(
					"What would you like to do? (Options include: Increase, Search, BST (RED Black Tree Style), Quit");

		}
		System.out.println();
		System.out.println();
		input.close();

	}

	/**
	 * BST Tree Class used as private class For sake of consistency, differences
	 * between native BST code and my code
	 *
	 * @author alex
	 */
	private static class BST {
		private WebPage root; // initializing root node

		public void inOrderTreeWalk(WebPage x) {
			String color;
			if (x != null) {
				inOrderTreeWalk(x.getRight());
				// rather than print the values in order, print w/ a counter and using actual
				// pageranks/indices to create
				// a nicely laid out table

				if (x.isRed)
					color = "RED";
				else
					color = "BLACK";
				System.out.println("PageRank: " + (++counter) + ") " + x.link + " - TotalScore(" + x.pageRank + ") -"
						+ " Color: " + color + " - Original Index: " + x.originalIndex);
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
			if (T.root == null)
				T.root = z; // initialize root as the webpage if root is null
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
		 * Deletes a given WebPage from a BST T, legacy method
		 *
		 * @param T
		 *            BST to modify
		 * @param z
		 *            WebPage to be deleted
		 */
		/*
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
		*/

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
						RBDelete(T, node); // using original RBDelete
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
			for (WebPage page : A) // insert all webpages in A
				RBInsert(this, page); // ^
			inOrderTreeWalk(this.root); // print out results
		}

		/**
		 * Adjusts pointers in order to relocate branhces in order to satisfy redblack
		 * prop
		 * 
		 * @param T
		 *            Tree to work on
		 * @param x
		 *            webpage to rotate upon
		 */
		public void leftRotate(BST T, WebPage x) {
			WebPage y = x.right;
			x.right = y.left;

			if (y.left != null)
				y.left.p = x;
			y.p = x.p;
			if (x.p == null)
				T.root = y;
			else if (x == x.p.left)
				x.p.left = y;
			else
				x.p.right = y;
			y.left = x;
			x.p = y;
		}

		/**
		 * Adjusts pointers in order to relocate branches in order to satisfy redblack
		 * property
		 * 
		 * @param T
		 *            Tree to work on
		 * @param x
		 *            webpage to rotate upon
		 */

		public void rightRotate(BST T, WebPage x) {
			WebPage y = x.left;
			x.left = y.right;

			if (y.right != null)
				y.right.p = x;
			y.p = x.p;
			if (x.p == null)
				T.root = y;
			else if (x == x.p.left)
				x.p.left = y;
			else
				x.p.right = y;
			y.right = x;
			x.p = y;
		}

		/**
		 * Inserts new webpage into tree
		 * 
		 * @param T
		 *            Tree to work on
		 * @param z
		 *            Webpage to Insert
		 */
		public void RBInsert(BST T, WebPage z) {
			WebPage y = null; // initialize
			WebPage x = T.root; // start at root
			while (x != null) { // null check
				y = x;
				if (z.pageRank < x.pageRank) // basic tree property
					x = x.left;
				else
					x = x.right;
			}
			z.p = y;
			if (y == null)
				T.root = z;
			else if (z.pageRank < y.pageRank) // basic tree property
				y.left = z;
			else
				y.right = z;
			z.left = null;
			z.right = null;
			z.isRed = true; // initialize z as red node
			RBInsertFixup(T, z);
		}

		/**
		 * Fixes red black property of tree after insertion
		 * 
		 * @param T
		 *            Tree to work on
		 * @param z
		 *            Webpage to insert and fixup based on
		 */
		public void RBInsertFixup(BST T, WebPage z) {
			while (z != T.root && z.p.isRed) { // nullcheck on Root and checking red property
				if (z.p.p != null && z.p.p.right != null && z.p == z.p.p.left) { // additional null checks
					WebPage y = null;
					if (z.p.p != null && z.p.p.right != null) // null checks
						y = z.p.p.right;
					if (y != null && y.isRed) {
						z.p.isRed = false;
						y.isRed = false; // adjusting actual colors
						z.p.p.isRed = true;
						z = z.p.p;
					} else {
						if (z == z.p.right) {
							z = z.p;
							leftRotate(T, z); // readjust branches
						}
						z.p.isRed = false;
						if (z.p.p != null) {
							z.p.p.isRed = true;
							rightRotate(T, z.p.p); // readjust branches
						}
						z = z.p;
					}
					// same concept, but flipped directions below
				} else {
					WebPage y = null;
					if (z.p.p != null && z.p.p.left != null)
						y = z.p.p.left;
					if (y != null && y.isRed) {
						z.p.isRed = false;
						y.isRed = false;
						z.p.p.isRed = true;
						z = z.p.p;
					} else {
						if (z == z.p.left) {
							z = z.p;
							rightRotate(T, z);
						}
						z.p.isRed = false;
						if (z.p.p != null) {
							z.p.p.isRed = true;
							leftRotate(T, z.p.p);
						}
						z = z.p;
					}
				}
				T.root.isRed = false;
			}
		}

		/**
		 * Transplants one tree branch to another location, essentially uprooting
		 * 
		 * @param T
		 *            Tree to work on
		 * @param u
		 *            branch to uproot
		 * @param v
		 *            branch doing the uprooting
		 */
		public void RBTransplant(BST T, WebPage u, WebPage v) {
			if (u.p == null) { // check for root status
				T.root = v;
			} else if (u == u.p.left)
				u.p.left = v; // check for left side
			else
				u.p.right = v; // check for right side
			if (v != null)
				v.p = u.p; // additional null check in order to avoid nullpointer
		}

		/**
		 * Delete function to do base delete and use RBdelete fixup to fix any issues
		 * with post deleted tree
		 * 
		 * @param T
		 *            Tree to work on
		 * @param z
		 *            webpage to delete
		 */
		public void RBDelete(BST T, WebPage z) {
			WebPage y = z;
			boolean oldRed = y.isRed; // original color of y storing
			WebPage x = new WebPage(null); // initialize webpage to work from
			if (z.left == null && z.right != null) {
				x = z.right;
				RBTransplant(T, z, z.right); // uproot old right side
			} else if (z.right == null && z.left != null) {
				x = z.left;
				RBTransplant(T, z, z.left); // uproot old left side
			} else if (z.right != null) {
				y = min(z.right);
				oldRed = y.isRed;
				x = y.right;
				if (y.p == z)
					if (x != null)
						x.p = y;
					else {
						RBTransplant(T, y, y.right); // uproot old right side
						y.right = z.right;
						if (y.right != null)
							y.right.p = y;
					}
				RBTransplant(T, z, y); // uproot newly created branch
				y.left = z.left;
				y.left.p = y;
				y.isRed = z.isRed;
			} else if (z.right == null && z.left == null) {
				oldRed = true;
				RBTransplant(T, z, null); // readjust
			}
			if (!oldRed) {
				RBDeleteFixup(T, x); // fix any color inconsistencies within tree
			}
		}

		/**
		 * Fixes Red Black Properties of tree after deletion
		 * 
		 * @param T
		 *            Tree to work on
		 * @param x
		 *            WebPage to begin fix-up process
		 */
		public void RBDeleteFixup(BST T, WebPage x) {
			WebPage w;
			// null checks alongside non-root checks to avoid working on unnecessary nodes
			while (x != null && x != T.root && x.isRed && x.p.left != null && x.p.right != null) {
				if (x == x.p.left) {
					w = x.p.right;
					if (w != null && !w.isRed) {
						w.isRed = false;
						x.p.isRed = true;
						leftRotate(T, x.p); // readjust tree structure to accommodate new changes
						w = x.p.right;
					}
					if (w != null && w.right != null && w.left != null && w.left.isRed && w.right.isRed) {
						w.isRed = true;
						x = x.p;
					} else if (w != null && w.right != null && w.right.isRed) {
						w.left.isRed = false;
						w.isRed = true;
						rightRotate(T, w); // readjust tree structure to accommodate new changes
						w = x.p.right;
					} else if (w != null && w.isRed && w.right != null && !w.right.isRed) {
						w.isRed = x.p.isRed;
						x.p.isRed = false;
						w.right.isRed = false;
						leftRotate(T, x.p); // readjust tree structure to accommodate new changes
						x = T.root;
					}
				}
				// same concept, but flipped for other side
				else if (x.p != null) {
					w = x.p.left;
					if (w != null && !w.isRed) {
						w.isRed = false;
						x.p.isRed = true;
						rightRotate(T, x.p);
						w = x.p.left;
					}
					if (w != null && w.right != null && w.left != null && w.right.isRed && w.left.isRed) {
						w.isRed = true;
						x = x.p;
					} else if (w != null && w.left != null && w.left.isRed) {
						w.right.isRed = false;
						w.isRed = true;
						leftRotate(T, w);
						w = x.p.left;
					} else if (w != null && w.isRed && w.left != null && !w.left.isRed) {
						w.isRed = x.p.isRed;
						x.p.isRed = false;
						w.left.isRed = false;
						rightRotate(T, x.p);
						x = T.root;
					}
				}
				x.isRed = false;
			}
		}
	}
}
