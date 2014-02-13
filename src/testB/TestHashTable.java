package testB;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import phaseB.HashTable;
import providedCode.Comparator;
import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.Hasher;
import providedCode.SimpleIterator;
import test.TestDataCounter;


public class TestHashTable extends TestDataCounter<Integer> {
	private static final long TIMEOUT = 2000;
	private HashTable<Integer> ht;
	
	@Override
	protected DataCounter<Integer> getDataCounter() {
		return new HashTable<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { return e1 - e2; }
		}, new IntHasher());
	}
	
	@Before
	public void setUp() {
		super.setUp();
		ht = new HashTable<Integer>(new Comparator<Integer>() {
				public int compare(Integer e1, Integer e2) { return e1 - e2; }
			}, new IntHasher());
	}
	
	private class IntHasher implements Hasher<Integer> {
		@Override
		public int hash(Integer e) {
			return e;
		}
	}
	
	@Test(timeout = TIMEOUT)
	public void test() {
		ht.incCount(1);
		ht.incCount(2);
		assertEquals(ht.getCount(1), 1);
		assertEquals(ht.getCount(2), 1);
	}
	
	// ====================== Tests For Resizing ============================
	
	@Test(timeout = TIMEOUT)
	public void test_insert_until_resize_once() {
		int[] vals = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
		addAndTestSize("Should have size 14", vals, 14);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_insert_until_resize_multiple_times() {
		for(int i = 0; i < 5000; i++) {
			ht.incCount(i);
		}
		assertEquals("Size should be 5000", 5000, ht.getSize());
	}
	
	// ======================= Test for internal Ordering ==============
	
	@Test(timeout = TIMEOUT)
	public void test_collision_two_values() {
		int[] startArray = {1, 12};
		
		for(int i = 0; i < startArray.length; i++) { ht.incCount(startArray[i]); }
		int[] expected = {12, 1};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = ht.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// test
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_collision_mult_values() {
		int[] startArray = {1, 12, 23, 34, 45};
		
		for(int i = 0; i < startArray.length; i++) { ht.incCount(startArray[i]); }
		int[] expected = {45, 34, 23, 12, 1};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = ht.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// test
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_order_of_nonrepeated_values() {
		int[] startArray = {0, 1, 2, 3, 4, 5, 6, 7};
		
		for(int i = 0; i < startArray.length; i++) { ht.incCount(startArray[i]); }
		int[] expected = startArray;
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = ht.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// test
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_order_of_repeated_values() {
		int[] startArray = {0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 5, 6, 6, 8};
		
		for(int i = 0; i < startArray.length; i++) { ht.incCount(startArray[i]); }
		int[] expected = {0, 1, 2, 3, 5, 6, 8};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = ht.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// test
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_order_of_nonsequential_values() {
		int[] startArray = {5, 1, 4, 3, 2};
		
		for(int i = 0; i < startArray.length; i++) { ht.incCount(startArray[i]); }
		int[] expected = {1, 2, 3, 4, 5};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = ht.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// test
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_order_of_large_values() {
		int[] startArray = {22, 45, 57, 69};
		
		for(int i = 0; i < startArray.length; i++) { ht.incCount(startArray[i]); }
		int[] expected = {22, 45, 57, 69};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = ht.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		// test
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	// ======================= Private Helpers ==========================
	
	private void addAndTestSize(String msg, int[] input, int expected) {
		for(int val : input) 
			ht.incCount(val);
		assertEquals(msg, expected, ht.getSize());
	}
}
