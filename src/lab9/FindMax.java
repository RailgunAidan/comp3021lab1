package lab9;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * COMP 3021
 * 
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29] 
another thread the max among the cells [30,59] 
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 * 
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	Thread t1, t2, t3;
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	public static void main(String[] args) {
		new FindMax().printMax();
	}

	public void printMax() {
		// this is a single threaded version
		ArrayList<Integer> maxValues = new ArrayList<Integer>();
		t1 = new Thread(()->{
			int max1 = findMax(0, 29);
			//System.out.println("the max1 value is " + max1);
			try {
				t2.join();
				t3.join();
				maxValues.add(max1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//maxValues.add(max1);						
		});
		t2 = new Thread(()->{
			int max2 = findMax(30, 59);
			//System.out.println("the max2 value is " + max2);
			maxValues.add(max2);		
		});
		t3 = new Thread(()->{
			int max3 = findMax(60, 89);
			//System.out.println("the max3 value is " + max3);
			maxValues.add(max3);
		});
		t1.start();
		t2.start();
		t3.start();		
		System.out.println(maxValues);
		int max = Collections.max(maxValues);
		System.out.println("the max value is " + max);
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
}
