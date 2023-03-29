package at.jku.swtesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

//Note: Tests follow the naming convention: ifCondition_whatShouldHappen()

public class RingBufferTest {

	RingBuffer<String> ringBuffer;

	@BeforeEach
	void setUp() {
		ringBuffer = new RingBuffer<>(3);
	}

	@Test
	void ifCapacityOverOne_RingbufferInstantiated(){
		assertNotNull(ringBuffer);
	}

	@Test
	void ifCapacityUnderOne_constructorThrowsException() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> { new RingBuffer<>(0);});
		String expectedMessage = "Initial capacity is less than one";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void ifItemsEnqueued_sizeEqualsCapacity(){
		ringBuffer.enqueue("one");
		ringBuffer.enqueue("two");
		ringBuffer.enqueue("three");
		assertEquals(ringBuffer.size(), ringBuffer.capacity());
	}

	@Test
	void ifItemsEnqueued_sizeEqualsNoOfEnqueuedItems(){
		ringBuffer.enqueue("eins");
		assertEquals(1, ringBuffer.size());
		ringBuffer.enqueue("zwei");
		ringBuffer.enqueue("drei");
		assertEquals(3, ringBuffer.size());
	}

	@Test
	void ifItemsEnqueued_peekReturnsCorrectItems() {
		ringBuffer.enqueue("a");
		assertEquals("a", ringBuffer.peek());
		ringBuffer.enqueue("b");
		ringBuffer.enqueue("c");
		ringBuffer.enqueue("d");
		assertEquals("b", ringBuffer.peek());
		ringBuffer.dequeue();
		ringBuffer.dequeue();
		assertEquals("d", ringBuffer.peek());
	}

	@Test
	void ifEmptyRingBufferDequeued_dequeueThrowsException() {
		Exception exception = assertThrows(RuntimeException.class , () -> {
			ringBuffer.dequeue();});

		String expectedMessage = "Empty ring buffer.";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void ifItemDequeued_dequeueReturnsFirstItem(){
		ringBuffer.enqueue("1");
		assertEquals("1" , ringBuffer.dequeue());
		ringBuffer.enqueue("2");
		ringBuffer.enqueue("3");
		ringBuffer.dequeue();
		assertEquals("3" , ringBuffer.dequeue());
	}


	@Test
	void ifEmptyRingBufferPeeked_peekThrowsException() {
		Exception exception = assertThrows(RuntimeException.class , () -> {
			ringBuffer.peek();
		});
		String expectedMessage = "Empty ring buffer.";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void ifItemEnqueued_peekReturnsFirstItem(){
		ringBuffer.enqueue("a");
		assertEquals("a", ringBuffer.peek());
		ringBuffer.enqueue("b");
		assertNotEquals("b", ringBuffer.peek());
	}

	@Test
	void ifRingBufferIsIterated_iteratorReturnsCorrectOrderOfElements(){
		ringBuffer.enqueue("c");
		ringBuffer.enqueue("a");
		ringBuffer.enqueue("b");
		Iterator<String> ringBufferIterator = ringBuffer.iterator();
		assertEquals("c", ringBufferIterator.next());
		assertEquals("a", ringBufferIterator.next());
		assertEquals("b", ringBufferIterator.next());
	}

	@Test
	void ifRingBufferOverflows_nextItemAfterFormerFirstIsReturned(){
		ringBuffer.enqueue("a");
		ringBuffer.enqueue("b"); // <-- this one should be returned
		ringBuffer.enqueue("c");
		ringBuffer.enqueue("d");

		assertEquals("b", ringBuffer.peek());
	}

	/*---Test revealing a bug---
	In this scenario the order of elements should be a first, then b and c.
	If you try to overflow the ring buffer, this is the expected behaviour:
		- If you add d, it will replace a and b should become the new first.
		- If you add e, it will replace b and c should become the new first.
	So if the ring buffer is iterated over, it should be in this order: c, d, e.
	After all, in this case peek() would return c (under the assumption that peek() is indeed working).
	 */

	@Test
	void ifRingBufferOverFlowsAndIsIterated_iteratorReturnsCorrectOrderOfElements(){
		ringBuffer.enqueue("a");
		ringBuffer.enqueue("b");
		ringBuffer.enqueue("c"); // <-- should now be the first item
		ringBuffer.enqueue("d"); // <-- overwrites "a" and comes after c
		ringBuffer.enqueue("e"); // <-- overwrites "b" and comes after d
		Iterator<String> ringBufferIterator = ringBuffer.iterator();
		assertEquals("c", ringBufferIterator.next());
		assertEquals("d", ringBufferIterator.next());
		assertEquals("e", ringBufferIterator.next());
	}
}
