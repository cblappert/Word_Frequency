1. Getting Started
DONE: GArrayStack: Needed for BinarySearchTree iterator. You may use your code from Project 1.
STARTED: StringComparator: Used by tboth data counters and sorting algorithms. Because of how the output must be sorted in the case of ties, your implementation should return a negative number when the first argument to compare alphabetically comes before the second argument. Do not use any String comparison provided in Java library; the only String methods you are allowed to use are length and charAt.
WordCount.getCountsArray: The provided code returns with an error. Your code should use the argument's iterator to retrieve all the elements and put them in a new array. The code you write is using ( not implementing) a SimpleIterator. If you have trouble with casting, take another look at these notes on generic arrays.

2. Adding Data Counter Implementations
Provide two additional implementations of DataCounter as described below. You should provide the methods defined in DataCounter. Follow the instructions & hints found inside of each file listed below. Then, adjust WordCount to use your new DataCounter implementations with the appropriate command-line arguments.
AVLTree: A self-balancing search tree, subclass of BinarySearchTree
MoveToFrontList: Type of linked list where new items are inserted at the front of the list, and an existing item gets moved to the front whenever it is referenced. Although it has O(n) worst-case time operations, it does well when some words are much more frequent than others, because frequent ones will be near the front.

3. Adding a Sorting Algorithm
Implement Sorter.heapSort and FourHeap as explained below. For the full credit, heapSort should be generic. You do not need to implement an in-place heapsort as was discussed in lecture. Then, adjust WordCount to use your heapSort method when the second command-line argument is -hs.
heapSort: Consists of two steps 1) Insert each element to be sorted into a heap (FourHeap) 2) Remove each element from the heap, storing them in order in the output array.
FourHeap: Heap with 4 children, subclass of Heap (Hint: Complete written homework #2 before attempting this). Follow the instructions & hints found inside of FourHeap.java and Heap.java.

4. JUnit Tests
Implement test files in package test and testA: TestDataCounter, TestSorter, TestAVLTree, TestMoveToFrontList and TestFourHeap. TestBinarySearchTree is already implemented as a simple example; Note that it is a subclass of TestDataCounter, which should be used to factor out the common testing codes of different data counter implementations (since all data counters should have the same external behavior). We expect your JUint tests to be more thorough than TestBinarySearchTree, testing both internal and external test both internal and external functionalities. To test internal functionality, consider writing a public method for test in the class being tested and call that method in JUnit test & compare with expected output. You do not need to test the functionality you did not implement.