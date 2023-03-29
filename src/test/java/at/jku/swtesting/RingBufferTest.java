package at.jku.swtesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
Note: follow naming convention: If_Condition_WhatShouldHappen()
TODO: or think of a better one because this one might actually be too confusing
TODO: tests have not been validated yet

TODOs:
- standardize comments
- maybe only use one method per unit test (so you know it is *this one* method that fails)
 */

public class RingBufferTest {

	RingBuffer<String> ringbuffer;
	@BeforeEach
	void setUp() {
		ringbuffer = new RingBuffer<>(3);
	}

	//Test
	void IfCapacityOverZero_RingbufferInstantiated(){
		assertNotNull(ringbuffer);
	}

	@Test
	void IfCapacityUnderZero_constructorThrowsException() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> { new RingBuffer<>(0);});
		String expectedMessage = "Initial capacity is less than one";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	//Test 2: tests capacity method
	//TODO: too broad? does not test a specific funcationality
	@Test
	void IfThreeItemsEnqueued_ringBufferHasThreeItems(){
		assertEquals(3, ringbuffer.capacity());
		ringbuffer.enqueue("one");
		ringbuffer.enqueue("two");
		ringbuffer.enqueue("three");
		//a.enqueue("four"); //works either way because of the wrap-around
		assertEquals(ringbuffer.size(), ringbuffer.capacity());
	}

	//Test 3: tests size method
	@Test
	void IfItemsEnqueued_sizeEqualsNoOfEnqueuedItems(){
		ringbuffer.enqueue("eins");
		assertEquals(1, ringbuffer.size());
		ringbuffer.enqueue("zwei");
		ringbuffer.enqueue("drei");
		assertEquals(3, ringbuffer.size());
	}

	//Test 4: tests enqueue method
	@Test
	void testEnqueue() {
		ringbuffer.enqueue("a");
		assertEquals("a", ringbuffer.peek());
		ringbuffer.enqueue("b");
		ringbuffer.enqueue("c");
		ringbuffer.enqueue("d");
		assertEquals("b", ringbuffer.peek());
		ringbuffer.dequeue();
		ringbuffer.dequeue();
		assertEquals("d", ringbuffer.peek());
	}

	//Test 5: tests dequeue method
	@Test
	void testDequeueException() {
		ringbuffer = null;
		Exception exception = assertThrows(RuntimeException.class , () -> {
			ringbuffer.dequeue();});

		String expectedMessage = "Empty ring buffer.";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	//Test
	void testDequeueValue(){
		ringbuffer.enqueue("1");
		assertEquals("1" , ringbuffer.dequeue());
		ringbuffer.enqueue("2");
		ringbuffer.enqueue("3");
		ringbuffer.dequeue();
		assertEquals("3" , ringbuffer.dequeue());
	}


	//Test 6: tests peek method
	@Test
	void testPeekException() {
		Exception exception = assertThrows(RuntimeException.class , () -> {
			ringbuffer.peek();
		});
		String expectedMessage = "Empty ring buffer.";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	//Test:
	void testPeekValue(){
		ringbuffer.enqueue("a");
		assertEquals("a", ringbuffer.peek());
		ringbuffer.enqueue("b");
		assertNotEquals("b", ringbuffer.peek());
	}

}
