package phaseA;
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
public class MoveToFrontList<E> extends DataCounter<E> {

	
	public MoveToFrontList(Comparator<? super E> c) {
		// TODO: To-be implemented
	}
	
	@Override
	public void incCount(E data) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCount(E data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
