package providedCode;

/**
 * Simple class to hold a piece of data and its count. 
 * See also the return type of the getIterator method of DataCounter.
 * @param <E> type of data whose count we are recording.
 */
public class DataCount<E> {
    /**
     * The data element whose count we are recording.
     */
    public E data;

    /**
     * The count for the data element.
     */
    public int count;

    /**
     * Create a new data count.
     * @param data the data element whose count we are recording.
     * @param count the count for the data element.
     */
    public DataCount(E data, int count) {
        this.data = data;
        this.count = count;
    }
}
