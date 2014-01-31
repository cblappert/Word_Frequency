package testA;
import static org.junit.Assert.*;

import org.junit.Test;

import phaseA.*;
import providedCode.Comparator;
import providedCode.DataCounter;
import test.TestDataCounter;

/**
 **   @author Christopher Blappert, Michael Mitasev
 **	  1/31/14
 **	  CSE 332 AB
 **	  Hye Kim
 **   Programming project 2a
 **
 **	  This class provides JUnit tests for the AVLTree class
 **/

public class TestAVLTree extends TestDataCounter<Integer> {
	@Override
	protected DataCounter<Integer> getDataCounter() {
		return new AVLTree<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) { return e1 - e2; }
		});
	}
	
	/** Test Internal Correctness ================================================================================**/

	@Test(timeout = TIMEOUT)
	public void test_tree_with_no_rotations_input() {
		int[] startArray = {5, 1, 9, 3};
		// should not require rebalancing
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		boolean treeState = ((AVLTree<Integer>) dc).testAVLTree();
		assertTrue(treeState);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_tree_with_left_left_imbalance_input() {
		int[] startArray = {5, 4, 3};
		// should create left-left imbalance, then fix it
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		boolean treeState = ((AVLTree<Integer>) dc).testAVLTree();
		assertTrue(treeState);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_tree_with_right_right_imbalance_input() {
		int[] startArray = {5, 9, 10, 20};
		// should create right-right imbalance then fix it
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		boolean treeState = ((AVLTree<Integer>) dc).testAVLTree();
		assertTrue(treeState);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_tree_with_right_left_imbalance_input() {
		int[] startArray = {5, 9, 6, 3};
		// should create a right-left imbalance and fix it
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		boolean treeState = ((AVLTree<Integer>) dc).testAVLTree();
		assertTrue(treeState);
	}
	
	@Test(timeout = TIMEOUT)
	public void test_tree_with_left_right_imbalance_input() {
		int[] startArray = {5, 1, 4, 3};
		// should create a left-right imbalance and fix it
		for(int i = 0; i < startArray.length; i++) { dc.incCount(startArray[i]); }
		boolean treeState = ((AVLTree<Integer>) dc).testAVLTree();
		assertTrue(treeState);
	}
}
