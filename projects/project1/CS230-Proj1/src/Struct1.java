import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 ****************************************************************************************
 *    Title: Queue.java, source code from "Algorithms, 4th Edition"
 *    Author: Robert Sedgewick and Kevin Wayne
 *    Date: 2011
 *    Availability: http://algs4.cs.princeton.edu/home/
 *
 ***************************************************************************************
 *primitive calls include, 
 *assignment, method call, arithmetic, comparison, indexing into an array, following an object
	reference, and returning from a method
 */
public class Struct1<T> implements CSC230Project1<Object> {

	private Node<T> first;    // beginning of queue
	private Node<T> last;     // end of queue
	private int n;               // number of elements on queue
	
	private int primitiveOperations;


	private static class Node<Item> {
		private Item item; 
		private Node<Item> next;
	}

	/**
	 * Initializes an empty queue.
	 */
	public Struct1() {
		first = null; //<-- 1
		last  = null;//<-- 1
		n = 0; //<-- 1
		primitiveOperations += 3;
	}

	/**
	 * Returns true if this queue is empty.
	 *
	 * @return {@code true} if this queue is empty; {@code false} otherwise
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Returns the number of items in this queue.
	 *
	 * @return the number of items in this queue
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns the item least recently added to this queue.
	 *
	 * @return the item least recently added to this queue
	 * @throws NoSuchElementException if this queue is empty
	 */
	public T peek() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		return first.item;
	}

	/**
	 * Adds the item to this queue.
	 *
	 * @param  item the item to add
	 */
	public void enqueue(T item) {
		
		Node<T> oldlast = last;
		
		last = new Node<T>(); 
		last.item = item; 
		last.next = null;
		
		if (isEmpty())
			first = last;
		else           
			oldlast.next = last;
		n++;
	}

	/**
	 * Removes and returns the item on this queue that was least recently added.
	 *
	 * @return the item on this queue that was least recently added
	 * @throws NoSuchElementException if this queue is empty
	 */
	public T dequeue() {
		
		if (isEmpty()) 
			throw new NoSuchElementException("Queue underflow");
		
		T item = first.item;
		first = first.next;
		n--;
		
		if (isEmpty()) 
			last = null; 
		
		return item;
	}


	@Override
	public void insert(Object t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalOperations() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetTotalOperations() {
		// TODO Auto-generated method stub

	}
}