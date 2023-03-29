package at.jku.swtesting;

import java.util.Iterator;
import java.util.NoSuchElementException;						  

/**
 * The RingBuffer class represents a first-in-first-out (FIFO) circular queue of elements. 
 * It has a maximum capacity of elements it can hold. If more elements are added, the 
 * last element will overwrite the first one.
 * 
 * Originally derived from http://www.cs.princeton.edu/introcs/43stack/RingBuffer.java.html
 */
public class RingBuffer<Item> implements Iterable<Item> {
	
	private Item[] a; 		// queue elements
	private int N = 0; 		// number of elements on queue
	private int first = 0; 	// index of first element of queue
	private int last = 0; 	// index of next available slot
	
	/**
	 * Creates a new empty ring buffer. 
	 * @param capacity number of elements the buffer is able to hold.
	 * @throws IllegalArgumentException if the initial capacity is less than one.
	 */
	@SuppressWarnings("unchecked")
	public RingBuffer(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Initial capacity is less than one");
		}
		// cast needed since no generic array creation in Java
		a = (Item[]) new Object[capacity];
	}	
	
	/** 
	 * Returns the number of elements the buffer can hold.
	 */
	public int capacity() {
		return a.length;
	}
	
	/** 
	 * Returns the number of elements in the buffer.
	 */
	public int size() {
		return N;
	}

	/**
	 * Returns true if the buffer contains no elements.
	 */
	public boolean isEmpty() {
		return N == 0;
	}
	
	/**
	 * Returns true if the buffer has reached its capacity, which is the maximum 
	 * number of elements it can hold, before overwriting elements.
	 */	
	public boolean isFull() {
		return N == a.length;
	}

	/**
	 * Appends the specified element to the end of the buffer. If the buffer has already 
	 * reached its capacity, appending overwrites the first element in the buffer.
	 * @param item to be appended to the buffer.
	 */
	public void enqueue(Item item) {
		a[last] = item;  // last item gets overwritten by added item
		last = (last + 1) % a.length; // wrap-around
		if (N < a.length) {
			N++;
		} else {
			first = (first + 1) % a.length;
		}
	}
	
	/**
	 * Removes the first element from the buffer. 
	 * @throws RuntimeException if the buffer is empty.
	 */	
	public Item dequeue() throws RuntimeException {
		if (isEmpty()) {
			throw new RuntimeException("Empty ring buffer.");
		}
		Item item = a[first];
		a[first] = null;
		N--;
		first = (first + 1) % a.length; // wrap-around
		return item;
	}
	
	/**
	 * Returns the first element from the buffer without removing it. 
	 * @throws RuntimeException if the buffer is empty.
	 */	
	public Item peek() {
		if (isEmpty()) {
			throw new RuntimeException("Empty ring buffer.");
		}
		return a[first];
	}

	/**
	 * Returns an iterator over the elements in the buffer. 
	 */
	public Iterator<Item> iterator() {
		return new RingBufferIterator();
	}

	private class RingBufferIterator implements Iterator<Item> {
		private int i = 0;
		private boolean isFirstIterate = true;
		private int nextFirst = first;
		
		/** @inheritDoc */
		public boolean hasNext() {
			return i < N;
		}

		/** @inheritDoc */
		public void remove() {
			// iterator, doesn't implement remove() since it's optional
			throw new UnsupportedOperationException();
		}

		/** @inheritDoc */
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();

			if(isFirstIterate){
				isFirstIterate = false;
				i++;
				return a[first];
			}

			nextFirst = nextFirst+1;
			if(nextFirst == capacity()){
				nextFirst = 0;
			}
			i++;
			return a[nextFirst];
		}
	}

}
