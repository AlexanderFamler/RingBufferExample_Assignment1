package at.jku.swtesting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RingBufferTest {

	RingBuffer<String> a;
	@BeforeEach
	void setUp() {
		a = new RingBuffer<>(3);
	}

	//test for constructor
	@Test
	void testRingBufferConstructor() {
		assertNotNull(a);
		assertThrows(IllegalArgumentException.class, () -> { new RingBuffer<>(0);});
	}

	//test for capacity method
	@Test
	void testCapacity(){
		assertEquals(3, a.capacity());
		a.enqueue("one");
		a.enqueue("two");
		a.enqueue("three");
		//a.enqueue("four"); //works either way because of the wrap-around
		assertEquals(a.size(), a.capacity());
	}

	@Test
	void testSize(){
		a.enqueue("eins");
		a.enqueue("zwei");
		assertEquals(2, a.size());
	}

	@Test
	void testEnqueue() {
		a.enqueue("a");
		assertEquals("a", a.peek());
		a.enqueue("b");
		a.enqueue("c");
		a.enqueue("d");
		assertEquals("b", a.peek());
		a.dequeue();
		a.dequeue();
		assertEquals("d", a.peek());
	}

	@Test
	void testDequeue() {
		a = null;
		assertThrows(RuntimeException.class , () -> {a.dequeue();});

		a = new RingBuffer<>(3);
		a.enqueue("1");
		assertEquals("1" , a.dequeue());
		a.enqueue("2");
		a.enqueue("3");
		a.dequeue();
		assertEquals("3" , a.dequeue());
	}

	@Test
	void testPeek() {
		a = null;
		assertThrows(RuntimeException.class , () -> {a.peek();});

		a = new RingBuffer<>(3);
		a.enqueue("a");
		assertEquals("a", a.peek());
		a.enqueue("b");
		assertNotEquals("b", a.peek());
	}

}
