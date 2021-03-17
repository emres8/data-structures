package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  Emre Safter
 * Class : LLDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the linked list yourself
 * Note that it should be a doubly linked list
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Iterator;


import given.Util;

//If you have been following the class, it should be obvious by now how to implement a Deque wth a doubly linked list
public class LLDeque<E> implements iDeque<E> {
  
  //Use sentinel nodes. See slides if needed
  private Node<E> header;
  private Node<E> trailer;
  
  private int size;

  // The nested node class, provided for your convenience. Feel free to modify
  private class Node<T> {
    private T element;
    private Node<T> next;
    private Node<T> prev;
    /*
     * ADD FIELDS IF NEEDED
     */
    
    Node(T d, Node<T> n, Node<T> p) {
      element = d;
      next = n;
      prev = p;
    }
    
    // to make more understandable
    public T getElement() { return element; } 
    public Node<T> getPrev() { return prev; } 
    public Node<T> getNext() { return next; } 
    public void setPrev(Node<T> p) { prev = p; } 
    public void setNext(Node<T> n) { next = n; }
  }
  
  public LLDeque() {
    //Remember how we initialized the sentinel nodes
    header  = new Node<E>(null, null, header);
    trailer = new Node<E>(null, trailer, header);
    header.next = trailer;
    size = 0;
    /*
     * ADD CODE IF NEEDED
     */
  }
  
  public String toString() {
    if(isEmpty())
      return "";
    StringBuilder sb = new StringBuilder(1000);
    sb.append("[");
    Node<E> tmp = header.next;
    while(tmp.next != trailer) {
      sb.append(tmp.element.toString());
      sb.append(", ");
      tmp = tmp.next;
    }
    sb.append(tmp.element.toString());
    sb.append("]");
    return sb.toString();
  }
  
  /*
   * ADD METHODS IF NEEDED
   */
  
  //took help from the book. I couldnt implement on my own without this method
  private E remove(Node<E> removal) {
	   Node<E> pre = removal.getPrev( );
	   Node<E> suc = removal.getNext( );
	   pre.setNext(suc);
	   suc.setPrev(pre);
	   size--;
	   return removal.getElement( );
  }
 
  /*
   * Below are the interface methods to be overriden
   */

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {

    return (size==0);
  }

  @Override
//  public void addFront(E o) {
//  addBetween(o , header, header.getNext());
//  }
  public void addFront(E o) {
	  Node<E> newest = new Node<>(o , header.getNext() , header);
	  header.getNext().setPrev(newest);
	  header.setNext(newest);
	  size++;
	  }
  @Override
  public E removeFront() {
   if(isEmpty()) {
    return null;
   }
   return remove(header.getNext());
  }
  
  @Override
  public E front() {
    if(isEmpty()) {
    	return null;
    }
    return header.getNext().getElement();
  }

  @Override
  public void addBehind(E o) {
	  Node<E> newest = new Node<>(o  , trailer , trailer.getPrev());
	  trailer.getPrev().setNext(newest);
	  trailer.setPrev(newest);
	  size++;
  }

  @Override
  public E removeBehind() {
	  if(isEmpty()) {
	    	return null;
	    }
	return  remove(trailer.getPrev());
  }

  @Override
  public E behind() {
    if(isEmpty()) {
    return null;
    }
    return trailer.getPrev().getElement();
  }

  @Override
  public void clear() {

    while(!isEmpty()) {
    	remove(header.getNext());
    }
  }
  
  @Override
  public Iterator<E> iterator() {

    //Hint: Fill in the LLDequeIterator given below and return a new instance of it
    return new LLDequeIterator();
   
  }
  
  private final class LLDequeIterator implements Iterator<E> {
    
    private Node<E> current = header.getNext();
    

    @Override
    public boolean hasNext() {
      return !(current == trailer);
    }

    @Override
    public E next() {
      if(!hasNext()) 
    	  return null;
      E temp = current.getElement();
      current = current.getNext();
      return temp;

    }        
  }
  
}
