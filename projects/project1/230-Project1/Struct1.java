/**
 * <p>Title: CSC230 Project 1: "Comparing Algorithims"</p>
 * 
 * <p>Description: Struct1.java: Queue structure using array based implementation.
 * @author Damian Diaz (N00821283@students.ncc.edu)
 *
 */
public class Struct1<T> implements CSC230Project1<T> {

	private int capacity = 100; //initial size of the array
	
	@SuppressWarnings("unchecked")
	T[] arr = (T[]) new Object[capacity]; //Generic array

	private int front = -1; //index for front of queue
	private int rear = 0; //index for rear of queue
	private int size ; //amount of data currently occupying array
	private int primitiveOperations; //primitive operation count

	/**
	 * Default constructor
	 */
	public Struct1() {
		size = 0;
		primitiveOperations +=1; //+1 for assignment
	}

	/**
	 * Adds the item to this queue.
	 *
	 * @param  t the item to add
	 */
	public void enqueue(T t) {

		if(front >= capacity - 1) { 

			capacity = capacity * 2;
			
			@SuppressWarnings("unchecked")
			T[] newArray = (T[]) new Object[capacity];

			for(int i = 0; i < arr.length; i ++) {
				primitiveOperations += 2; //for loop and assignment
				newArray[i] = arr[i];
			}
			arr = newArray;
			
			primitiveOperations += 4; //+4 assignments and foor loop 
		}

		front++;
		arr[front] = t;
		size++;
		
		primitiveOperations += 4; //if statement and 3 assignments

	}

	/**
	 * Removes and returns the item on this queue that was least recently added.
	 *
	 * @return the item on this queue that was least recently added
	 */
	public T dequeue() {

		T oldTail = arr[rear];
		primitiveOperations += 1; //+1 assignment

		if(front >= rear) {
			rear ++;
			size --;
			primitiveOperations += 4; //+4 if and two assignments and return
			return oldTail;
		}
		return null;

	}

	@Override
	public void insert(T t) {
		primitiveOperations += 1; //+1 method call
		enqueue(t);
	}

	@Override
	public T remove() {
		primitiveOperations += 1; //+1 method call
		return dequeue();
	}

	@Override
	public long getTotalOperations() {

		return primitiveOperations;
	}

	@Override
	public void resetTotalOperations() {
		primitiveOperations = 0;

	}
}