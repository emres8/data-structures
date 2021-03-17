package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {
  
  // Use this arraylist to store the nodes of the heap. 
  // This is required for the autograder. 
  // It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but then you do not have to deal with dynamic resizing
  protected ArrayList<Entry<Key,Value>> nodes;
 
  Comparator<Key> comparator;
  
  public int parentOf(int i) {
		return (i-1)/2;
	}
	public int leftChildOf(int i) {
		return 2*i+1;
	}
	public int rightChildOf(int i) {
		return 2*i+2;
	}

	public boolean hasLeftChild(int i ) {
		return leftChildOf(i) < size();
	}
	public boolean hasRightChild(int i ) {
		return rightChildOf(i) < size();
	}
	
	public void swap(int i , int j) {
		Entry<Key, Value> temp = nodes.get(i);
		nodes.set(i, nodes.get(j));
		nodes.set(j, temp);
	}
	
	private void upHeap(int i) {
		while(i > 0) {
			int p = parentOf(i);
			if(comparator.compare(nodes.get(i).getKey(), nodes.get(p).getKey()) >= 0 ) 
			break;
			
			swap(i , p);
			i = p;
		}
	}
	
	private void downHeap(int i) {
		while(hasLeftChild(i)) {
			int smallest = leftChildOf(i);
			if(hasRightChild(i)) {
				int rc = rightChildOf(i);
				if(comparator.compare(nodes.get(rc).getKey(), nodes.get(smallest).getKey()) < 0) {
					smallest = rc;
				}
			}
			if(comparator.compare(nodes.get(smallest).getKey(),nodes.get(i).getKey()) >= 0 ) {
				break;
			}
			swap(i , smallest);
			i = smallest;
		}
	}
  
  
  
  @Override
  public int size() {
  return nodes.size();
  }

  @Override
  public boolean isEmpty() {
   return size() == 0;
  }

  @Override
  public void setComparator(Comparator<Key> C) {
   comparator = C;
    
  }

  @Override
  public Comparator<Key> getComparator() {
  return comparator;
  }

  @Override
  public void insert(Key k, Value v) {
   Entry<Key, Value> entry = new Entry<Key, Value>(k , v);
   nodes.add(entry);
   upHeap(size()-1);
  }
 

  @Override
  public Entry<Key, Value> pop() {
	  if(nodes.isEmpty()) return null;
		Entry<Key, Value> entry = nodes.get(0);
		swap(0, size()-1);
		nodes.remove(nodes.size()-1);
		downHeap(0);
		return entry;
  }

  @Override
  public Entry<Key, Value> top() {
	  if(nodes.isEmpty()) return null;
		return nodes.get(0);
	}

  @Override
  public Value remove(Key k) {
    int location = 0; 
    Value value = null;
    for(Entry<Key, Value> entry : nodes) {
    	if(entry.getKey() == k) {
    		location = nodes.indexOf(entry);
    		value = entry.getValue();
    	}
    }
    swap(location , size()- 1);
    nodes.remove(size() - 1);
    if(comparator.compare(nodes.get(location).getKey(), nodes.get(this.parentOf(location)).getKey()) < 0){
    	upHeap(location);
    }else {
    	downHeap(location);
    }
    return value;
  }

  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
	  Key key = null;
	  int location = 0;
	  for(Entry<Key , Value> candidate  : nodes) {
		  if(candidate.getKey() == entry.getKey() && candidate.getValue() == entry.getValue()) {
			  key = candidate.getKey();
			  entry.setKey(k);
			  location = nodes.indexOf(candidate);
		  }
		  candidate.setKey(k);
		  if(comparator.compare(nodes.get(location).getKey(), nodes.get(this.parentOf(location)).getKey()) < 0) {
		    	upHeap(location);
		    }else {
		    	downHeap(location);
		    }
	  }
	  return key;
  }

  @Override
  public Key replaceKey(Value v, Key k) {
  Key key = null;
  for(Entry<Key , Value> entry : nodes) {
	  if(entry.getValue().equals(v)) {
		  key = entry.getKey();
		  replaceKey(entry , k);
	  }
  }
  return key;
  }

  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
	  Value oldValue = null;
   if(nodes.get(nodes.indexOf(entry)).getKey() == entry.getKey() && nodes.get(nodes.indexOf(entry)).getValue() == entry.getValue()) {
	   oldValue = entry.getValue();
	   entry.setValue(v);
   }
   return oldValue;
  }
  
}
