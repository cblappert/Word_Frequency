package phaseA;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. It is exactly like the binary heap we studied, except nodes should have 4 children 
 * 	  instead of 2. Only leaves and at most one other node will have fewer children. 
 * 2. Use array-based implementation, beginning at index 0 (Root should be at index 0). 
 *    Construct the FourHeap by passing appropriate argument to superclass constructor.
 *    Hint: Complete written homework #2 before attempting this.   
 * 3. Throw appropriate exceptions in FourHeap whenever needed. For example, 
 * 	  when deleteMin on an empty heap, you could use UnderFlowException as is done in the Weiss text, 
 *    or you could use NoSuchElementException (in which case it will be fine if you want to import it). 
 * TODO: Develop appropriate JUnit tests for your FourHeap.
 */
public class FourHeap<E> extends Heap<E> {
	
	public FourHeap(Comparator<? super E> c) {
		// TODO: To-be implemented. Replace dummy parameter to superclass constructor
	}

	@Override
	public void insert(E item) {
		// TODO Auto-generated method stub
	}

	@Override
	public E findMin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E deleteMin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
