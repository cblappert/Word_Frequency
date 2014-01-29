package phaseA;
import providedCode.*;

/**
 **   Christopher Blappert, Michael Mitasev
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
	
	// Post: returns a balanced version of the node passed in
	//       if it is balanced to begin with, returns th enode
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
	
	// Post: rotate AVLTreeNode with left child,
    //       update heights, then return new root.
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

    // Post: Rotate AVLTreeNode with right child,
    //       update heights, then return new root.
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

    // Post: double rotate binary tree node to fix left-right imbalance
    @SuppressWarnings("unchecked")
	private AVLNode doubleWithLeftChild(AVLNode node) {
        node.left = rotateWithRightChild((AVLNode) node.left);
        return rotateWithLeftChild(node);
    }

    // Post: double rotate binary tree node to fix right-left imbalance
    @SuppressWarnings("unchecked")
	private AVLNode doubleWithRightChild(AVLNode node) {
        node.right = rotateWithLeftChild((AVLNode) node.right);
        return rotateWithRightChild(node);
    }
    
	// Post: returns the height of a node, or -1 if the node passed is null
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
		
		// Post: creates a new AVLNode with default height 0
		public AVLNode(E d) {
			super(d);
			height = 0;
		}
	}
	
	// --------------------------------------------------------------
	// Below are methods used for testing of the AVLTree which are 
	// not part of the assignment prompt
	// --------------------------------------------------------------
	
	// Extra method to be used to test the form of an AVLTree
    // will test for correct height, correct BST order and balance
    @SuppressWarnings("unchecked")
	public void testAVLTree() {
    	if(overallRoot != null) {
    		E min = findMin(overallRoot);
        	E max = findMax(overallRoot);
    		testAVLTree((AVLNode) overallRoot, min, max);
    	}
    }

    // Method that finds the minimum value in the tree
    public E findMin(BSTNode node) {
    	E min = node.data;
    	if(node.right != null) {
    		E temp = findMin(node.right);
    		if(comparator.compare(temp, min) < 0) {
    			min = temp;
    		}
    	} 
    	if(node.left != null) {
    		E temp = findMin(node.left);
    		if(comparator.compare(temp, min) < 0) {
    			min = temp;
    		}
    	}
    	return min;
    }
    
    // Method that finds the maximum value in the tree
    public E findMax(BSTNode node) {
    	E max = node.data;
    	if(node.right != null) {
    		E temp = findMax(node.right);
    		if(comparator.compare(temp, max) > 0) {
    			max = temp;
    		}
    	} 
    	if(node.left != null) {
    		E temp = findMax(node.left);
    		if(comparator.compare(temp, max) > 0) {
    			max = temp;
    		}
    	}
    	return max;
    }
    
    // Private helper for determining if the AVLTree is correct
    // Takes a node, and the max and min bounds for the possible
    // data values in that node according to BST form restrictions
    @SuppressWarnings("unchecked")
	private void testAVLTree(AVLNode node, E min, E max) {
        if(node == null) {
            return;
        }
        if(height(node) != Math.max(height((AVLNode) node.right), 
        		height((AVLNode) node.left)) + 1) {
            throw new IllegalStateException();
        }
        if(comparator.compare(node.data, max) > 0 || 
        		comparator.compare(node.data, min) < 0) {
            throw new IllegalStateException();
        }
        if(Math.abs(height((AVLNode) node.right)-height((AVLNode) node.left))
        		> ALLOWED_IMBALANCE) {
            throw new IllegalStateException();
        }
        testAVLTree((AVLNode) node.right, node.data, max);
        testAVLTree((AVLNode) node.left, min, node.data);
    }
}
