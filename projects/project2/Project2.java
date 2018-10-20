import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
*
* <p>Title: Data Structures Project 2: "Job Scheduling"</p>
* <p>Description: This Program reads input from a .txt file by line creates a task object with that data. The program
* then adds those tasks to a sorted linked list and simulates a cpu with cycles to run each task by cycle and priority.
* </p>
* <p>Due 1 Nov 22 11:59 pm</p>
* @author Damian Diaz (N00821283@students.ncc.edu)
* 
**/

class Task{

	//*****************************INSTANCE VARIABLES
	private int cycle;
	private int priority;
	private int cycle_amt;
	private String description;
	private int cyclesLeft;

	private Task next;

	public Task(int _cycle, int _priority, int _cycle_amt, String _description) {

		cycle = _cycle;
		priority = _priority;
		cycle_amt = _cycle_amt;
		description = _description;
		cyclesLeft = cycle_amt;

	}

	//*****************************ACCESSOR METHODS
	public int getCycle() {return cycle;}
	public int getPriority() {return priority;}
	public int getCycleAmt() {return cycle_amt;}
	public String getDescription() {return description;}
	public int getCyclesLeft() {return cyclesLeft;}
	public Task getNext() {return next;}
	
	//*****************************MUTATOR METHODS
	public void setNext(Task node) {next = node;};

	/**
	 * runCycle method
	 * 
	 * @description decrements the "cyclesLeft" count and returns the current state
	 * of the task.
	 *
	 */
	public String runCycle() {

		cyclesLeft--;
		return "["+priority+"] " + description + " (" + cyclesLeft + " cycles left)";

	}

}

class TaskList{

	//*****************************INSTANCE VARIABLES
	private Task first;
	private int count;

	public TaskList() {
		first = null;
		count = 0;
	}

	/**
	 * addTask method
	 * 
	 * @description adds a task object to the sorted linked list.
	 * 
	 * THIS METHOD SHOULD ONLY BE CALLED WHILE SORTING BY CYCLE
	 *
	 */
	public void addTask(Task node) {

		Task newTask = node;

		if(first == null) 
			first = newTask;

		else if (newTask.getCycle() < first.getCycle()) {
			newTask.setNext(first);
			first = newTask;
		}
		else {

			Task after = first.getNext();
			Task before = first;

			while(after != null) {

				if(newTask.getCycle() < after.getCycle()) 
					break;
				before = after;
				after = after.getNext();

			}

			newTask.setNext(before.getNext());
			before.setNext(newTask);
		}
		count++;
	}

	/**
	 * addTaskByPriority method
	 * 
	 * @description adds a task object to the sorted linked list.
	 * 
	 * THIS METHOD SHOULD ONLY BE CALLED WHILE SORTING BY PRIORITY
	 *
	 */
	public void addTaskByPriority(Task node) {

		Task newTask = node;

		if(first == null) 
			first = newTask;

		else if (newTask.getPriority() < first.getPriority()) {
			newTask.setNext(first);
			first = newTask;
		}
		else {

			Task after = first.getNext();
			Task before = first;

			while(after != null) {

				if(newTask.getPriority() < after.getPriority())
					break;
				before = after;
				after = after.getNext();

			}

			newTask.setNext(before.getNext());
			before.setNext(newTask);
		}
		count++;
	}
	
	/**
	 * removeFirst method
	 * 
	 * @description removes first task in the list.
	 * @return Task removed.
	 */
	public Task removeFirst() {

		Task temp = first;
		first = first.getNext();
		count --;
		temp.setNext(null);
		return temp;

	}
	
	//*****************************ACCESSOR METHODS
	public boolean isEmpty() {return count == 0;}
	public Task peek() {return first;}
	public int getCount() {return count;}

	/**
	 * toString method
	 * 
	 * @return the state of the Sorted Linked List
	 *
	 */
	public String toString()
	{
		String str = "Task List:\n";

		Task n = first;

		while (n != null)
		{
			str += n.toString() +"\n";
			n = n.getNext();
		}

		return str;
	}
}


class CPU{
	
	//*****************************INSTANCE VARIABLES
	private TaskList listByTime;
	private TaskList listByPriority;
	private int count;

	public CPU(TaskList _list) {
		listByTime = _list;
		listByPriority = new TaskList();
		count = 0;
	}
	
	/**
	 * runCPU method
	 * @description as long as both listByTime and listByPriority contain values, this method will
	 * continously add, run, and remove programs by cycle.
	 * 
	 */
	public void runCPU() {


		while( !listByTime.isEmpty() || !listByPriority.isEmpty() ) {

			System.out.println("Time: " + count + " ," + listByPriority.getCount() + " jobs to run.");

			while(!listByTime.isEmpty() && listByTime.peek().getCycle() == count) {

				Task temp = listByTime.removeFirst();

				listByPriority.addTaskByPriority(temp);
				System.out.println("*ADDED* " + temp.getDescription() + " ["+temp.getPriority()+"]");

			}

			if(!listByPriority.isEmpty()) {
				if(listByPriority.peek().getCyclesLeft() == 0) {
					System.out.println("***DONE***: " + listByPriority.peek().getDescription());
					listByPriority.removeFirst();
				}
				else if(listByPriority.peek().getCyclesLeft() > 0) {

					System.out.println(listByPriority.peek().runCycle());

					if(listByPriority.peek().getCyclesLeft() == 0) {
						System.out.println("***DONE***: " + listByPriority.peek().getDescription());
						listByPriority.removeFirst();
					}
				}
			}

			System.out.println("-------------------");
			count++;

		}
	}
}

public class Project2 {
	
	public static void main(String args[]) {
		
		TaskList list = new TaskList();

		try {
			
			File file = new File("input.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				
				String arg[] = line.split(" ");
				String description = "";
				
				for(int i = 3; i < arg.length; i++) {
					description += (arg[i]);
					if(i != arg.length-1)
						description += " ";
				}
				
				int cycle = Integer.parseInt(arg[0]);
				int priority = Integer.parseInt(arg[1]);
				int cyclesLeft = Integer.parseInt(arg[2]);
				String des = description;
				
				list.addTask(new Task(cycle,priority,cyclesLeft,des));

			}
			
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		CPU cpu = new CPU(list);
		cpu.runCPU();

	}
}