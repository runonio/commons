

package io.runon.commons.math.calculator;

 class DequeStack implements Stack
{
	LinkedListDeque deque;

	/////////////////////////////////// Constructor

	public DequeStack() 
	// initialise stack
	{
		deque = new LinkedListDeque();
	}

	/////////////////////////////////// Mutator Methods

	public Object pop()
	// return the object on the top of the stack and remove it
	{
		return deque.removeLast();	
	}

	public void push(Object o)
	// put o on top of the stack
	{
		deque.insertLast(o);
	}

	/////////////////////////////////// Access Methods

	public int size()
	// return the number of objects on the stack
	{
		return deque.size();
	}

	public Object top()
	// return the object on top of the stack, but do not remove it
	{
		return deque.lastElement();
	}

	public boolean isEmpty()
	// returns TRUE if there is nothing on the stack
	{
		return deque.isEmpty();
	}
}