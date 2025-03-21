
package io.runon.commons.math.calculator;

import io.runon.commons.exception.EmptyException;

/**
 * @author macle
 */
 class LinkedListDeque implements Deque {
	private int dequeSize;
	private ListNode head, tail;

	/////////////////////////////////// Constructor

	public LinkedListDeque()
	{
		head = new ListNode(null,null,null);
		tail = new ListNode(null,null,head); // head is prev node
		head.setNext(tail);
		dequeSize = 0;
	}

	/////////////////////////////////// Mutator Methods

	public void insertFirst(Object o)
	// places a new ListNode containing o between the head sentinel
	// and the node that the head was originally linked to.
	{
		ListNode newNode = new ListNode(o,head.getNext(),head);
		head.getNext().setPrev(newNode);
		head.setNext(newNode);
		dequeSize++;
	}

	public void insertLast(Object o)
	// places a new ListNode containing o between the tail sentinel
	// and the node that the tail was originally linked to.
	{
		ListNode newNode = new ListNode(o,tail,tail.getPrev());
		tail.getPrev().setNext(newNode);
		tail.setPrev(newNode);
		dequeSize++;
	}

	public Object removeFirst()
	// returns the object in the node linked to the head of the list,
	// and removes that node.
	{
		if (isEmpty())
			throw new EmptyException();
		ListNode node = head.getNext();
		head.setNext(node.getNext());
		node.getNext().setPrev(head);
		dequeSize--;
		return node.getElement();
	}

	public Object removeLast()
	// returns the object in the node linked to the tail of the list,
	// and removes that node.
	{
		if (isEmpty())
			throw new EmptyException();
		ListNode node = tail.getPrev();
		tail.setPrev(node.getPrev());
		node.getPrev().setNext(head);
		dequeSize--;
		return node.getElement();
	}

	public Object firstElement()
	// returns the object in the node linked to the head of the list
	{
		if (isEmpty())
			throw new EmptyException();
		return head.getNext().getElement();
	}

	public Object lastElement()
	// returns the object in the node linked to the tail of the list
	{
		if (isEmpty())
			throw new EmptyException();
		return tail.getPrev().getElement();
	}

	/////////////////////////////////// Access Methods

	public boolean isEmpty()
	// returns true if there are no nodes between the head and tail
	{
		return (dequeSize == 0);
	}

	public int size()
	// returns the number of nodes between the head and the tail
	{
		return dequeSize;
	}
}