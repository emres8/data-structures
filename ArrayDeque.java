package code;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  Emre Safter
 * Class : ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY 
 * 
 * */

import given.iDeque;
import java.util.Arrays;
import java.util.Iterator;

import given.Util;


/*
 * You must have a circular implementation. 
 * 
 * WARNING: Modulo operator (%) might create issues with negative numbers.
 * Use Math.floorMod instead if you are having issues
 */
public class ArrayDeque<E> implements iDeque<E> {

  private E[] A; //Do not change this name!
  private int front;
  private int size;
 

	
	public ArrayDeque() {
		this(1000);
    /*
     * ADD CODE IF NEEDED
     */
	}
	
	public ArrayDeque(int initialCapacity) {
	   if(initialCapacity < 1)
	      throw new IllegalArgumentException();
		A = createNewArrayWithSize(initialCapacity);
		
		front = 0;
		size = 0;
		
	}
	
	// This is given to you for your convenience since creating arrays of generics is not straightforward in Java
	@SuppressWarnings({"unchecked" })
  private E[] createNewArrayWithSize(int size) {
	  return (E[]) new Object[size];
	}
	
	
	
	
	//Modify this such that the dequeue prints from front to back!
	//Hint, after you implement the iterator, use that!
  public String toString() {
    
	if(isEmpty()) return "";
    
    StringBuilder sb = new StringBuilder(1000);
    sb.append("[");
    Iterator<E> iter = iterator();
    while(iter.hasNext()) {
      E e = iter.next();
      if(e == null)
        continue;
      sb.append(e);
      if(!iter.hasNext())
        sb.append("]");
      else
        sb.append(", ");
    }
    return sb.toString();
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
  public void addFront(E o) {
  if(size == A.length) {
		E temp[] = createNewArrayWithSize(2*A.length);
		for(int i=0; i<size-1;i++) {
			temp[i] = A[i];
		}
		front = 0;
		A = temp;
	} 
  	front = Math.floorMod((front-1),A.length);
	A[front] = o;
	size++;
}

  @Override
  public E removeFront() {
	  if (isEmpty())
	       return null;
	     E temp = A[front];
	     A[front] = null;
	     front = Math.floorMod((front+1),A.length);
	     size--; 
	     return temp;
  }
  
  // Removes the first node


  @Override
  public E front() {
  if(isEmpty()) {
	  return null;
  }
  return A[front];  
  }

  @Override
  public void addBehind(E o) {
	  if(size == A.length) {
		  E temp[] = createNewArrayWithSize(2*A.length);
		  for(int i=0; i<size-1;i++) {
			  int j = Math.floorMod(front + i, A.length);
			  temp[i] = A[j];
		  }	
		  front = 0;
		  A = temp;
	  }
	  A[Math.floorMod((front+size),A.length)] = o;
    size++;
	    
  }

  @Override
  public E removeBehind() {
	  if (isEmpty())
	      return null;

	  	E temp = A[Math.floorMod((front+size-1),A.length)];
		A[Math.floorMod((front+size-1),A.length)] = null;
		size--;
		return temp;
 }

  @Override
  public E behind() {
	  if(isEmpty()) {
		  return null;
	  }
	  return A[Math.floorMod((front+size-1),A.length)];
  }

  @Override
  public void clear() {
	  for(int i=0; i<A.length; i++) {
			A[i] = null;
		}
		size = 0;
		front = 0;
    
  }
  
  //Must print from front to back
  @Override
  public Iterator<E> iterator() {
    //Hint: Fill in the ArrayDequeIterator given below and return a new instance of it
    return new ArrayDequeIterator();
  }
  
  private final class ArrayDequeIterator implements Iterator<E> {
    
    /*
     * 
     * ADD A CONSTRUCTOR IF NEEDED
     * Note that you can freely access everything about the outer class!
     * 
     */
    
    private int current = 0;
  	private int frontIter = front;
  	  
      @Override
      public boolean hasNext() {
      return (current < size);
      }

      @Override
      public E next() {
      	
      		if(current == size) return null;
      		current++;
      		return A[Math.floorMod((frontIter++),A.length)];
      		
    }        
  }
}
