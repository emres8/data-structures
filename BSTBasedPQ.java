package code;

import java.util.List;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	public BSTBasedPQ() {
		super();
	}
  
  @Override
  public void insert(Key k, Value v) {
	  put(k , v);
    
  }

  @Override
	public Entry<Key, Value> pop() {
		if(isEmpty()) return null;
		BinaryTreeNode<Key, Value> node = this.getRoot();
		while(isInternal(node.getLeftChild())) {
			node = node.getLeftChild();
		}
		Entry<Key, Value> entry = new Entry<Key, Value>(node.getKey(), node.getValue());
		this.remove(node.getKey());
		return entry;
	}

  @Override
	public Entry<Key, Value> top() {
		if(isEmpty()) return null;
		BinaryTreeNode<Key, Value> node = this.getRoot();
		while(isInternal(node.getLeftChild())) {
			node = node.getLeftChild();
		}
		return node;
		
	}
  
  // Replace the key of the given entry and return the old key
  // Return null if the entry does not exists
  // Make sure to match both the key and the value before replacing anything!

  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
   Key key = entry.getKey();
   Value value = entry.getValue();
   
   if(isExternal(getNode(key))) 
	   return null;
   if(getNode(key).getValue().equals(value) && getNode(key).getKey().equals(key)) {
	   remove(key);
	   put(k , value);
	   return key;
   }
   return null;
  }

  @Override
  public Key replaceKey(Value v, Key k) {
	  Key key = null;
	  for(BinaryTreeNode<Key, Value> node : getNodesInOrder()) {
		  if(node.getValue().equals(v)) {
			  key = node.getKey();
			  remove(key);
			  put(k , v);
		  }
	  }
	  return key;
  }

  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
	Value value = null;
	if(isExternal(getNode(entry.getKey()))) return null;
	
	// I asked my friends for this part and still I cant get how keys may differ from each other since we basically reaching node using entry's key
	if(getNode(entry.getKey()).getKey().equals(entry.getKey()) && getNode(entry.getKey()).getValue().equals(entry.getValue())) {
		value = getNode(entry.getKey()).getValue();
		getNode(entry.getKey()).setValue(v);
	}
	return value;
  }


}
