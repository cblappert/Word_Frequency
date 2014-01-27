/**
 **   Christopher Blappert
 **	  1/13/14
 **	  CSE 332 AB
 **	  Hye Kim
 **   Programming project 2a
 **
 **	  This class provides a resizeable array type implementation
 **   of a basic stack with only push, pop, peek, isEmpty functions.
 **   This stack is generic.
 **/

package phaseA;
import providedCode.*;
import java.util.EmptyStackException;

public class GArrayStack<T> implements GStack<T> {
	private static final int DEFAULT_SIZE = 10;
	private static final int RESIZE_FACTOR = 2;
	private T[] data;
	private int end;
	
	// Post: Initializes a new stack with a default size of 10.
	@SuppressWarnings("unchecked")
	public GArrayStack() {
		data = (T[]) new Object[DEFAULT_SIZE];
		end = 0;
	}
	
	// Post: Returns a boolean representing whether or not the stack 
	//       is empty
	@Override
	public boolean isEmpty() {
		return end == 0;
	}

	// Post: Pushes the specified value on the top of the stack
	@Override
	public void push(T d) {
		if(end == data.length) {
			resize();
		}
		data[end] = d;
		end++;
	}
	
	// Post: Doubles the size of the array that stores the data of the stack
	@SuppressWarnings("unchecked")
	private void resize() {
		T[] oldDataArray = data;
		
		data = (T[]) new Object[data.length * RESIZE_FACTOR];
		for(int i = 0; i < end; i++) {
			data[i] = oldDataArray[i];
		}
	}
	
	// Pre: Stack is not empty, otherwise throws EmptyStackException
	// Post: Returns the most recently pushed value and removes it 
	//		 from the stack
	@Override
	public T pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		end--;
		return data[end]; 
	}

	// Pre: Stack is not empty, otherwise throws EmptyStackException
	// Post: Returns the most recently pushed value
	@Override
	public T peek() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		return data[end - 1];
	}

}
