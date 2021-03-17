package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DirectedWeightedGraph<V> extends BaseGraph<V>  {

public HashMap<V,List<V>> adjacencyList = new HashMap<>();
	
	
	
	public class Edge{
		public float weight;
		public V from;
		public V to;
		
		public Edge(V f, V t) {
			from = f;
			to = t;
		}
		public Edge(V f, V t, float w) {
			weight = w;
			from = f;
			to = t;
		}
	}
	
	public ArrayList<Edge> edges = new ArrayList<Edge>();
  
  @Override
  public String toString() {
    String tmp = "Directed Weighted Graph";
    return tmp;
  }

  @Override
  public void insertVertex(V v) {
	  if (!this.adjacencyList.containsKey(v)) {
		  this.adjacencyList.put(v, new ArrayList<V>());
      }   
  }

  @Override
  public V removeVertex(V v) {
  if (!this.adjacencyList.containsKey(v)) {
	  return null;
  }
  this.adjacencyList.remove(v);
  
  for (V u: this.adjacencyList.keySet()) {
      this.adjacencyList.get(u).remove(v);
  }
  for(int i =0; i < edges.size(); i++) {
	  Edge e = edges.get(i);
	  if(e.from.equals(v) || e.to.equals(v))
		  this.edges.remove(e);
  }
  return v;
}
  
  @Override
  public boolean areAdjacent(V v1, V v2) {
	  if(!this.adjacencyList.containsKey(v1) || !this.adjacencyList.containsKey(v2))
	 return false;
	  else
	  return this.adjacencyList.get(v1).contains(v2);
  }
  


  @Override
  public void insertEdge(V source, V target) {
	  if (!this.adjacencyList.containsKey(source)) 
          insertVertex(source);
      if (!this.adjacencyList.containsKey(target)) 
          insertVertex(target);
      
      this.adjacencyList.get(source).add(target);
     
      Edge e = new Edge(source, target);
      edges.add(e);
  }


  @Override
  public void insertEdge(V source, V target, float weight) {
	  if (!this.adjacencyList.containsKey(source)) 
          insertVertex(source);
      if (!this.adjacencyList.containsKey(target)) 
          insertVertex(target);
      
      this.adjacencyList.get(source).add(target);
     
      boolean tmp = false;
      for(int i =0; i < edges.size(); i++) {
		  Edge e = edges.get(i);
		  if(e.from.equals(source) && e.to.equals(target)) {
				e.weight = weight;
				tmp = true;
		  }
	  }
      if(!tmp) {
      Edge e = new Edge(source, target, weight);
      edges.add(e);
      }
  }
  
  @Override
  public boolean removeEdge(V source, V target) {
	  if (!this.adjacencyList.containsKey(source) || !this.adjacencyList.containsKey(target)
	  || !areAdjacent(source, target)){
		  return false;
	  }else {
	  this.adjacencyList.get(source).remove(target);
	  for(int i =0; i < edges.size(); i++) {
		  Edge e = edges.get(i);
		  if(e.from.equals(target) && e.to.equals(source))
				edges.remove(e);
	  }
	  return true;
	  }
  }
  
  public float getEdgeWeight(V source, V target) {
	   if (!this.adjacencyList.containsKey(source) || !this.adjacencyList.containsKey(target) 
		|| !areAdjacent(source, target))
		  return Float.MAX_VALUE;
	   else {
		   float w =0;
		   for(int i =0; i < edges.size(); i++) {
			   Edge e = edges.get(i);
				if(e.from.equals(source) && e.to.equals(target))
					w = e.weight;
		}
		  return w;
	   }  
	  } 
  
  @Override
  public int numVertices() {
   return this.adjacencyList.size();
  }

  @Override
  public Iterable<V> vertices() {
	  return this.adjacencyList.keySet();
  }

  @Override
  public int numEdges() {
	  int num = 0;
    for(V v : this.vertices()) {
    	num  += this.inDegree(v);
    }
    return num;
  }  

  @Override
  public boolean isDirected() {
  return true;
  }

  @Override
  public boolean isWeighted() {
  return true;
  }

  @Override
  public int outDegree(V v) {
	  if(!this.adjacencyList.containsKey(v) || this.adjacencyList.get(v) == null || v == null){
		  return -1;
	  }
	  else
	  return this.adjacencyList.get(v).size();
	  
  }

  @Override
  public int inDegree(V v) {
	  if(!this.adjacencyList.containsKey(v) || this.adjacencyList.get(v) == null || v == null){
		  return -1;
	  }else {
		  int i = 0;
		  for(V m : incomingNeighbors(v))
			  i++;
		  return i;
	  }
  }

  @Override
  public Iterable<V> outgoingNeighbors(V v) {
	  if(!this.adjacencyList.containsKey(v) || this.adjacencyList.get(v) == null || v == null){
		  return null;
	  }
	  else
	  return this.adjacencyList.get(v);
  }

  @Override
  public Iterable<V> incomingNeighbors(V v) {
	  if(!this.adjacencyList.containsKey(v) || this.adjacencyList.get(v) == null || v == null){
		  return null;
	  }else {
		  ArrayList<V> incoming = new ArrayList<V>();
		  for (V u: this.adjacencyList.keySet()){
			  if(this.areAdjacent(u, v))
				  incoming.add(u);
		  }
		  return incoming;
	  }
  }
}
