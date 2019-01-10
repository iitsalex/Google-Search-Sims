package quicksort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSort {

	public static void bucketSort(String[] arr) {
        int n = 36; // Number of buckets (+10 because of 0-9 numbers)

        @SuppressWarnings("unchecked")		// remove warnings
        List<String>[] buckets = new List[n];	// create array of list of strings
        for (int i = 0; i < n; i++)
            buckets[i] = new ArrayList<String>();	// for each i in buckets, create a new arraylist of strings
        
        for (String s : arr) {
            String[] temp = s.split("//", 2);
            temp = temp[temp.length-1].split("www.", 2);	// create temp string based on splitting of // from https:// and splitting at www.
            buckets[Character.getNumericValue(temp[temp.length-1].charAt(0))].add(s); //once split correctly, use domain names first character to add to buckets
        }
        
        for (List<String> bucket : buckets)
            Collections.sort(bucket);				// sort buckets with sorting algorithm of choice, in this case insertion is fine.

        int count = 0;
        for (List<String> b : buckets)
            for (Object o : b)
                arr[count++] = (String) o;
    }
}