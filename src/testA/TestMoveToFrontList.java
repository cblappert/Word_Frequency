package testA;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import phaseA.*;
import providedCode.*;
import test.TestDataCounter;


public class TestMoveToFrontList extends TestDataCounter<Integer> {

	@Override
	public DataCounter<Integer> getDataCounter() {
		return new MoveToFrontList<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { return e1 - e2; }
		});
	}

	
	
	/** Test Ordering =======================================================================================**/
	
	@Test(timeout = TIMEOUT)
	public void test_internal_ordering_no_duplicate() {
		int[] startArray = {0, 1, 2, 3, 4, 5};
		
		// Expected array is in reverse insertion order
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		int[] expected = {5, 4, 3, 2, 1, 0};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
	
	public void test_internal_ordering_with_duplicate() {
		int[] startArray = {0, 1, 2, 3, 2, 1};
		
		// Expected array is in order of last access
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		int[] expected = {1, 2, 3, 0};
		
		// Actual array returned by the iterator
		int i = 0;
		SimpleIterator<DataCount<Integer>> iter = dc.getIterator();
		int[] actual = new int[expected.length];
		while(iter.hasNext()) { actual[i++] = iter.next().data; }
		
		assertArrayEquals("Added " + Arrays.toString(startArray), expected, actual);
	}
}
