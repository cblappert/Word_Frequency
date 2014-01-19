package providedCode;
import java.util.Arrays;


/** 
 * Interface for a Heap (priority queue) that your FourHeap should implement
 */
public abstract class Heap<E> {
	protected E[] heapArray;
	protected int size;
	
	public abstract boolean isEmpty();
	public abstract void insert(E item);
	public abstract E findMin();
	public abstract E deleteMin();
	
	/** Used for grading **/
	public E[] getInternalArray() {
		return Arrays.copyOf(heapArray, size);
	}
}
