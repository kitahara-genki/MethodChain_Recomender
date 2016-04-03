package studio1.percent;
import static org.junit.Assert.*;
import org.junit.Test;


public class PercentTest {

	@Test
	public void test() {
		PercentModel pm = new PercentModel();
		int checkType = pm.computePercentOf(0);
		// initially, pm at 100%
		assertEquals(40, pm.computePercentOf(40));
		// try 50%
		pm.setValue(50);
		assertEquals(20, pm.computePercentOf(40));
		// try 50% again (make sure it's not cummulative)
		pm.setValue(50);
		assertEquals(20, pm.computePercentOf(40));
		// try 0%
		pm.setValue(0);
		assertEquals(0, pm.computePercentOf(40));
		// try 300% which should be same as 100%
		pm.setValue(300);
		assertEquals(40, pm.computePercentOf(40));
		// try -300% which should be same as 0%
		pm.setValue(-300);
		assertEquals(0, pm.computePercentOf(40));
	}

}
