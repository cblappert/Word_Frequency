package test;
import static org.junit.Assert.*;

import org.junit.*;

import providedCode.DataCount;
import providedCode.DataCounter;
import providedCode.SimpleIterator;


public abstract class TestDataCounter<E> {
	protected static final long TIMEOUT = 2000;
	protected DataCounter<E> dc;
	protected abstract DataCounter<E> getDataCounter();
	
	@Before
	public void setUp() {
		dc = getDataCounter();
	}
	
	@Test(timeout = TIMEOUT)
	public void test_size_empty(){
		assertEquals("Tree should have size 0 when constructed", 0, dc.getSize());
	}
	
	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void test_iterator_empty() {
		SimpleIterator<DataCount<E>> iter = dc.getIterator();
		iter.next(); 
	}
}
