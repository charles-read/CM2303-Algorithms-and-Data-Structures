//c1646151
import java.util.*;
import java.io.*;
import java.lang.*;

public class ArrayGenerator {
    public static void main (String args[]){
        for (int i = 10 ; i <= 60 ; i+=10){ //important loop for generating arrays. i+=10 means increment arrays by 10 each loop iteration.
            int n = i; //size of array.
            int min = 0;
            int max = 100; //important for counting sort, changes range(k).
            try{
                BestCase(n); //calls best method which produces sorted arrays with n elements.
                AverageCase(n, min, max); //calls averageCase method which produces random arrays with n elements.
            }

            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void BestCase(int n) throws java.io.IOException{
        List<Integer> bestArrays = new ArrayList<Integer>();
        int[] numbers = new int[n];
        for (int i = 0; (i < numbers.length); i++) { //for loops adds incrementing integers to 'numbers'
            numbers[i] = i;
        }
        for (int x : numbers) {
            bestArrays.add(x);
        }

        WorstCase(n , numbers); //calls worst method which produces reversed arrays with n elements.

        FileWriter writeArray = new FileWriter("bestCase.txt",true); //creates and writes to external txt file.
        List<String> bestArraysSize = new ArrayList<String>(bestArrays.size());
        for (int j : bestArrays){
            bestArraysSize.add(String.valueOf(j));
        }
        for(String str : bestArraysSize){
    	   writeArray.write(str);
    	   writeArray.write(","); //commas separate array elements
        }
        writeArray.write("\n");
        writeArray.close();
    }

    public static void AverageCase(int n, int min, int max) throws java.io.IOException {
        Random r = new Random(); //uses random from import
        List<Integer> avgArray = new ArrayList<Integer>();
        int[] numbers = new int[n];
        for (int i = 0; (i < numbers.length); i++) {
            numbers[i] = r.nextInt(max - min + 1) + min; //selects random number in range and adds to numbers.
        }
        for (int x : numbers ) {
            avgArray.add(x);
        }
        FileWriter writeArray = new FileWriter("averageCase.txt", true); //creates and writes to external txt file.
        
        List<String> avgArraySize = new ArrayList<String>(avgArray.size());
        for (int j : avgArray){
            avgArraySize.add(String.valueOf(j));
        }
        for(String str : avgArraySize) {
            writeArray.write(str);
            writeArray.write(","); //commas separate array elements
        }
        writeArray.write("\n");
        writeArray.close();
    }


    public static void WorstCase(int n, int[] numbers) throws java.io.IOException {
        List<Integer> worstArray = new ArrayList<Integer>();

        for (int i = numbers.length - 1; (i >= 0); i--) { //reverses 'numbers' varible length from bestCase.
            worstArray.add(numbers[i]); 
        }
        FileWriter writeArray = new FileWriter("worstCase.txt", true); //creates and writes to external txt file.
       
        List<String> worstArraySize = new ArrayList<String>(worstArray.size());
        for (int x : worstArray){
            worstArraySize.add(String.valueOf(x));
        }

        for(String str : worstArraySize) {
            writeArray.write(str);
            writeArray.write(","); //commas separate array elements
        }
        writeArray.write("\n");
        writeArray.close();
    }
}
