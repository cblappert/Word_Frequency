package phaseB;
import java.util.NoSuchElementException;

import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You may implement any kind of HashTable discussed in class; the only 
 *    restriction is that it should not restrict the size of the input domain 
 *    (i.e., it must accept any key) or the number of inputs (i.e., it must 
 *    grow as necessary).
 * 2. You should use this implementation with the -h option.
 * 3. To use your HashTable for WordCount, you will need to be able to hash 
 *    strings. Implement your own hashing strategy using charAt and length. 
 *    Do not use Java's hashCode method.
 * TODO: Develop appropriate JUnit tests for your HashTable.
 */

/**
 *   @author Christopher Blappert, Michael Mitasev
 *	 2/13/14
 *	 CSE 332 AB
 *	 Hye Kim
 *   Programming project 2b
 *
 *	 This class provides an implementation of a hash table
 *   that keeps counts of data objects, supporting fast
 *   retrieval and incrementing of counts. Maximum number
 *   of entries is 327869.
 **/

public class HashTable<E> extends DataCounter<E> {
	private static final int[] PRIME_SIZES = {11, 23, 41, 83, 163, 331, 641, 
		1283, 2579, 5147, 10243, 20483, 40961, 81929, 163841, 327869};
	// Hardcoded list of primes works up to size of 327869, roughly
	// doubling in size each time.
	private static final double LOAD_FACTOR = 1.00;
	private int currentArraySize;
	private HashBucket[] dataArray;
	private int size;
	private Comparator<? super E> comparator;
	private Hasher<E> hasher;
	
	/**
	 * Creates a new HashTable with the specified comparator
	 * and hasher.
	 * 
	 * @param c A comparator to determine the equality of the 
	 * objects being stored in the hashtable
	 * @param h A hasher for the objects to be stored in the hashtable
	 * @throws IllegalArgumentException if either argument is null.
	 */
	@SuppressWarnings("unchecked")
	public HashTable(Comparator<? super E> c, Hasher<E> h) {
		if(c == null || h == null) {
			throw new IllegalArgumentException();
		}
		currentArraySize = 0;
		dataArray = (HashBucket[]) 
				new HashTable.HashBucket[PRIME_SIZES[currentArraySize]];
		comparator = c;
		hasher = h;
		size = 0;
		// TODO: To-be implemented
	}
	
	/**
	 * Increases the count of an object in the hashtable, or adds
	 * it if it does not exist in the hashtable.
	 * 
	 * @param data The data for which the count needs to be increased
	 * @throws IllegalArgumentException if the data passed is null.
	 */
	@Override
	public void incCount(E data) {
		if(data == null) {
			throw new IllegalArgumentException();
		}
		int key = Math.abs(hasher.hash(data)) % dataArray.length;
		// find if the data is in the chain already
		HashBucket bucket = dataArray[key];
		while(bucket != null) {
			if(comparator.compare(bucket.data, data) == 0) {
				bucket.count++;
				return;
			}
			bucket = bucket.next;
		}
		// if it isnt in the chain
		if(bucket == null) {
			// check if we need to rehash
			if((size + 1) > (int) (LOAD_FACTOR * dataArray.length)) {
				rehash();
				key = Math.abs(hasher.hash(data)) % dataArray.length;
			}
			dataArray[key] = new HashBucket(data, dataArray[key]);
			size++;
		}
	}

	/**
	 * Used to get the size of the HashTable
	 * 
	 * @return The size of the HashTable
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * Gets the count of an object that has been stored in the HashTable
	 * 
	 * @param data The data for which to retrieve the count.
	 * @return The count of the data.
	 * @throws IllegalArgumentException if the data passed is null.
	 */
	@Override
	public int getCount(E data) {
		if(data == null) {
			throw new IllegalArgumentException();
		}
		int key = Math.abs(hasher.hash(data)) % dataArray.length;
		HashBucket bucket = dataArray[key];
		while(bucket != null) {
			if(comparator.compare(bucket.data, data) == 0) {
				return bucket.count;
			}
		}
		return 0;
	}

	/**
	 * Gets an iterator for the HashTable that is able
	 * to return DataCount objects representing the elements
	 * in the table.
	 * 
	 * @return An iterator for the data in the HashTable
	 */
	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			private int currentIndex = 0;
			private HashBucket currentBucket = dataArray[0];
			
			/**
			 * Gets the next object in the table if there is one.
			 * 
			 * @throws NoSuchElementException if there is no next object.
			 * @return the next DataCount object in the table.
			 */
			@Override
			public DataCount<E> next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				HashBucket temp = currentBucket;
				currentBucket = currentBucket.next;
				return temp;
			}

			/**
			 * Gives a boolean representing if there are more elements
			 * to be iterated over.
			 * 
			 * @return Whether or not there is another element in the iterator
			 */
			@Override
			public boolean hasNext() {
				while(currentIndex < dataArray.length - 1 && 
						currentBucket == null) {
					currentBucket = dataArray[++currentIndex];
				}
				return currentIndex < dataArray.length && 
						currentBucket != null;
			}
			
		};
	}
	
	// Private helper method to rehash the table when the load factor
	// exceeds LOAD_FACTOR. Rehashes to a prime number roughly double
	// the previous size.
	@SuppressWarnings("unchecked")
	private void rehash() {
		currentArraySize++;
		HashBucket[] newDataArray = (HashBucket[]) 
				new HashTable.HashBucket[PRIME_SIZES[currentArraySize]];
		for(int i = 0; i < dataArray.length; i++) {
			HashBucket bucket = dataArray[i];
			while(bucket != null) {
				int key = Math.abs(hasher.hash(bucket.data)) % 
						newDataArray.length;
				HashBucket nextBucket = bucket.next;
				bucket.next = newDataArray[key];
				newDataArray[key] = bucket;
				bucket = nextBucket;
			}
		}
		dataArray = newDataArray;
	}
	
	// Private inner class representing the buckets of the hashtable
	// for use in separate chaining.
	private class HashBucket extends DataCount<E> {
		private HashBucket next;
		
		public HashBucket(E data, HashBucket next) {
			super(data, 1);
			this.next = next;
		}
	}
}
