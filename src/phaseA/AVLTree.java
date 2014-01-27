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

/**
 **   Christopher Blappert
 **	  1/31/14
 **	  CSE 332 AB
 **	  Hye Kim
 **   Programming project 2a
 **
 **	  This class provides an implementation of a generic
 **   AVL tree that supports keeping counts of data values
 **   and retreiving these counts. 
 **/

public class AVLTree<E> extends BinarySearchTree<E> {
	private static final int ALLOWED_IMBALANCE = 1;

	// Pre: comparator passed not null
	// Post: initializes an empty 
	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	// Pre: comparator not null
	// Post: increments the count of the data if it is already in the tree
	// 		 otherwise adds it to the tree
	@Override
	public void incCount(E data) {
		if (overallRoot == null) {
            overallRoot = new AVLNode(data);
            return;
        }
		overallRoot = incCount(data, overallRoot);
	}
	
	// Private helper method to increase the count of the data starting from
	// a certain node
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
	
	// Private helper method to balance a node if it is necessary 
	@SuppressWarnings("unchecked")
	private BSTNode balance(BSTNode node) {
		if(node == null) {
			return node;
		}
		if(height((AVLNode) node.left) - height((AVLNode) node.right)  
					> ALLOWED_IMBALANCE) {
			if(height((AVLNode) node.left) >= height((AVLNode) node.right)) {
				node = rotateWithLeftChild((AVLNode) node);
			} else {
                node = doubleWithLeftChild((AVLNode) node);
			} 
		} else if(height((AVLNode) node.right) - height((AVLNode) node.left)
				> ALLOWED_IMBALANCE) {
            if(height((AVLNode) node.right.right) >= 
            			height((AVLNode) node.right.left)) {
            	node = rotateWithRightChild((AVLNode) node);
            } else {
            	node = doubleWithRightChild((AVLNode) node);
            }
		}
		((AVLNode) node).height = Math.max(height((AVLNode) node.left),
				height((AVLNode) node.right)) + 1;
		return node;
	}
	
	// Rotate AVLTreeNode with left child.
    // Update heights, then return new root.
    @SuppressWarnings("unchecked")
	private AVLNode rotateWithLeftChild(AVLNode node) {
    	AVLNode lChild = (AVLNode) node.left;
        node.left = lChild.right;
        lChild.right = node;
        node.height = Math.max(height((AVLNode) node.left), 
        		height((AVLNode) node.right)) + 1;
        lChild.height = Math.max(height((AVLNode) lChild.left), 
        		node.height) + 1;
        return lChild;
    }

    // Rotate AVLTreeNode with right child.
    // Update heights, then return new root.
    @SuppressWarnings("unchecked")
	private AVLNode rotateWithRightChild(AVLNode node) {
    	AVLNode rChild = (AVLNode) node.right;
        node.right = rChild.left;
        rChild.left = node;
        node.height = Math.max(height((AVLNode) node.left), 
        		height((AVLNode) node.right)) + 1;
        rChild.height = Math.max(height((AVLNode) rChild.right), 
        		node.height) + 1;
        return rChild;
    }

    // Double rotate binary tree node to fix Left-Right imbalance
    @SuppressWarnings("unchecked")
	private AVLNode doubleWithLeftChild(AVLNode node) {
        node.left = rotateWithRightChild((AVLNode) node.left);
        return rotateWithLeftChild(node);
    }

    // Double rotate binary tree node to fix Right-Left imbalance
    @SuppressWarnings("unchecked")
	private AVLNode doubleWithRightChild(AVLNode node) {
        node.right = rotateWithLeftChild((AVLNode) node.right);
        return rotateWithRightChild(node);
    }
	
    // Extra method to be used to test the form of an AVLTree
    // will test for correct height, correct node order and balance
    @SuppressWarnings("unchecked")
	public void testAVLTree() {
        testAVLTree((AVLNode) overallRoot);
    }

    // Private helper for determining if the AVLTree is correct
    @SuppressWarnings("unchecked")
	private void testAVLTree(AVLNode node) {
        if(node == null) {
            return;
        }
        if(height(node) != Math.max(height((AVLNode) node.right), 
        		height((AVLNode) node.left)) + 1) {
            throw new IllegalStateException();
        }
        if((node.left != null && comparator.compare(node.data, node.left.data)
        		< 0) || (node.right != null && 
        		comparator.compare(node.data, node.right.data) > 0)) {
            throw new IllegalStateException();
        }
        if(Math.abs(height((AVLNode) node.right)-height((AVLNode) node.left))
        		> ALLOWED_IMBALANCE) {
            throw new IllegalStateException();
        }
        testAVLTree((AVLNode) node.right);
        testAVLTree((AVLNode) node.left);
    }
    
	// To get correct heights including null values
	private int height(AVLNode node) {
		if(node == null) {
			return -1;
		} else {
			return node.height;
		}
	}
	
	// Nested class that adds a height field to the BSTNode
	// so it can be used in an AVLTree
	private class AVLNode extends BSTNode {
		public int height;
		
		public AVLNode(E d) {
			super(d);
			height = 0;
		}
	}
}
