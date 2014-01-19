package phaseB;
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
public class HashTable<E> extends DataCounter<E> {

	
	public HashTable(Comparator<? super E> c, Hasher<E> h) {
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
