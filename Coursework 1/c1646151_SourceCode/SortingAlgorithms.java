//c1646151
import java.util.*;
import java.io.*;

public class SortingAlgorithms{
	public static int[] arrays1;
	public static int[] arrays2;
	public static int item;
	public static int i;
	public static int j;
	public static void main(String args[]){
		String txtFile = "worstCase.txt"; //txt file arrays are extracted from.
		String line = "";
		try (BufferedReader br = new BufferedReader(new FileReader(txtFile))){
			while ((line = br.readLine()) != null){ //each array is separated by line. this while loop retrieves each array.
				String[] n = line.split(","); //splits array elements by their commas. 
				int[] arrays1 = new int[n.length];
				int[] arrays2 = new int[n.length];
			
				for (int i = 0 ; i < n.length ; i++){ 
					arrays1[i] = Integer.parseInt(n[i]);
					arrays2[i] = Integer.parseInt(n[i]);
				}

				int k = getMax(arrays2) + 1; //uses getMax method to retrieve max to calculate k.
				int[] B = new int[arrays2.length];


				int iterations = 10; //the number of times each array is put through each algorithm.
				

				//Insertion Sort Timing.
				long insertionStart = System.nanoTime(); //gets system time before algorithm.
				for (int i = 1 ; i <= iterations ; i++){
					insertionSort(arrays1, arrays1.length); //calls insertionSort method.
				} 
				long insertionStop = System.nanoTime(); //gets system time after algorithm.


				long insertionAVG = 0;
				insertionAVG = ((insertionStop - insertionStart) / iterations); //calculates insertion average run time by dividing by iterations.
				System.out.println();
				System.out.println("[InsertionSort] Average run time(ns): " + insertionAVG); //outputs average in nanoseconds.
				System.out.println();



				//Counting Sort Timing.
				long countingStart = System.nanoTime(); //gets system time before algorithm.
				for (int i = 1 ; i <= iterations ; i++){
					countingSort(arrays2, B, arrays2.length, k);//calls countingSort method.
				}
				long countingStop = System.nanoTime(); //gets system time after algorithm.


				long countingAVG = 0;
				countingAVG = ((countingStop - countingStart) / iterations); //calculates counting average run time by dividing by iterations.
				System.out.println();
				System.out.println("[CountingSort] Average run time(ns): " + countingAVG); //outputs average in nanoseconds.
				System.out.println();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getMax(int[] arrayInput){ //method to get max array elements(k-1)
		int max = arrayInput[0];
		for(int i = 1 ; i < arrayInput.length ; i++){ 
			if(arrayInput[i] > max){ 
				max = arrayInput[i]; 
			}
		}
    return max;
	}

	public static void insertionSort(int[] Atemp, int n){
		System.out.println();
		System.out.println("Insertion Sort Algorithm");
		System.out.println("-------------------------");
		System.out.print("[InsertionSort] Unsorted Array: ");
		
		int[] A = new int[Atemp.length]; //assigns Atemp to A. Had to use Atemp as it was static.
		for (int i = 0 ; i < Atemp.length ; i++){
			A[i] = Atemp[i];
		}
		
		//prints out unsorted array for insertionSort.
		for (int i = 0 ; i < A.length ; i++){
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(A[i]);
		}

		//InsertionSort algorithm:
		for (i = 1 ; i <= n - 1; i++){
			item = A[i];
			j = i -  1;
			while (j >= 0 && A[j] > item){
				A[j + 1] = A[j];
				j = j - 1;
			}
			A[j + 1] = item;
		}
		

		System.out.println();
		System.out.println();
		System.out.print("[InsertionSort] Sorted Array: ");
		for (int i = 0 ; i < A.length ; i++){ //for loop prints out sorted array(B) for insertionSort.
			if (i > 0) {
				System.out.print(", "); //elements separated by commas.
			}
			System.out.print(A[i]);
		}
		System.out.println();
	}

	public static void countingSort(int[] A, int[] B, int n, int k){
		System.out.println();
		System.out.println("Counting Sort Algorithm");
		System.out.println("------------------------");
		System.out.print("[CountingSort] Unsorted Array: ");
		
		for (int i = 0 ; i < A.length ; i++){ //for loop prints out unsorted array(A) for countingSort.
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(A[i]);
		}
		
		//CountingSort algorithm:
		int[] C = new int[k];
		for (i = 0 ; i <= k - 1 ; i++){
			C[i] = 0;
		}
		for (j = 0 ; j <= n - 1 ; j++){
			C[A[j]] = C[A[j]] + 1;
		}
		for (i = 1 ; i <= k - 1 ; i++){
			C[i] = C[i] + C[i-1];
		}
		for (j = n - 1 ; j >= 0 ; j--){
			B[C[A[j]] - 1] = A[j];
			C[A[j]] = C[A[j]] - 1;
		}
		
		System.out.println();		
		System.out.println();
		System.out.print("[CountingSort] Sorted Array: ");
		for (int i = 0 ; i < B.length ; i++){ //for loop prints out sorted array(B) for countingSort.
			if (i > 0) {
				System.out.print(", "); //elements separated by commas.
			}
			System.out.print(B[i]);
		}
		System.out.println();
	}
}