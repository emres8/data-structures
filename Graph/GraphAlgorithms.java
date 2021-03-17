package code;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import java.util.Stack;

/*
 * The class that will hold your graph algorithm implementations
 * Implement:
 * - Depth first search
 * - Breadth first search
 * - Dijkstra's single-source all-destinations shortest path algorithm
 * 
 * Feel free to add any addition methods and fields as you like
 */

public class GraphAlgorithms<V extends Comparable<V>> {
	
	
public  class costComparator implements Comparator<V> {
		 public float getCost(V v) {
			  if(weightList.containsKey(v))
			  return weightList.get(v);
			  else 
		      return Float.POSITIVE_INFINITY;
		  }
		@Override
		public int compare(V o1, V o2) {
		return (int) (this.getCost(o1) - this.getCost(o2));
		}
	  
	}
	 
 public Comparator<V> costComparator = new costComparator(); 
	
  public HashMap<V,Boolean> visitList = new HashMap<>();
  

  
  
  public  List<V> DFSlist = new ArrayList<V>();
  public  List<V> BFSlist = new ArrayList<V>();
  
  
  public void visit(V v) {
	  visitList.put(v, true);
  }
  
  public void resetList() {
	  for(V v : visitList.keySet()) 		
	  visitList.replace(v, false);
  }
 
  
  public boolean isVisited(V v) {
	  if(visitList.containsKey(v))
		  return visitList.get(v);
	  else 
		  return false;
  }
  
  public boolean allVisited() {
	  boolean tmp = true;
	  for(V v : visitList.keySet()) {
		  if(!isVisited(v))
			  tmp = false;
	  }
	  return tmp;	  
  }
  
  public static boolean usageCheck = false;
  
  /*
   * WARNING: MUST USE THIS FUNCTION TO SORT THE 
   * NEIGHBORS (the adjacent call in the pseudocodes)
   * FOR DFS AND BFS
   * 
   * THIS IS DONE TO MAKE AUTOGRADING EASIER
   */
  public Iterable<V> iterableToSortedIterable(Iterable<V> inIterable) {
    usageCheck = true;
    List<V> sorted = new ArrayList<>();
    for (V i : inIterable) {
      sorted.add(i);
    }
    Collections.sort(sorted);
    return sorted;
  }
  
  /*
   * Runs depth first search on the given graph G and
   * returns a list of vertices in the visited order, 
   * starting from the startvertex.
   * 
   */
  

  
    public List<V> DFS(BaseGraph<V> G, V startVertex) {
    resetList();
    Stack<V> s = new Stack<>();
    s.push(startVertex);
    while(!s.isEmpty()) {
    	V u = s.pop();
    	if(!isVisited(u)) {
    		visit(u);
    		this.DFSlist.add(u);
    		for(V k : iterableToSortedIterable(G.outgoingNeighbors(u))){
    			if(!isVisited(k))
    				s.push(k);
    		}
    	}
    }
    return this.DFSlist; 	
  }
    
  /*
   * Runs breadth first search on the given graph G and
   * returns a list of vertices in the visited order, 
   * starting from the startvertex.
   * 
   */
  

  
    public List<V> BFS(BaseGraph<V> G, V startVertex) {
    	usageCheck = false;
    	resetList();
    	Deque<V> q = new ArrayDeque<>();
    	q.addLast(startVertex);
   
    	while(!q.isEmpty()) {
    		V u = q.removeFirst();
    		if(!isVisited(u)) {
    			visit(u);
    			BFSlist.add(u);
    			for(V w : iterableToSortedIterable(G.outgoingNeighbors(u))){
    				if(!isVisited(w))
    					q.addLast(w);
    			}
    		}
    	}
    	return BFSlist;
    }

  
    
  
  
  /*
   * Runs Dijkstras single source all-destinations shortest path 
   * algorithm on the given graph G and returns a map of vertices
   * and their associated minimum costs, starting from the startvertex.
   * 
   */
  //to keep track of costs
  public HashMap<V,Float> weightList = new HashMap<>();
  
  

  
  public void setCost(V v,float w) {
	  this.weightList.put(v, w);
  }
  
  public float getCost(V v) {
	  return weightList.get(v);

  }
  
  
  public void initWeightList(BaseGraph<V> G) {
	  for(V v : G.vertices())
	  this.weightList.put(v,Float.POSITIVE_INFINITY);
  }
  

  	public HashMap<V,Float> Dijkstras(BaseGraph<V> G, V startVertex) {
    usageCheck = false;
    HashMap<V,Float> DijkstraMap = new HashMap<>();
    resetList();
    initWeightList(G);
    setCost(startVertex,0);
	PriorityQueue<V> pq = new PriorityQueue(G.numVertices(), costComparator);
	
    pq.add(startVertex);
 
    while(!pq.isEmpty()) {
    V u = pq.poll();
    if(!this.isVisited(u)) {
    	this.visit(u);
    	DijkstraMap.put(u, this.getCost(u));
    	
    	for(V w : iterableToSortedIterable(G.outgoingNeighbors(u))){
    		if(!this.isVisited(w) && this.getCost(w) > ( this.getCost(u) + G.getEdgeWeight(u,w ))) {
    			this.setCost(w, this.getCost(u) + G.getEdgeWeight(u, w));
    			if(!pq.contains(w))
    			pq.add(w);
    		}
    	}
    }
    }
    
    return DijkstraMap;
  }
  
  
  public BaseGraph<V>  undirectedtoDirected(BaseGraph<V> G) {
	  BaseGraph<V> tmp;
	  if(G.isWeighted()) {
		  tmp = new DirectedWeightedGraph();
		  for(V origin : G.vertices()) {
			  tmp.insertVertex(origin);
			  for(V adj : G.incomingNeighbors(origin)) {
				  tmp.insertEdge(origin, adj , G.getEdgeWeight(adj, origin));
				  tmp.insertEdge(adj,origin , G.getEdgeWeight(adj, origin));
			  }
		  }
	  }else {
		  tmp = new DirectedUnweightedGraph();
		  for(V origin : G.vertices()) {
			  tmp.insertVertex(origin);
			  for(V adj : G.incomingNeighbors(origin)) {
				  tmp.insertEdge(origin, adj);
				  tmp.insertEdge(adj,origin);
			  }
	  }
	 }
	  return tmp;
  }
  
  /*
   *  Returns true if the given graph is cyclic, false otherwise
   */
  public boolean isCyclic(BaseGraph<V> G) {

	  boolean isCyclic = false;
	  if(!G.isDirected())
		  G = this.undirectedtoDirected(G);
	  
	  resetList();
	  Stack<V> s = new Stack<>();
	  s.push(G.vertices().iterator().next());
	  while(!s.isEmpty()) {
		  V u = s.pop();
		  if(!isVisited(u)) 
			  visit(u);
			  for(V k : iterableToSortedIterable(G.outgoingNeighbors(u))){
				  if(isVisited(k) && s.contains(k))
					  isCyclic = true;
				  if(!isVisited(k))
					  s.push(k);		
			  
		  }
	  }
	  return isCyclic;
  } 
  
  

	  
}

 
  

