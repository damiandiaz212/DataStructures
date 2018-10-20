import java.util.Random;

/**
* <p>Title: CSC230 Project3: "Network Monitoring"</p>
*
* <p>Description: A system of computers making up a network using an adjancency matrix system.</p>
*
* <p>Due 12/18/17 9:30 am</p>
*
* @author Damian Diaz (N00821283@students.ncc.edu)
*/
public class CSC230Network {

	public static void main (String args[]) {


		//TASK 1 - 65
		System.out.println("\n================ TASK 1  ================");
		ADJMatrix networkDefault = new ADJMatrix();

		//TASK 2 - 70
		System.out.println("\n================ TASK 2  ================");

		randomConnectionCost(networkDefault);
		randomConnectionActive(networkDefault);

		System.out.println(networkDefault.toString());

		//TASK 3 - 80
		System.out.println("\n================ TASK 3  ================");
		int numOfComputers = 5;

		ADJMatrix network = new ADJMatrix(numOfComputers);
		randomConnectionCost(network);
		randomConnectionActive(network);

		System.out.println(network.toString());

		System.out.println("Paths of nodes and status:\n(Checks if there is a direct connection first, if none finds path)\n");
		network.getPath(1, 4);
		network.getPath(2, 0);
		network.getPath(3, 2);

		//TASK 4 - 85
		System.out.println("\n================ TASK 4  ================");

		System.out.println("Toggling 5 connections:\n");
		toggleRandomConnections(network, numOfComputers, 5);

		System.out.println("\nPaths of nodes and status:\n(Checks if there is a direct connection first, if none finds path)\n");
		network.getPath(1, 4);
		network.getPath(2, 0);
		network.getPath(3, 2);

		//TASK 5 - 90
		System.out.println("\n================ TASK 5  ================");
		
		System.out.println("Any Path: ");
		network.getPath(0, 2);
		System.out.println("\nShortest Path: ");
		network.shortestPath(0, 2);

		//TASK 5 - 90
		System.out.println("\n================ TASK 6 ================");
		System.out.println("Unreachable nodes for 192.168.1.4: \n" + network.unreachableNodes(4)) ;


		//TASK 6 - 110
		System.out.println("\n================ TASK 7  ================");
		System.out.println("Minimum Spanning Tree:");
		System.out.println(network.getMST());


	}

	private static void randomConnectionCost(ADJMatrix network){

		int amt = network.getAmt();
		Random rand = new Random();

		for(int i = 0; i < amt; i++){
			for(int j = 0; j < amt; j++){

				int latency = rand.nextInt(99) + 1;
				network.setEdgeWeight(i, j, latency);

			}
		}

	}

	private static void randomConnectionActive(ADJMatrix network){

		int amt = network.getAmt();
		Random rand = new Random();

		for(int i = 0; i < amt; i++){
			for(int j = 0; j < amt; j++){

				int chance = rand.nextInt(2);

				if(chance == 0)
					network.setEdgeActive(i,j,true);
			}
		}

	}

	private static void toggleRandomConnections(ADJMatrix network, int numOfComputers, int amt){

		String ipString = "192.168.1.";
		int count = amt;
		Random rand = new Random();

		while(count > 0){

			//Generate Random vertices for edge
			int com1 = rand.nextInt(numOfComputers);
			int com2 = rand.nextInt(numOfComputers);

			//Ensure connection isn't between the same computer
			while(com2 == com1)
				com2 = rand.nextInt(numOfComputers);

			network.toggleEdgeActive(com1, com2);
			count -- ;

			System.out.println("Toggled connection: " + ipString + com1 + " <-> " + ipString + com2);
		}
	}
}

class ADJMatrix{

	//connections is a 2D array representing the adjacency matrix, 
	//each index can store an integer that represents an edge and weight 
	//Vertices represents amount of nodes inside the adj matrix
	private Edge edges[][];
	private int vertices;

	/**
	 * Default constructor
	 */
	public ADJMatrix() {

		vertices = 5;
		edges = new Edge[vertices][vertices];

		for(int i = 0; i < vertices; i++){
			for(int j = 0; j < vertices; j++){

				//By default latency is set to 50, even while inactive
				edges[i][j] = new Edge(i, j, 50, false);
			}
		}
		
		System.out.println("Default Network Created with: 5 computers connected.");

	}

	/**
	 * Parameterized Contsructor
	 * @param initialCount
	 */
	public ADJMatrix(int initialCount){

		if(initialCount >= 2 && initialCount <= 256){

			vertices = initialCount;
			edges = new Edge[vertices][vertices];

			for(int i = 0; i < vertices; i++){
				for(int j = 0; j < vertices; j++){

					//By default latency is set to 50, even while inactive
					edges[i][j] = new Edge(i, j, 50, false);
				}
			}
		}
		System.out.println("Parameterized Network Created with: " + initialCount + " computers connected.");
	}

	/**
	 * setEdgeWeight method
	 * assigns a integer value representing a weight to an edge 
	 * @param vert1
	 * @param vert2
	 * @param weight
	 */
	public void setEdgeWeight(int vert1, int vert2, int weight){

		if(withinBounds(vert1, vert2)){

			edges[vert1][vert2].setWeight(weight);
			edges[vert2][vert1].setWeight(weight);
		}
	}

	/**
	 * setEdgeActive method
	 * sets and edge active or innactive 
	 * @param vert1
	 * @param vert2
	 * @param active
	 */
	public void setEdgeActive(int vert1, int vert2, boolean active){

		if(withinBounds(vert1, vert2)){

			edges[vert1][vert2].setActive(active);
			edges[vert2][vert1].setActive(active);

		}
	}

	/**
	 * toggleEdgeActive
	 * toggles active state of certain edge
	 * @param vert1
	 * @param vert2
	 */
	public void toggleEdgeActive(int vert1, int vert2){

		if(withinBounds(vert1, vert2)){

			if(edges[vert1][vert2].isActive() && edges[vert2][vert1].isActive()){ 

				edges[vert1][vert2].setActive(false);
				edges[vert2][vert1].setActive(false);

			}else{

				edges[vert1][vert2].setActive(true);
				edges[vert2][vert1].setActive(true);

			}
		}
	}

	/**
	 * getAmt method
	 * returns amount of vertices in ADJMatrix
	 * @return
	 */
	public int getAmt(){ return vertices;}

	/**
	 * withinBounds method
	 * @param i vertex 1
	 * @param j vertex 2
	 * @return whether requested vertices are within current bounds of adjacency matrix
	 */
	public boolean withinBounds(int i, int j){
		return (i >= 0 && i < vertices) && (j > 0 && j < vertices);
	}

	/**
	 * listConnections method
	 * @param computer index to check connections for
	 * @return connection objects both active and non active for index
	 */
	public String listConnections(int com){

		//Passed computer index
		String list = "192.168.1." + com + ":\n";

		for(int i = 0; i < vertices; i++){

			//As long as index isn't the same as the one were checking
			if(i != com){

				list += "   192.168.1." + i + ": ";

				//If connection is active print latency else print no connection
				if(edges[com][i].isActive())
					list += edges[com][i].getWeight() + " ms\n";
				else
					list += "No connection\n";
			}
		}

		return list;

	}

	/**
	 * listConnectionsByPair method
	 * @return All connections per every computer on the network
	 */
	public String listConnectionsByPair(int com1, int com2){

		return edges[com1][com2].toString();

	}

	/**
	 * listAllConnections method
	 * @return All connections per every computer on the network
	 */
	public String toString(){

		String list = "";

		for(int i = 0; i < vertices; i++){
			list += listConnections(i);
			if(i < vertices-1)
				list += "\n";
		}

		return list;

	}

	/** 
	 * getPath method
	 * displays the weight of a edge
	 * @param i
	 * @param j
	 */
	public void getPath(int i, int j) {
		if(edges[i][j].isActive())
			System.out.println(i + " <-> " + j + " : " + edges[i][j].getWeight());
		else {
			ShortestPath sp = new ShortestPath();
			System.out.println(sp.dijkstra(edges, i, j, vertices));
		}
	}

	/**
	 * shortestPath method
	 * displays shortest path between two nodes
	 * @param i
	 * @param j
	 */
	public void shortestPath(int i, int j) {
		ShortestPath sp = new ShortestPath();
		System.out.println(sp.dijkstra(edges, i, j, vertices));
	}

	/**
	 * unreachableNodes method
	 * returns node(s) unreachable by src
	 * @param src
	 * @return
	 */
	public String unreachableNodes(int src) {


		String str = "";
		ShortestPath sp = new ShortestPath();

		int count = 0;

		for (int i = 0; i < vertices; i++) {
			if(i != src) {
				if(sp.dijkstra(edges, src, i, vertices).contains("No connection")) {
					str+="192.168.1."+ i +"\n";
					count++;
				}
			}
		}

		if(count == 0)
			str = "Entire network is connected.";

		return str;
	}

	/**
	 * getMST method
	 * returns a Minimum Spanning tree based on the ADJMatrix created
	 * @return
	 */
	public String getMST() {
		MST mst = new MST();
		return mst.prim(edges, vertices);
	}
}

class Edge {

	private int weight;
	private boolean active;
	private int vert1;
	private int vert2;

	public Edge(int vert1, int vert2, int weight, boolean active){
		this.vert1 = vert1;
		this.vert2 = vert2;
		this.weight = weight;
		this.active = active;
	}

	//ACCESSOR METHODS
	public int getWeight(){return weight;}
	public boolean isActive(){return active;}
	public int getVert1() {return vert1;}
	public int getVert2() {return vert2;}

	//MUTATOR METHODS
	public void setWeight(int weight){this.weight = weight;}
	public void setActive(boolean active){this.active = active;}

	public String toString() {
		if(active)
			return "192.168.1." + vert1 + " <-> " + "192.168.1." + vert2 + " : " + weight + " ms";
		else
			return "192.168.1." + vert1 + " <-> " + "192.168.1." + vert2 + " : No connection exists";
	}

	public int compareTo(Edge other) {
		if(weight < other.getWeight())
			return -1;
		else if(weight > other.getWeight())
			return 1;
		else
			return 0;
	}

}

class ShortestPath
{

	int vertices;

	/**
	 * dijkstra method
	 * uses dijkstra's algorithm to calculate shortest path of nodes.
	 * @param graph
	 * @param src
	 * @param tar
	 * @param count
	 */
	public String dijkstra(Edge graph[][], int src, int tar, int count)
	{
		vertices = count;
		int dist[] = new int[vertices]; 
		boolean checked[] = new boolean[vertices];

		for (int i = 0; i < vertices; i++)
		{
			dist[i] = 999999;
			checked[i] = false;
		}

		dist[src] = 0;

		for (int i = 0; i < vertices-1; i++)
		{

			int minIndex = minDistance(dist, checked);

			checked[minIndex] = true;

			for (int j = 0; j < vertices; j++) {
				if (!checked[j] && graph[minIndex][j].isActive() && dist[minIndex] + graph[minIndex][j].getWeight() < dist[j])
					dist[j] = dist[minIndex] + graph[minIndex][j].getWeight();
			}
		}

		return toString(src, tar, dist[tar]);
	}

	/** 
	 * minDistance method
	 * returns shortest distance between nodes
	 * @return
	 */
	private int minDistance(int dist[], boolean checked[])
	{

		int min = 999999;
		int min_index = -1;

		for (int i = 0; i < vertices; i++) {
			if (checked[i] == false && dist[i] <= min)
			{
				min = dist[i];
				min_index = i;
			}
		}

		return min_index;
	}

	/**
	 * toString method
	 * prints shortest path between passed vertices
	 * @param i
	 * @param j
	 * @param dist
	 */
	private String toString(int i, int j, int dist)
	{
		if(dist != 999999 && dist != -999999)
			return i + " <-> " + j +" : " + dist;
		else
			return i + " <-> " + j +" : No connection";
	}

}

class MST
{

	/**
	 * prim method 
	 * uses a 2d edge array and creates a Minimum spanning tree out of it
	 * @param graph
	 * @param count
	 * @return 
	 */
	public String prim(Edge graph[][], int count)
	{

		int vertices = count; //amount of vertices in graph
		int lastVal[] = new int[vertices]; //parent of selected vertices
		int vals[] = new int [vertices]; //vertices selected for mst
		boolean unchecked[] = new boolean[vertices]; //unchecked vertices

		for (int i = 0; i < vertices; i++) //Set all min values and unchecked
		{
			vals[i] = Integer.MAX_VALUE;
			unchecked[i] = false;
		}

		vals[0] = 0;
		lastVal[0] = -1; 

		for (int i = 0; i < vertices-1; i++) //vertices - 1 because MST will always contain amount - 1 nodes
		{
			//Finds min vertice by weight of edge
			int min = findMinVertice(vals, vertices,unchecked);

			//Check if graph is fully connected, if not return error statement
			if(min != -1)
				unchecked[min] = true;
			else
				return "Unable to create MST, ADJ Matrix is not fully connected.";

			for (int j = 0; j < vertices; j++)

				if (graph[min][j].isActive() && unchecked[j] == false) {
					if(graph[min][j].getWeight() <  vals[j])
					{
						lastVal[j]  = min;
						vals[j] = graph[min][j].getWeight();
					}
				}
		}

		return toString(lastVal, vertices, graph);
	}

	/**
	 * findMinVertice method
	 * finds the min connection
	 * @param vals
	 * @param amt
	 * @param unchecked
	 * @return
	 */
	int findMinVertice(int vals[], int amt, boolean unchecked[])
	{

		int min = Integer.MAX_VALUE;
		int index = -1;

		for (int i = 0; i < amt; i++)
			if (unchecked[i] == false && vals[i] < min)
			{
				min = vals[i];
				index = i;
			}

		return index;
	}

	/** 
	 * toString method
	 * returns mst in string form
	 * @param lastVal
	 * @param amt
	 * @param graph
	 * @return
	 */
	public String toString(int lastVal[], int amt, Edge graph[][])
	{
		String str = "";

		for (int i = 1; i < amt; i++)
			str += lastVal[i] + " <-> " + i + " : " + graph[i][lastVal[i]].getWeight() + "\n";

		return str;
	}
}
