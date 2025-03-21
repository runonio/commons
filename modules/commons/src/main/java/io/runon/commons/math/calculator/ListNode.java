
package io.runon.commons.math.calculator;

 class ListNode
{
	private Object thisNode;
	private ListNode next,previous;

	/////////////////////////////////// Constructors

	public ListNode()
	{
		this(null,null,null);
	}

	public ListNode(Object item, ListNode newNext, ListNode newPrev)
	{
		thisNode = item;  // the object that the node points to
		next = newNext;	  // the next node		
		previous = newPrev; // the preceding node
	} 

	/////////////////////////////////// Mutator Methods

	public void setNext(ListNode newNext)
	// specify which node follows the current one
	{
		next = newNext;
	}

	public void setPrev(ListNode newPrev)
	// specify which node precedes the current one
	{
		previous = newPrev;
	}

	public void setElement(Object item)
	// specify the object to be pointed to by the node
	{
		thisNode = item;
	}

	/////////////////////////////////// Access Methods

	public Object getElement()
	// returns the object pointed to by the node
	{
		return thisNode;
	}

	public ListNode getNext()
	// returns the node which follows this one
	{
		return next;
	}

	public ListNode getPrev()
	// returns the node which preceeds this one
	{
		return previous;
	}

}