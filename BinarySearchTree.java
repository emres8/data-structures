package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import given.iMap;
import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {
 
	

	
private BinaryTreeNode<Key, Value> root = new BinaryTreeNode<Key, Value>(null,null);
private int size = 0;
Comparator<Key> comparator;

	
	
  @Override
  public Value get(Key k) {
    return getValue(k);
  }

  @Override
  public Value put(Key k, Value v) {
  if(isEmpty()) {
	  root = new BinaryTreeNode<Key, Value>(k, v, null,null,null);
	  size++;
  }
  
  
  BinaryTreeNode<Key, Value> node = getNode(k);
  if(isExternal(node)) {
	  BinaryTreeNode<Key , Value> child1 = new BinaryTreeNode<Key, Value>(null,null,null,null,node);
	  BinaryTreeNode<Key , Value> child2 = new BinaryTreeNode<Key, Value>(null,null,null,null,node);
	  node.setKey(k);
	  node.setValue(v);
	  node.setLeftChild(child1);
	  node.setRightChild(child2);
	  size++;
	  return null;
  }else {
	  Value val = node.getValue();
	  node.setValue(v);
	  return val;
  	}
  }

  @Override
  public Value remove(Key k) {
   
  BinaryTreeNode<Key, Value> node = getNode(k);
  
  
  if(isExternal(node)) 
	  return null;
  
  if(isExternal(node.getLeftChild()) && isExternal(node.getRightChild())) {
	  Value v = node.getValue();
	  node = null;
	  size--;
	  return v;
  }else if(isExternal(node.getLeftChild()) && !isExternal(node.getRightChild())) {
	  Value v = node.getValue();
	  BinaryTreeNode<Key , Value> temp = node.getRightChild();
	  node.setKey(temp.getKey());
	  node.setValue(temp.getValue());
	  node.setRightChild(temp.getRightChild());
	  node.setLeftChild(temp.getLeftChild());
	  size--;
	  return v;
  }else if(!isExternal(node.getLeftChild()) && isExternal(node.getRightChild())) {
	  Value v = node.getValue();
	  BinaryTreeNode<Key , Value> temp = node.getLeftChild();
	  node.setKey(temp.getKey());
	  node.setValue(temp.getValue());
	  node.setRightChild(temp.getRightChild());
	  node.setLeftChild(temp.getLeftChild());
	  size--;
	  return v;
  }else{
	  BinaryTreeNode<Key , Value> next = getNodesInOrder().get(getNodesInOrder().indexOf(node) + 1);
	  BinaryTreeNode<Key , Value> temp = node;
	  Value v = node.getValue();
	  node.setKey(next.getKey());
	  node.setValue(next.getValue());
	  //not sure about this part
	  next.setKey(temp.getKey());
	  next.setValue(temp.getValue());
	  remove(next.getKey());
	  size--;
	  return v;
  }
  
  }

  @Override
  public Iterable<Key> keySet() {
  List<Key> list = new ArrayList<Key>();
  for(BinaryTreeNode<Key, Value> node : getNodesInOrder()) {
	  list.add(node.getKey());
  }
  return list;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size==0;
  }

  @Override
  public BinaryTreeNode<Key, Value> getRoot() {
  return root;
  }

  @Override
  public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
  return  node.getParent();
  }

  @Override
  public boolean isInternal(BinaryTreeNode<Key, Value> node) {
   return (node.leftChild != null || node.rightChild != null);
  }

  @Override
  public boolean isExternal(BinaryTreeNode<Key, Value> node) {
   return (node.getLeftChild() == null && node.getRightChild() == null);
  }

  @Override
  public boolean isRoot(BinaryTreeNode<Key, Value> node) {
	  return node == root;
  }

  @Override
  public BinaryTreeNode<Key, Value> getNode(Key k) {
	  BinaryTreeNode<Key, Value> node = getNodeHelper(k , root);
	  if(isExternal(node))
	  return null;
	
	  return node;
  }
  
  
  private BinaryTreeNode<Key, Value> getNodeHelper(Key k , BinaryTreeNode<Key, Value> node){
	  if(isExternal(node)) {
		  return node;
	  }
	  else if(comparator.compare(k, node.getKey()) == 0) {
		  return node;
	  }else if(comparator.compare(k, node.getKey()) > 0){
		  return getNodeHelper(k , node.getRightChild());
	  }else 
		  return getNodeHelper(k , node.getLeftChild());
  }

  @Override
  public Value getValue(Key k) {
	  BinaryTreeNode<Key, Value> temp = getNode(k);
	  if(isExternal(temp))
		  return null;
	  return temp.getValue();
  }

  @Override
  public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
   return node.getLeftChild();
  }

  @Override
  public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
  return node.getRightChild();
  }

  @Override
  public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
	if(node.getParent().getLeftChild() == node && node.getParent().getRightChild() != null) {
		return node.getParent().getRightChild();	
	}
	else if(node.getParent().getRightChild() == node && node.getParent().getLeftChild() != null) {
		return node.getParent().getLeftChild();	
	}else	
    return null;
  }

  @Override
  public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
	  return node.getParent().getLeftChild() == node;
  }

  @Override
  public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
   return node.getParent().getRightChild() == node;
  }

  @Override
  public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
  List<BinaryTreeNode<Key, Value>> list = new ArrayList<BinaryTreeNode<Key, Value>>();
  if(root != null)
  inOrderTraversal(root,list);
  return list;
  
  }

  private void inOrderTraversal(BinaryTreeNode<Key, Value> node , List<BinaryTreeNode<Key, Value>> list) {
	  if(!isExternal(node.getLeftChild())) {
		  inOrderTraversal(node.getLeftChild(), list);
	  } 
	  list.add(node);
	  if(!isExternal(node.getRightChild())) {
		  inOrderTraversal(node.getRightChild(), list);
	  } 
  }

  @Override
  public void setComparator(Comparator<Key> C) {
  this.comparator = C;
  }

  @Override
  public Comparator<Key> getComparator() {
   return comparator;
  }

  @Override
  public BinaryTreeNode<Key, Value> ceiling(Key k) {
	  BinaryTreeNode<Key, Value> node = getNode(k);
		if(isInternal(node)) 
		return node;
		
		while(!isRoot(node)) {
			if(isLeftChild(node)) {
				return node.getParent();
			}else {
				node = node.getParent();
			}
		}
		return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> floor(Key k) {
		BinaryTreeNode<Key, Value> node = getNode(k);
		if(isInternal(node)) 
		return node;
		
		while(!isRoot(node)) {
			if(isRightChild(node)) {
				return node.getParent();
			} else {
				node = node.getParent();
			}
		}
		return null;
	}
}
