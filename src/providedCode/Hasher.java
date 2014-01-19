package providedCode;

/** 
 * interface for function objects that hash data
 */
public interface Hasher<E> {
	public int hash(E e);
}
