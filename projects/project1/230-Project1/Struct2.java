/**
 * <p>Title: CSC230 Project 1: "Comparing Algorithims"</p>
 * 
 * <p>Description: Struct2.java: Binary Search Tree structure using a linked structure
 * 
 ****************************************************************************************
 *    Title: BST.java, source code
 *    Author: http://algorithms.tutorialhorizon.com/binary-search-tree-complete-implementation/
 *    Date: 2014
 ***************************************************************************************
 */
public class Struct2<T extends Comparable<T>> implements CSC230Project1<T>{

	public Node<T> root;
	private int primitiveOperations;

	/**
	 * Default Constructor
	 */
	public Struct2(){
		this.root = null; //+1 assignment
		primitiveOperations += 1;
	}

	/**
	 * find method
	 * @param id data to be searched for
	 * @return boolean 
	 */
	public boolean find(T id){ 

		Node<T> current = root; 
		primitiveOperations += 1; //+1 assignment

		while(current!=null){ 

			primitiveOperations += 1; //for while loop
		
			if(current.data.compareTo(id) == 0){ 
				primitiveOperations += 2; //+2 for if and return method
				return true;

			}else if(current.data.compareTo(id) > 0){ 
				current = current.left; 
				primitiveOperations += 3; //+3 for two if and else if and assignment
			}else{
				current = current.right; 
				primitiveOperations += 4; //+4 for two if's and else for if and assignment
			}
		}

		primitiveOperations += 1; //+1 for return
		return false; 

	}

	/**
	 * delete method
	 * @param id data to be removed from tree
	 * @return boolean wheter deletion was completed
	 */
	public boolean delete(T id){
		
		Node<T> parent = root;
		Node<T> current = root;
		boolean isLeftChild = false;
		primitiveOperations += 3; //+3 for assignments
		
		while(current.data.compareTo(id)!=0){ 
		
			parent = current;
			primitiveOperations += 2; //+2 for while loop and assignment
			
			if(current.data.compareTo(id)>0){
				isLeftChild = true;
				current = current.left;
				primitiveOperations += 3; //+3 for assignments and if
			}else{
				isLeftChild = false;
				current = current.right;
				primitiveOperations += 4; //+4 for assignments and if-else
			}
			
			if(current == null){
				primitiveOperations += 2; //+2 for if and return
				return false;
			}
			
			primitiveOperations += 1; //+1 for if
		}
		
		if(current.left==null && current.right==null){
			
			primitiveOperations += 2; //+2 if statement with two conditions
			
			if(current==root){
				primitiveOperations += 1; //+1 assignment
				root = null;
			}
			
			primitiveOperations += 1; //+1 if statement
			
			if(isLeftChild ==true){
				primitiveOperations += 2; //+2 if and assignment
				parent.left = null;
			}else{
				primitiveOperations += 3; //+3 if and else and assignment
				parent.right = null;
			}
		}
		
		//Case 2 : if node to be deleted has only one child
		else if(current.right==null){
			
			primitiveOperations += 2; // + 2 if and else if
			
			if(current==root){
				primitiveOperations += 2; //+2 if and assignment
				root = current.left;
			}else if(isLeftChild){
				primitiveOperations += 3; //+2 if and else if and assignment
				parent.left = current.left;
			}else{
				primitiveOperations += 4; //+4 if and else if and else and assignment
				parent.right = current.left;
			}
			
		}
		else if(current.left==null){
			
			primitiveOperations += 3; //+3 if and else if and else if 
			
			if(current==root){
				primitiveOperations += 2; //+2 if and assignment
				root = current.right;
			}else if(isLeftChild){
				primitiveOperations += 3; //+3 if and else if and assignment
				parent.left = current.right;
			}else{
				primitiveOperations += 4; //+4 if and else if and else and assignment
				parent.right = current.right;
			}
		}else if(current.left!=null && current.right!=null){
			
			primitiveOperations += 4; //+4 if and else if and else if and else if and assignment

			Node<T> successor	 = getSuccessor(current);
			primitiveOperations += 1; //+1 assignment
			
			
			if(current==root){
				primitiveOperations += 2; //+2 if and assignment
				root = successor;
			}else if(isLeftChild){
				primitiveOperations += 3; //+3 if and else if and assignment
				parent.left = successor;
			}else{
				primitiveOperations += 4; //+4 if and else if and else and assignment
				parent.right = successor;
			}
			
			primitiveOperations += 1; //+1 assignment
			successor.left = current.left;
		}		
		
		primitiveOperations += 1; //+1 return statement
		return true;		
	}

	/**
	 * getSuccessor method
	 * @param deleleNode deleted node
	 * @return Node
	 */
	public Node<T> getSuccessor(Node<T> deleleNode){
		
		Node<T> successsor =null;
		Node<T> successsorParent =null;
		Node<T> current = deleleNode.right;
		primitiveOperations += 3; //+3 assignment statements
		
		while(current!=null){
			
			successsorParent = successsor;
			successsor = current;
			current = current.left;
			primitiveOperations += 4; //+4 while loop and assignments
		}
		
		primitiveOperations += 1; //+1 while loop
		
		if(successsor!=deleleNode.right){
			successsorParent.left = successsor.right;
			successsor.right = deleleNode.right;
			primitiveOperations += 2; //+2 assignments
		}
		primitiveOperations += 1; //+1 if statement
		
		primitiveOperations += 1; //+1 return statement
		return successsor;
	}

	/** 
	 * insert method
	 * @param id data to be inserted
	 */
	public void insert1(T id){
		
		primitiveOperations += 1; //+1 assignment
		Node<T> newNode = new Node<T>(id);
		
		if(root==null){
			root = newNode;
			primitiveOperations += 3; //+3 if and assignment and return
			return;
		}
		
		primitiveOperations += 1; //+1 if statement
		
		primitiveOperations += 2; //+2 assignments
		Node<T> current = root;
		Node<T> parent = null;
		
		while(true){
			
			primitiveOperations += 2; //+2 while and assignment
			parent = current;
			
			if(current.data.compareTo(id)>0){	
				
				primitiveOperations += 2; //+2 if and assignment
				current = current.left;
				
				if(current==null){
					primitiveOperations += 3; //+3 if and assignment and return
					parent.left = newNode;
					return;
				}
				primitiveOperations += 1; //+1 if statement
				
			}else{
				
				primitiveOperations += 3; //+3 if and else and assignment
				current = current.right;
				
				if(current==null){
					primitiveOperations += 3; //+3 if and assignment and return
					parent.right = newNode;
					return;
				}
				
				primitiveOperations += 1; //+1 if statement
			}
		}
	}

	public void display(Node<T> root){
		
		if(root!=null){
			
			display(root.left);
			System.out.print(" " + root.data);
			display(root.right);
			primitiveOperations += 4; //+4 if and assignments
			
		}
		primitiveOperations += 1; //+1 if statement
	}

	@Override
	public void insert(T t) {
		insert1(t);
		primitiveOperations += 1; //+1 method call
	}

	@Override
	public T remove() {
		
		T removeData = root.data;
		primitiveOperations += 1; //+1 assignment
		
		if(delete(root.data)) {
			primitiveOperations += 2;//+2 if and return
			return removeData;
		}
		
		primitiveOperations += 2; //if and return
		return null;
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

class Node<T extends Comparable<T>>{
	T data;
	Node<T> left;
	Node<T> right;	
	public Node(T data){
		this.data = data;
		left = null;
		right = null;
	}
}