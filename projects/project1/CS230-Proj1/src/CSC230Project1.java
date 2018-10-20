
public interface CSC230Project1<T> {
	
	public void insert( T t );
	public T remove();
	public long getTotalOperations();
	public void resetTotalOperations();
	
}