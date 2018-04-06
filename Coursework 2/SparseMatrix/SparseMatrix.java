/*  Charles Read 
 *  c1646151
 *
 * Didn't manage to get my add function working with the correct output values but all other functions work correctly.
 *
 */

import java.util.*;
import java.io.*;

// A class that represents a dense vector and allows you to read/write its elements
class DenseVector {
    private int[] elements;

    public DenseVector(int n) {
        elements = new int[n];
    }

    public DenseVector(String filename) {
        File file = new File(filename);
        ArrayList < Integer > values = new ArrayList < Integer > ();

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextInt()) {
                values.add(sc.nextInt());
            }

            sc.close();

            elements = new int[values.size()];
            for (int i = 0; i < values.size(); ++i) {
                elements[i] = values.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Read an element of the vector
    public int getElement(int idx) {
        return elements[idx];
    }

    // Modify an element of the vector
    public void setElement(int idx, int value) {
        elements[idx] = value;
    }

    // Return the number of elements
    public int size() {
        return (elements == null) ? 0 : (elements.length);
    }

    // Print all the elements 
    public void print() {
        if (elements == null) {
            return;
        }

        for (int i = 0; i < elements.length; ++i) {
            System.out.println(elements[i]);
        }
    }
}




// A class that represents a sparse matrix
public class SparseMatrix {
    // Auxiliary function that prints out the command syntax
    public static void printCommandError() {
        System.err.println("ERROR: use one of the following commands");
        System.err.println(" - Read a matrix and print information: java SparseMatrix -i <MatrixFile>");
        System.err.println(" - Read a matrix and print elements: java SparseMatrix -r <MatrixFile>");
        System.err.println(" - Transpose a matrix: java SparseMatrix -t <MatrixFile>");
        System.err.println(" - Add two matrices: java SparseMatrix -a <MatrixFile1> <MatrixFile2>");
        System.err.println(" - Matrix-vector multiplication: java SparseMatrix -v <MatrixFile> <VectorFile>");
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            printCommandError();
            System.exit(-1);
        }

        if (args[0].equals("-i")) {
            if (args.length != 2) {
                printCommandError();
                System.exit(-1);
            }

            SparseMatrix mat = new SparseMatrix();
            mat.loadEntries(args[1]);
            System.out.println("Read matrix from " + args[1]);

            System.out.println("The matrix has " + mat.numRows() + " rows and " + mat.numColumns() + " columns");
            System.out.println("It has " + mat.numNonZeros() + " non-zeros");
        } else if (args[0].equals("-r")) {
            if (args.length != 2) {
                printCommandError();
                System.exit(-1);
            }

            SparseMatrix mat = new SparseMatrix();
            mat.loadEntries(args[1]);
            System.out.println("Read matrix from " + args[1] + ":");
            mat.print();

        } else if (args[0].equals("-t")) {
            if (args.length != 2) {
                printCommandError();
                System.exit(-1);
            }

            SparseMatrix mat = new SparseMatrix();
            mat.loadEntries(args[1]);
            System.out.println("Read matrix from " + args[1]);
            SparseMatrix transpose_mat = mat.transpose();
            System.out.println();
            System.out.println("Matrix elements:");
            mat.print();
            System.out.println();
            System.out.println("Transposed matrix elements:");
            transpose_mat.print();

        } else if (args[0].equals("-a")) {
            if (args.length != 3) {
                printCommandError();
                System.exit(-1);
            }

            SparseMatrix mat1 = new SparseMatrix();
            mat1.loadEntries(args[1]);
            System.out.println("Read matrix 1 from " + args[1]);
            System.out.println("Matrix elements:");
            mat1.print();

            System.out.println();
            SparseMatrix mat2 = new SparseMatrix();
            mat2.loadEntries(args[2]);
            System.out.println("Read matrix 2 from " + args[2]);
            System.out.println("Matrix elements:");
            mat2.print();
            SparseMatrix mat_sum1 = mat1.add(mat2);

            System.out.println();
            mat1.multiplyBy(2);
            SparseMatrix mat_sum2 = mat1.add(mat2);

            mat1.multiplyBy(5);
            SparseMatrix mat_sum3 = mat1.add(mat2);

            System.out.println("Matrix1 + Matrix2 =");
            mat_sum1.print();
            System.out.println();

            System.out.println("Matrix1 * 2 + Matrix2 =");
            mat_sum2.print();
            System.out.println();

            System.out.println("Matrix1 * 10 + Matrix2 =");
            mat_sum3.print();

        } else if (args[0].equals("-v")) {
            if (args.length != 3) {
                printCommandError();
                System.exit(-1);
            }

            SparseMatrix mat = new SparseMatrix();
            mat.loadEntries(args[1]);
            DenseVector vec = new DenseVector(args[2]);
            DenseVector mv = mat.multiply(vec);

            System.out.println("Read matrix from " + args[1] + ":");
            mat.print();
            System.out.println();

            System.out.println("Read vector from " + args[2] + ":");
            vec.print();
            System.out.println();

            System.out.println("Matrix-vector multiplication:");
            mv.print();
        }
    }



    // Loading matrix entries from a text file
    // You need to complete this implementation
    public void loadEntries(String filename) {
        File file = new File(filename);

        try {
            Scanner sc = new Scanner(file);
            int numRows = sc.nextInt();

            numCols = sc.nextInt();
            count = new ArrayList<Integer>();
            entries = new ArrayList < ArrayList < Entry > > ();

            for (int i = 0; i < numRows; ++i) {
                entries.add(null);
            }

            while (sc.hasNextInt()) {
                // Read the row index, column index, and value of an element
                int row = sc.nextInt();
                int col = sc.nextInt();
                int val = sc.nextInt();
                count.add(val);

                ArrayList < Entry > inArray = new ArrayList < Entry >();

                //initialize empty rows
                if (entries.get(row) ==  null){
                    inArray.add(new Entry(col,val));
                }
                else{
                    for (int i = 0; i < entries.get(row).size(); i++){
                        inArray.add(entries.get(row).get(i));
                    }
                    inArray.add(new Entry (col, val));

                    //sort algorithm
                    SparseMatrix.Entry oldA =inArray.get(0);
                    for (int i = 1; i < inArray.size(); i++){
                        if (inArray.get(i).getColumn() < oldA.getColumn()){
                            inArray.set(i - 1, inArray.get(i));
                            inArray.set(i, oldA);
                        }
                        oldA = inArray.get(i);
                    }
                }   
            entries.set(row, inArray);
            }

        } catch (Exception e) {
            e.printStackTrace();
            numCols = 0;
            entries = null;
        }
    }

    // Default constructor
    public SparseMatrix() {
        numCols = 0;
        entries = null;
    }


    // A class representing a pair of column index and elements
    private class Entry {
        private int column; // Column index
        private int value; // Element value

        // Constructor using the column index and the element value
        public Entry(int col, int val) {
            this.column = col;
            this.value = val;
        }

        // Copy constructor
        public Entry(Entry entry) {
            this(entry.column, entry.value);
        }

        // Read column index
        int getColumn() {
            return column;
        }

        // Set column index
        void setColumn(int col) {
            this.column = col;
        }

        // Read element value
        int getValue() {
            return value;
        }

        // Set element value
        void setValue(int val) {
            this.value = val;
        }
    }
   

    // Adding two matrices - Doesn't output correct results.
    public SparseMatrix add(SparseMatrix M){
        SparseMatrix addInstance = new SparseMatrix();
        ArrayList< ArrayList<Entry> > matrix1 = new ArrayList< ArrayList<Entry> >();
        int Rows = entries.size();
        int Cols = numCols;

        //initialize empty rows
        for(int i = 0; i < Rows; ++i) {
            matrix1.add(null);
        }

        for(int i = 0; i < Rows; ++ i){
            ArrayList<Entry> currentRow1 = entries.get(i);
            ArrayList<Entry> currentRow2 = M.entries.get(i);

            int currentCol1 = -1;
            int currentCol2 = -1;
            int entriesIdx = -1;
            int value1 = 0, value2 = 0, value3 = 0;
            
            if((currentRow1 != null && 
                (!currentRow1.isEmpty())) && 
                (currentRow2 != null && 
                (!currentRow2.isEmpty()))){
                entriesIdx = 0;
                currentCol1 = currentRow1.get(entriesIdx).getColumn();
                currentCol2 = currentRow2.get(entriesIdx).getColumn();
            }
            
            inArray = new ArrayList< Entry >();

            for(int j = 0;  j < numCols; ++ j){
                if (j == currentCol1 || j == currentCol2){
                    if(j == currentCol1 && j != currentCol2){
                        value1 = currentRow1.get(entriesIdx).getValue();
                        value2 = 0;
                        value3 = value1;
                        inArray.add(new Entry(currentCol1,value3));
                    }
                    else if (j == currentCol2 && j != currentCol1){
                        value1 = 0;
                        value2 = currentRow2.get(entriesIdx).getValue();
                        value3 = value2;
                        inArray.add(new Entry(currentCol2, value3));
                    }
                    else if (j == currentCol2 && j == currentCol1){
                        value1 = currentRow1.get(entriesIdx).getValue();
                        value2 = currentRow2.get(entriesIdx).getValue();
                        value3 = value1 + value2;
                        inArray.add(new Entry(currentCol1, value3));
                    }
                    entriesIdx++;

                    SparseMatrix.Entry oldA = inArray.get(0);
                    for (int k = 1; k < inArray.size(); k++){
                        if(inArray.get(k).getColumn() < oldA.getColumn()){
                            inArray.set(k - 1, inArray.get(k));
                            inArray.set(k, oldA);
                        }
                        oldA = inArray.get(k);
                    }
                }
            }
            matrix1.set(i, inArray);
        }
        addInstance.numCols = Cols;
        addInstance.entries = matrix1;
        return addInstance;
    }


    // Transposing a matrix
    public SparseMatrix transpose() {
        SparseMatrix transInstance = new SparseMatrix();
        int transCols = numRows();
        int transRows = numColumns();


        transInstance.numCols = transCols;
        transInstance.entries = new ArrayList < ArrayList < Entry > > ();
        for (int i = 0; i < transRows; ++i) {
            transInstance.entries.add(null);
        }

        int rowIndex = 0;
        for (int k = 0; k < entries.size(); k++){
            ArrayList<Entry> oldRow = entries.get(k);
            if (oldRow != null){
                for (Entry currentEntry1 : oldRow){
                    int rowNum = currentEntry1.getColumn(); //get row number from first matrix column index.
                    if (transInstance.entries.get(rowNum) == null) {
                        transInstance.entries.set(rowNum, new ArrayList < Entry > ());
                    }

                    Entry entryTemp = new Entry(currentEntry1); //temp value for secondary matrix.
                    entryTemp.setColumn(rowIndex); //sets column as row doe transpose
                    transInstance.entries.get(rowNum).add(entryTemp);
                }
            }

                rowIndex++;
        }
        return transInstance;
    }


    // Matrix-vector multiplication
  public DenseVector multiply(DenseVector v) {
        DenseVector multiplyInstance = new DenseVector(entries.size());
        int rowIndex = 0;

        for (int k = 0; k < entries.size(); k++){
            ArrayList<Entry> matrixRow = entries.get(k);
            int mulSum = 0;

            for (Entry currentEntry : matrixRow) { //for each entry within matrix
                mulSum += (currentEntry.getValue()*v.getElement(currentEntry.getColumn())); //multiplies entry by corresponding vector element.
            }
            multiplyInstance.setElement(rowIndex, mulSum);
            rowIndex++;
        }
        return multiplyInstance;
    }


    // Count the number of non-zeros
    public int numNonZeros() {
        return count.size();
    }

    // Multiply the matrix by a scalar, and update the matrix elements
    public void multiplyBy(int scalar) {
        for (int i = 0; i < entries.size(); i++){ //for each row
            ArrayList<Entry> currentRow = entries.get(i);
            for (int j = 0; j < currentRow.size(); j++){// for each element in row. 
                Entry currentEntry = currentRow.get(i);
                currentEntry.setValue(currentEntry.getValue() * scalar); //multiplies current entry by scalar.
            }
        }
    }


    // Number of rows of the matrix
    public int numRows() {
        if (this.entries != null) {
            return this.entries.size();
        } else {
            return 0;
        }
    }

    // Number of columns of the matrix
    public int numColumns() {
        return this.numCols;
    }





    // Output the elements of the matrix, including the zeros
    // Do not modify this method
    public void print() {
        int numRows = entries.size();
        for (int i = 0; i < numRows; ++i) {
            ArrayList < Entry > currentRow = entries.get(i);
            int currentCol = -1, entryIdx = -1;
            if (currentRow != null && (!currentRow.isEmpty())) {
                entryIdx = 0;
                currentCol = currentRow.get(entryIdx).getColumn();
            }

            for (int j = 0; j < numCols; ++j) {
                if (j == currentCol) {
                    System.out.print(currentRow.get(entryIdx).getValue());
                    entryIdx++;
                    currentCol = (entryIdx < currentRow.size()) ? currentRow.get(entryIdx).getColumn() : (-1);
                } else {
                    System.out.print(0);
                }

                System.out.print(" ");
            }

            System.out.println();
        }
    }

    private int numCols; // Number of columns
    private ArrayList < ArrayList < Entry > > entries; // Entries for each row
    private ArrayList<Integer> count;
    private ArrayList< Entry > inArray;
}