package code;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

public class UndirectedUnweightedGraph<V> extends BaseGraph<V> {
  
	private HashMap<V,List<V>> adjacencyList = new HashMap<>();
  
  @Override
  public String toString() {
    String tmp = "Undirected Unweighted Graph";
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
      this.adjacencyList.get(target).add(source);
  }

  @Override
  public void insertEdge(V source, V target, float weight) {
    insertEdge(source,target);
  }

  
  @Override
  public boolean removeEdge(V source, V target) {
	  if (!this.adjacencyList.containsKey(source) || !this.adjacencyList.containsKey(target)
	  || !areAdjacent(source, target)){
		  return false;
	  }else {
	  this.adjacencyList.get(source).remove(target);
	  this.adjacencyList.get(target).remove(source);
	  return true;
	  }
  }

 
  @Override
  public float getEdgeWeight(V source, V target) {
   if (!this.adjacencyList.containsKey(source) || !this.adjacencyList.containsKey(target) 
	|| !areAdjacent(source, target))
	  return 0;
   else
	  return 1;
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
    return num/2;
  }

  @Override
  public boolean isDirected() {
    return false;
  }

  @Override
  public boolean isWeighted() {
    return false;
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
	  }
	  else
	  return this.adjacencyList.get(v).size();
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
	 return outgoingNeighbors(v);
  }

}
