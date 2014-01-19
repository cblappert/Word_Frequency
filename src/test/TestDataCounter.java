package test;
import static org.junit.Assert.*;
import org.junit.*;
import providedCode.DataCounter;


public abstract class TestDataCounter<E> {
	protected DataCounter<E> dc;
	protected abstract DataCounter<E> getDataCounter();
	
	@Before
	public void setUp() {
		dc = getDataCounter();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
}
