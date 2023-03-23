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

}
