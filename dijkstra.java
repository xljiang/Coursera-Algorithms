package assign5;

/* Shortest path problem. Coursera Algo1, assignment 5
 * use Dijkstra's algorithm; naive mode (no heap)
 * ------------------
 * The algorithm:
 * Initialize:
 * - X = [start]
 * - A[start] = 0 // shortest path
 * Main Loop:
 * - while X != V // V is the whole set
 * 	 - among all edges(v, w) belongs to E
 * 		with v belongs to X, w in V-X
 * 		pick the one that minimizes "A[v] + length(v,w)" -- dijkstra's greedy score
 * 		call it (v*, w*)
 * 	 - add w* to X
 * 	 - set A[w*]:= A[v*] + length(v*, w*)
 */

import java.util.*;
import java.io.*;

public class dijkstra {
	
	public static void main(String[] args){
		// read file
		readInFile(FILENAME);
		
		// run dijkstra's algorithm
		dijkstraRun();
		
		// print out the result
		System.out.println("node: shoretest length between the node and node 1");
		for(int i = 0; i < A.length; i++){
			System.out.println(i + ": " + A[i]);
		}
		System.out.println(A[7] + "," + A[37] + "," + A[59]+ "," + A[82] + "," + A[99] + "," + 
				A[115] + "," + A[133] + "," + A[165] + "," + A[188] + "," + A[197]);
	}

	/** dijkstra's algorithm */
	private static void dijkstraRun(){
		// initialization
		initialize();
		// main_loop
		while (!X.equals(V)){
			Integer v_select = 0;
			Integer w_select = 0;
			int minLength = MAX_DISTANCE;
			for(Integer v: X){ // for all v in X
				for(Integer w: graph.get(v).keySet()){ // for all w has edges with v
					if(!X.contains(w)){ // if w not belong to X
						int length = A[v] + getLength(v,w);  // dijkstra's greedy score
						if( minLength > length) {
							minLength = length;
							v_select = (Integer) v;  // v_select is v* in the algorithm
							w_select = (Integer) w;  // w_select is w* in the algorithm
						}
					}
				}
			}		
			X.add(w_select);
			A[w_select] = A[v_select] + getLength(v_select, w_select);
		}
	}
	
	/** initialization of the algorithm */
	private static void initialize(){
		// initialize set V and X
		V.addAll(graph.keySet()); // add all vertices into set V
		X.add(1);
		// initialize all shortest path length as 1000000
		for(int i = 0; i < A.length; i++){
			A[i] = MAX_DISTANCE;
		}
		// set A[start] = 0; here start = 1 according to the assignment
		A[1] = 0;
	}
	
	/** calculate the length between vertices v and w */
	private static int getLength(Integer v, Integer w){
		int length = graph.get(v).get(w);		
		return length;
	}

	/** read input adjacent list and store all of them in the graph 
	 *  graph is a hash map, with key = node name, value = all adjacent nodes and their weights
	 *  adjNodes is a hash map, with key = adjacent node name, value = length
	 */
	private static void readInFile(String filename){
	    try {
		      BufferedReader rd = new BufferedReader(new FileReader(filename));
		      while(true) {
		    	  String line = rd.readLine();
			   	  if(line == null) break;
			   	  lineToHashMap(line);
		      }
		         rd.close();
		    } catch(IOException ex) {
		    	System.out.println("Cannot find file \"" + filename + "\"");
		    	System.exit(0);		    
		    } 
	}
	
	
	/** read one line and put the structure into the graph */
	private static void lineToHashMap(String line){
		HashMap<Integer, Integer> adjNodes = new HashMap<Integer, Integer>();
		String[] nodes = line.split("\t");
		int node = Integer.parseInt(nodes[0]);
		for (int i = 1; i < nodes.length; i++){  // i = 0 is key, not adjacent vertices
			String[] adj = nodes[i].split(",");
			int adjNode = Integer.parseInt(adj[0]);
			int adjLength = Integer.parseInt(adj[1]);
			adjNodes.put(adjNode, adjLength);
		}
		graph.put(node, adjNodes);
	}
	
	/* constants */
	private static final int MAX_DISTANCE = 1000000;
	private static final int N = 200;
	private static final String FILENAME = "file/dijkstraData.txt";
	
	/* ivars */
    private static HashMap<Integer, HashMap<Integer, Integer>> graph = new HashMap<Integer, HashMap<Integer, Integer>>(); // the whole graph
    private static Set<Integer> X = new HashSet<Integer>(); // explored nodes set
    private static Set<Integer> V = new HashSet<Integer>(); // the whole nodes set
    private static int[] A = new int[N + 1]; // store shortest path distance for each node. A[0] is a fake node

}


