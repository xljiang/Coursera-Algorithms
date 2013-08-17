package assign6;
/* Median-Maintenance problem. Coursera Algo1, assignment 6, Question 2
 * The goal of this problem is to implement the "Median Maintenance" algorithm 
 * (covered in the Week 5 lecture on heap applications). The text file contains a list of 
 * the integers from 1 to 10000 in unsorted order; 
 * you should treat this as a stream of numbers, arriving one by one. 
 * Letting xi denote the ith number of the file, the kth median mk is defined as the median 
 * of the numbers x1,…,xk. (So, if k is odd, then mk is ((k+1)/2)th smallest number among 
 * x1,…,xk; if k is even, then mk is the (k/2)th smallest number among x1,…,xk.)
 */

import java.io.*;

public class MedianMaintenance {
	
	public static void main(String[] args){
		int median = 0; // median for each streaming input
		int sumMedian = 0; // sum of the median
		try{
			BufferedReader rd = new BufferedReader(new FileReader(FILENAME));
			while(true){
				String line = rd.readLine();
				if(line == null) break;
				median = findMedian(Integer.parseInt(line));
				sumMedian += median;
			}	
		}
		catch(IOException ex){
			System.out.println("Cannot find the file \"" + FILENAME + "\"");
			System.exit(0);
		}
		System.out.println("the sum is: " + sumMedian);
	}
	
	/** maintain 2 heaps of similar length (difference <= 1): 
	 * left side: max heap -- for smaller numbers; right side: min heap -- for larger numbers; 
	 * thus median = maxHeap's max.
	 */
	private static int findMedian(int input){
		if(input < minHeap.getMin()) {
			maxHeap.insert(input);
		} else{
			minHeap.insert(input);
		}
		if(maxHeap.getSize() - minHeap.getSize() > 1){
			minHeap.insert(maxHeap.extractMax());
		}
		if(minHeap.getSize() > maxHeap.getSize()){
			maxHeap.insert(minHeap.extractMin());
		}
		return maxHeap.getMax();
	}

	/* consts */
	private static final String FILENAME = "file/Median.txt";
//	private static final String FILENAME = "file/Median_test.txt";
	
	/* ivars */
	private static MaxHeap maxHeap = new MaxHeap(); 
	private static MinHeap minHeap = new MinHeap();
	
}
