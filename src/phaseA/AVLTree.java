package phaseA;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * AVLTree must be subclass of BinarySearchTree<E> and must use inheritance 
 * and calls to superclass methods to avoid unnecessary duplication or copying
 * of functionality.
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override incCount method such that it creates AVLNode instances instead 
 *    of BSTNode instances.
 * 3. Do not "replace" the left and right fields in BSTNode with new left and 
 *    right fields in AVLNode. This will instead mask the super-class fields 
 *    (i.e., the resulting node would actually have four node fields, with 
 *    code accessing one pair or the other depending on the type of the
 *    references used to access the instance). Such masking will lead to
 *    highly perplexing and erroneous behavior. Instead, continue using the
 *    existing BSTNode left and right fields. Cast their values to AVLNode 
 *    whenever necessary in your AVLTree. Note: This may require many casts, 
 *    but that is o.k. given that our goal is to reuse methods from BinarySearchTree.
 * 4. Do not override dump method of BinarySearchTree & toString method of 
 * 	  DataCounter. They are used for grading. 
 * TODO: Develop appropriate JUnit tests for your AVLTree (TestAVLTree in 
 *    testA package).
 */
public class AVLTree<E> extends BinarySearchTree<E> {
	
	private static final int ALLOWED_IMBALANCE = 1;

	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	@Override
	public void incCount(E data) {
		if (overallRoot == null) {
            overallRoot = new AVLNode(data);
            return;
        }
		overallRoot = incCount(data, overallRoot);
	}
	
	private BSTNode incCount(E data, BSTNode node) {
    	if(node == null) {
    		return new AVLNode(data);
    	}
    	int cmp = comparator.compare(data, node.data);
    	if(cmp == 0) {
    		node.count++;
    		return node;
    	} else if(cmp < 0) {
    		node = incCount(data, node.left);
    	} else {
    		node = incCount(data, node.right);
    	}
    	return balance(node);
    }
	
	@SuppressWarnings("unchecked")
	private BSTNode balance(BSTNode node) {
		if(node == null) {
			return node;
		}
		if(height((AVLNode) node.left) - height((AVLNode) node.right) > ALLOWED_IMBALANCE) {
			if(height((AVLNode) node.left) >= height((AVLNode) node.right)) {
				node = rotateWithLeftChild((AVLNode) node);
			} else {
                node = doubleWithLeftChild((AVLNode) node);
			} 
		} else if( height((AVLNode) node.right) - height((AVLNode) node.left) > ALLOWED_IMBALANCE) {
            if( height((AVLNode) node.right.right) >= height((AVLNode) node.right.left)) {
            	node = rotateWithRightChild((AVLNode) node);
            } else {
            	node = doubleWithRightChild((AVLNode) node);
            }
		}
		((AVLNode) node).height = Math.max(height((AVLNode) node.left), height((AVLNode) node.right)) + 1;
		return node;
	}
	
	/**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    @SuppressWarnings("unchecked")
	private AVLNode rotateWithLeftChild(AVLNode k2) {
    	AVLNode k1 = (AVLNode) k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height((AVLNode) k2.left), height((AVLNode) k2.right)) + 1;
        k1.height = Math.max(height((AVLNode) k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    @SuppressWarnings("unchecked")
	private AVLNode rotateWithRightChild(AVLNode k1) {
    	AVLNode k2 = (AVLNode) k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height((AVLNode) k1.left), height((AVLNode) k1.right)) + 1;
        k2.height = Math.max(height((AVLNode) k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    @SuppressWarnings("unchecked")
	private AVLNode doubleWithLeftChild(AVLNode k3) {
        k3.left = rotateWithRightChild((AVLNode) k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    @SuppressWarnings("unchecked")
	private AVLNode doubleWithRightChild(AVLNode k1) {
        k1.right = rotateWithLeftChild((AVLNode) k1.right);
        return rotateWithRightChild(k1);
    }
	
	// To get correct heights
	private int height(AVLNode node) {
		if(node == null) {
			return -1;
		} else {
			return node.height;
		}
	}
	
	// TODO: To-be implemented
	private class AVLNode extends BSTNode {
		public int height;
		public AVLNode(E d) {
			super(d);
			height = 0;
		}
	}
}
