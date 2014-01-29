package main;
import java.io.IOException;

import phaseA.*;
import providedCode.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	
	// Pre: file passed is valid, counter not null
	// Post: counts the occurrances of words in the file using the counter
    private static void countWords(String file, DataCounter<String> counter) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        }catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
    }
    
    
    // Pre: counter passed not null, otherwise returns null
    // Post: returns an array of DataCount objects containing each unique word
 	@SuppressWarnings("unchecked")
	private static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
 		if(counter == null) {
 			return null;
 		}
 		DataCount<E>[] result = (DataCount<E>[]) new DataCount[counter.getSize()];
 		SimpleIterator<DataCount<E>> iterator = counter.getIterator();
 		int i = 0;
 		while(iterator.hasNext()) {
 			result[i] = iterator.next();
 			i++;
 		}
 		return result;
 	}
    
 	
    // IMPORTANT: Used for grading. Do not modify the printing format!
 	// You may modify this method if you want.
    private static void printDataCount(DataCount<String>[] counts) {
    	for (DataCount<String> c : counts) {
            System.out.println(c.count + "\t" + c.data);
        }
    }
    
    // Pre: paramenters are valid
    // Post: processes parameters as shown in the spec
    public static void main(String[] args) {
    	DataCounter<String> counter = null;
    	switch(args[0]) {
    	case "-b": 
    		counter = new BinarySearchTree<String>(new StringComparator()); 
    		break;
    	case "-a":
    		counter = new AVLTree<String>(new StringComparator()); 
    		break;
    	case "-m":
    		counter = new MoveToFrontList<String>(new StringComparator());
    		break;
    	}
    	countWords(args[2], counter); 
        DataCount<String>[] counts = getCountsArray(counter);        
    	switch(args[1]) {
    	case "-is": 
    		Sorter.insertionSort(counts, new DataCountStringComparator());
    		break;
    	case "-hs":
    		Sorter.heapSort(counts, new DataCountStringComparator());
    		break;
    	}
        printDataCount(counts);
    }
}
