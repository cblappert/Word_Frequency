package providedCode;
import phaseA.*;


/**
 * BinarySearchTree implements the DataCounter interface using a binary search tree.  
 * The constructor takes a Comparator<? super E> "function object" so that items of type E can be compared.  
 * Each tree node associates a count with an E.
 */
public class BinarySearchTree<E> extends DataCounter<E> {
    protected BSTNode overallRoot;               // The root of the BST. Root is null if and only if the tree is empty.
    protected int size;                          // Number of nodes in the binary search tree.
    protected Comparator<? super E> comparator;  // Function object to compare elements of type E, passed in constructor.
    

    /**
     * Create an empty binary search tree.
     */
    public BinarySearchTree(Comparator<? super E> c) {
        overallRoot = null;
        size = 0;
        comparator = c;
    }
    

    /** {@inheritDoc} */
    public void incCount(E data) {
        if (overallRoot == null) {
            overallRoot = new BSTNode(data);
            return;
        }
        
        BSTNode currentNode = overallRoot;
        while (true) {
        	// compare the new data with the data at the current node
        	int cmp = comparator.compare(data, currentNode.data);
        	if(cmp == 0) {            // a. Current node is a match
        		currentNode.count++;
        		return;
            }else if(cmp < 0) {       // b. Data goes left of current node
            	if(currentNode.left == null) {
            		currentNode.left = new BSTNode(data);
            		return;
                }	
            	currentNode = currentNode.left;
            }else{                    // c. Data goes right of current node
            	if(currentNode.right == null) {
            		currentNode.right = new BSTNode(data);
            		return;
                }
                currentNode = currentNode.right;
            }
        }
    }
    
    /** {@inheritDoc} */
    public int getCount(E data) {
    	BSTNode n = overallRoot;
    	while(n != null) {
    		int cmp = comparator.compare(data, n.data);
    		if(cmp < 0) {
    			n = n.left;
    		}else if(cmp > 0) {
    			n = n.right;
    		}else{
    			return n.count;
    		}
    	}
    	return 0;
    }
    
    /** {@inheritDoc} */
    public int getSize() {
        return size;
    }
    
    /** {@inheritDoc} */
    public SimpleIterator<DataCount<E>> getIterator() {
    	// Anonymous inner class that keeps a stack of yet-to-be-processed nodes
    	return new SimpleIterator<DataCount<E>>() {  
    		GStack<BSTNode> stack = new GArrayStack<BSTNode>(); 
    		{
    			if(overallRoot != null) { stack.push(overallRoot); }
    		}
    		public boolean hasNext() {
        		return !stack.isEmpty();
        	}
        	public DataCount<E> next() {
        		if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
        		}
        		BSTNode nextNode = stack.pop();
        		if(nextNode.left != null) {
        			stack.push(nextNode.left);
        		}
        		if(nextNode.right != null) {
        			stack.push(nextNode.right);
        		}
        		return new DataCount<E>(nextNode.data, nextNode.count);
        	}
    	};
    }
    
    
    
	/** Private Class =======================================================================================**/

    /**
     * Inner class to represent a node in the tree. Each node includes a data of type E 
     * and an integer count. The class is protected so that subclasses of BinarySearchTree can access it.
     */
    protected class BSTNode {
        public BSTNode left;    // The left child of this node.
        public BSTNode right;   // The right child of this node.
        public E data;          // The data element stored at this node.
        public int count;       // The count for this data element.

        /**
         * Create a new data node and increment the enclosing tree's size.
         * @param data data element to be stored at this node.
         */
        public BSTNode(E d) {
            data  = d;
            count = 1;
            left  = null;
            right = null;
            size++;
        }
    }
}
