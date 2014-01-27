package phaseA;

import java.util.NoSuchElementException;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items (with a count of 1) to the front of the list.
 * 3. Whenever an existing item has its count incremented by incCount or 
 *    retrieved by getCount, move it to the front of the list. That means 
 *    you remove the node from its current position and make it the first 
 *    node in the list.
 * 4. You need to implement an iterator. The iterator should not move elements
 *    to the front. The iterator should return elements in the order they are 
 *    stored in the list, starting with the first element in the list.
 * TODO: Develop appropriate JUnit tests for your MoveToFrontList.
 */

/**
 **   Christopher Blappert
 **	  1/31/14
 **	  CSE 332 AB
 **	  Hye Kim
 **   Programming project 2a
 **
 **	  This class provides an implementation of a linked list
 **   that keeps track of data values and their respective counts
 **   while being efficient for frequently accessed values.
 **/

public class MoveToFrontList<E> extends DataCounter<E> {
	private int size;
	private ListNode front;
	private Comparator<? super E> comparator;
	
	// Pre: comparator not null else comparison operations will error
	// Post: initializes a new MoveToFrontList
	public MoveToFrontList(Comparator<? super E> c) {
		size = 0;
		front = null;
		comparator = c;
	}
	
	// Post: increments the count of the data or adds it to the list
	// 		 and moves it to the front of the list
	@Override
	public void incCount(E data) {
		ListNode current = front;
		boolean foundNode = false;
		
		if(front == null) {
			front = new ListNode(data, front);
			size++;
			return;
		}
		if(comparator.compare(front.data, data) == 0) {
			front.count++;
		} else {
			while(!foundNode && current.next != null) {
				if(comparator.compare(current.next.data, data) == 0) {
					foundNode = true;
					current.next.count++;
				} else {
					current = current.next;
				}
			}
			if(foundNode) {
				moveToFront(current);
			} else {
				current.next = new ListNode(data);
				size++;
			}
		}
	}
	
	// Private helper that moves the node after the node passed to the front
	// of the MoveToFrontList
	private void moveToFront(ListNode node) {
		ListNode temp = node.next;
		node.next = node.next.next;
		temp.next = front;
		front = temp;
	}

	@Override
	public int getSize() {
		return size;
	}

	// Pre: object is part of the list, otherwise returns -1
	// Post: returns the count of the data object passed
	@Override
	public int getCount(E data) {
		ListNode current = front;
		if(current == null) {
			return -1;
		}
		if(comparator.compare(current.data, data) == 0) {
			return current.count;
		}
		while(current.next != null) {
			if(comparator.compare(current.next.data, data) == 0) {
				int result = current.next.count;
				moveToFront(current);
				return result;
			}
			current = current.next;
		}
		return -1;
	}

	// Post: returns an iterator for the object that can
	//		 retrieve data in the form of DataCount objects
	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			ListNode current = front;
			@Override
			public DataCount<E> next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				DataCount<E> value = new DataCount<E>(current.data, 
						current.count);
				current = current.next;
				return value;
			}

			@Override
			public boolean hasNext() {
				return current != null && current.next != null;
			}
		};
	}

	// Nested class that is the nodes that contain the data and count 
	// of each element contained in the list
	private class ListNode {
		public E data;
		public int count;
		public ListNode next;
		
		public ListNode(E d) {
			this(d, null);
		}
		
		public ListNode(E d, ListNode node) {
			data = d;
			next = node;
			count = 1;
		}
	}
}
