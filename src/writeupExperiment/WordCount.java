package writeupExperiment;
import java.io.IOException;

import main.Sorter;
import phaseA.*;
import phaseB.HashTable;
import phaseB.StringHasher;
import providedCode.*;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {
	private static final int NUM_TEST = 30;
	private static final int NUM_WARMUP = 20;
	
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
    
    // Private helper method to time 
    public static double getAverageRuntime(String[] arguments) {
        double totalTime = 0;
        for(int i=0; i<NUM_TEST; i++) {
          long startTime = System.currentTimeMillis();
          WordCount.main(arguments);
          long endTime = System.currentTimeMillis();
          if(NUM_WARMUP <= i) {                    // Throw away first NUM_WARMUP runs to encounter JVM warmup
            totalTime += (endTime - startTime);
          }
        }
        return totalTime / (NUM_TEST-NUM_WARMUP);  // Return average runtime.
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
    	case "-h":
    		counter = new HashTable<String>(new StringComparator(), new StringHasher());
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
    	case "-os":
    		Sorter.otherSort(counts, new DataCountStringComparator());
    		break;
    	default:
    		if(args[1].startsWith("-k")) {
    			int k = Integer.parseInt(args[1].substring(3));
    			Sorter.topKSort(counts, new DataCountStringComparator(), k);
    		}
    		break;
    	}
    }
}
