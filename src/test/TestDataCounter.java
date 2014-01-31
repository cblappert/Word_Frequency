package test;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;

/**
 **   @author Christopher Blappert, Michael Mitasev
 **	  1/31/14
 **	  CSE 332 AB
 **	  Hye Kim
 **   Programming project 2a
 **
 **	  This class provides JUnit tests for any DataCounter
 **   object, and does so by testing on Integers.
 **/

// Although this class is generic, I assume the getDataCounter
// will return an DataCounter<Integer> so that I can add test code
// that requires creating elements to put into the datacounter
public abstract class TestDataCounter<E> {
	protected static final long TIMEOUT = 2000;
	protected DataCounter<Integer> dc;
	protected abstract DataCounter<E> getDataCounter();
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		dc = (DataCounter<Integer>) getDataCounter();
	}
	
	/** Test Size =======================================================================================**/

	@Test(timeout = TIMEOUT)
	public void test_size_empty(){
		assertEquals("DataCounter should have size 0 when constructed", 0, dc.getSize());
	}
	
	@Test(timeout = TIMEOUT) 
	public void test_size_after_adding_single_num(){
		addAndTestSize("DataCounter should have size 1 after adding single 5", new int[]{5}, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_many_same_num(){
		addAndTestSize("DataCounter should have size 1 after adding multiple 5", new int[]{5,5,5}, 1);
	}

	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_unique_nums(){
		int[] testArray = {0,1,2,3,4};
		addAndTestSize("List " + Arrays.toString(testArray), testArray, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_duplicate_nums(){
		int[] testArray = {0,0,1,1,2,2,3,3,4,4};
		addAndTestSize("List " + Arrays.toString(testArray), testArray, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_after_adding_nonsequential_duplicate_nums(){
		int[] testArray = {0,1,0,1,3,4,3,2,4,2};
		addAndTestSize("List " + Arrays.toString(testArray), testArray, 5);
	}
	
	/** Test getCount =======================================================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_single_num(){
		int key = 9;
		int[] testArray = {9};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_few_different_num(){
		int key = 9;
		int[] testArray = {8, 9, 7};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_same_num(){
		int key = 9;
		int[] testArray = {9, 9, 9, 9, 9, 9, 9};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 7);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_get_count_after_adding_many_diff_nums(){
		int key = 5;
		int[] testArray = {0, 5, -1, 5, 1, 5, 2};
		addAndGetCount("Added " + Arrays.toString(testArray) + ", key=" + key, testArray, key, 3);
	}
	
	/** Test Iterator =======================================================================================**/

	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_iterator_empty() {
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		iter.next(); 
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_hasNext_empty() {
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		assertFalse("Empty iterator should not have a next element", iter.hasNext()); 
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_one_item() {
		dc.incCount(7);
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		assertEquals("Added 7, iterator should return one 7", 7, (int) iter.next().data); 
	}
	
	@Test(timeout = TIMEOUT)
	public void test_iterator_get_all_data() {
		int[] startArray = {7, -5, -5, -6, 6, 10, -9, 4, 8, 6};
		
		// Expected array has no duplicates and is sorted
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		int[] expected = {-9, -6, -5, 4, 6, 7, 8, 10};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// Sort and test
		Arrays.sort(actual);
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_iterator_get_next_at_end_of_data() {
		int[] startArray = {7, -5, -5, -6, 6, 10, -9, 4, 8, 6};
		
		// add some elements
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		while(iter.hasNext()) { iter.next(); }
		iter.next(); // should error
	}
	
	/** Private methods =======================================================================================**/

	private void addAndTestSize(String message, int[] input, int expected){
		for(int num : input) { dc.incCount(num); }
		assertEquals(message, expected, dc.getSize());
	}
	
	private void addAndGetCount(String message, int[] input, int key, int expected){
		for(int num : input) { dc.incCount(num); }
		assertEquals(message, expected, dc.getCount(key));
	}
}
