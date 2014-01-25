package main;
import java.io.IOException;

import phaseA.*;
import providedCode.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	
	// TODO: Replace this comment with your own as appropriate.
	// You may modify this method if you want.
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
    
    
    // TODO: Replace this comment with your own as appropriate.
    // Implement method that returns an array of DataCount objects containing each unique word.
    // If generics confuse you, write non-generic version first and then adjust it.
    // Pre: counter passed not null, otherwise returns null
    // Post: 
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
    
    
    /** 
     *  TODO: Replace this comment with your own as appropriate.
 	 *  Edit this method (including replacing the dummy parameter checking below) 
 	 *  to process all parameters as shown in the spec.
 	 */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: filename of document to analyze");
            System.exit(1);
        }
        DataCounter<String> counter = new BinarySearchTree<String>(new StringComparator());
        countWords(args[0], counter); 
        DataCount<String>[] counts = getCountsArray(counter);
        Sorter.insertionSort(counts, new DataCountStringComparator());
        printDataCount(counts);
    }
}
