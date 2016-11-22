import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class TestClassFunctA {
	private WorkSchedule ws;
	private String[] emp = { "Victor", "Richard" };
	private String[] emp1 = { "Victor" };
	private String[] emp2 = { "Richard" };
	private final static int SIZE = 5;

	@Before
	public void setUp() throws Exception {
		ws = new WorkSchedule(SIZE);
	}

	// ----------------------------------------------------------------
	// Partition tests
	// ----------------------------------------------------------------

	// STARTTIME = -3
	@Test
	public void Test_partition1() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], -3, 4));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// STARTTIME = 1
	@Test
	public void Test_partition2() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 1, 4));
		assertTrue(verifyHours(2, 1, 1, 4));
		assertTrue(verifyHours(2, 0, 0, 0));
		for (int i = 1; i < SIZE; i++) {
			assertArrayEquals("Test", ws.readSchedule(i).workingEmployees, emp1);
		}
	}

	// ENDTIME = -6
	@Test
	public void Test_partition3() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 0, -6));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// ENDTIME = 7
	@Test
	public void Test_partition4() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 0, 7));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// ENDTIME = 3
	// add string test
	@Test
	public void Test_partition5() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 3));
		assertTrue(verifyHours(2, 1, 0, 3));
		assertTrue(verifyHours(2, 0, 4, 4));

	}

	// STARTTIME = 2
	@Test
	public void Test_partition6() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 2, 4));
		assertTrue(verifyHours(2, 1, 2, 4));
		assertTrue(verifyHours(2, 0, 0, 1));
		for (int i = 2; i < SIZE; i++) {
			assertArrayEquals("Test", ws.readSchedule(i).workingEmployees, emp1);
		}
	}

	// STARTTIME = 6
	@Test
	public void Test_partition7() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 6, 4));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// HOUR = 1
	@Test
	public void Test_partition8() {
		ws.setRequiredNumber(1, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 1, 1));
		assertTrue(verifyHours(1, 1, 1, 1));
		assertTrue(verifyHours(1, 0, 0, 0));
		assertTrue(verifyHours(1, 0, 2, 4));
		assertFalse(ws.addWorkingPeriod(emp2[0], 1, 1));
		assertTrue(verifyHours(1, 1, 1, 1));
		assertTrue(verifyHours(1, 0, 0, 0));
		assertTrue(verifyHours(1, 0, 2, 4));
	}

	// HOUR = 1
	@Test
	public void Test_partition9() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 1, 1));
		assertTrue(verifyHours(2, 1, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 4));
		assertTrue(ws.addWorkingPeriod(emp2[0], 1, 1));
		assertTrue(verifyHours(2, 2, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 4));
	}

	// HOUR = 2
	@Test
	public void Test_partition10() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 2, 2));
		assertTrue(verifyHours(2, 1, 2, 2));
		assertTrue(verifyHours(2, 0, 0, 1));
		assertTrue(verifyHours(2, 0, 3, 4));
		assertFalse(ws.addWorkingPeriod(emp1[0], 2, 2));
		assertTrue(verifyHours(2, 1, 2, 2));
		assertTrue(verifyHours(2, 0, 0, 1));
		assertTrue(verifyHours(2, 0, 3, 4));
	}

	// HOUR = 2
	@Test
	public void Test_partition11() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 2, 2));
		assertTrue(verifyHours(2, 1, 2, 2));
		assertTrue(verifyHours(2, 0, 0, 1));
		assertTrue(verifyHours(2, 0, 3, 4));
		assertTrue(ws.addWorkingPeriod(emp2[0], 2, 2));
		assertTrue(verifyHours(2, 2, 2, 2));
		assertTrue(verifyHours(2, 0, 0, 1));
		assertTrue(verifyHours(2, 0, 3, 4));
		assertArrayEquals("Test", ws.readSchedule(2).workingEmployees, emp);
	}

	private boolean verifyHours(int reqNum, int size, int start, int end) {
		for (int i = start; i < end + 1; i++) {
			if (!verifyHour(i, reqNum, size))
				return false;
		}
		return true;
	}

	private boolean verifyHour(int hour, int reqNum, int size) {
		boolean isReqNumCorrect = ws.readSchedule(hour).requiredNumber == reqNum;
		boolean isSizeCorrect = ws.readSchedule(hour).workingEmployees.length == size;
		return isReqNumCorrect && isSizeCorrect;
	}

	// ----------------------------------------------------------------
	// Border case tests
	// ----------------------------------------------------------------

	//STARTTIME = MIN_INT
	@Test
	public void Test_border1() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], Integer.MIN_VALUE, 4));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	//STARTTIME = -1
	@Test
	public void Test_border2() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], -1, 4));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	//STARTTIME = 0
	@Test
	public void Test_border3() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 4));
		assertTrue(verifyHours(2, 1, 0, 4));
		for (int i = 0; i < SIZE; i++) {
			assertArrayEquals("Test", ws.readSchedule(i).workingEmployees, emp1);
		}
	}

	//STARTTIME = MAX_INT
	@Test
	public void Test_border4() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], Integer.MAX_VALUE, 4));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	//ENDTIME = MIN_INT
	@Test
	public void Test_border5() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 0, Integer.MIN_VALUE));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	//ENDTIME = -1
	@Test
	public void Test_border6() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 0, -1));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// endtime = size
	@Test
	public void Test_border7() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 0, 5));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// endtime = MAX_INT
	@Test
	public void Test_border8() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 0, Integer.MAX_VALUE));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// endtime = 0
	@Test
	public void Test_border9() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 0));
		assertTrue(verifyHours(2, 1, 0, 0));
		assertTrue(verifyHours(2, 0, 1, 4));
		assertArrayEquals("Test", ws.readSchedule(0).workingEmployees, emp1);
	}

	// endtime = size-1
	@Test
	public void Test_border10() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 4));
		assertTrue(verifyHours(2, 1, 0, 4));
		for (int i = 0; i < SIZE; i++) {
			assertArrayEquals("Test", ws.readSchedule(i).workingEmployees, emp1);
		}
	}

	// starttime = 0
	@Test
	public void Test_border11() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 4));
		assertTrue(verifyHours(2, 1, 0, 4));
		for (int i = 0; i < SIZE; i++) {
			assertArrayEquals("Test", ws.readSchedule(i).workingEmployees, emp1);
		}
	}

	// starttime = endtime
	@Test
	public void Test_border12() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 4, 4));
		assertTrue(verifyHours(2, 1, 4, 4));
		assertTrue(verifyHours(2, 0, 0, 3));
		assertArrayEquals("Test", ws.readSchedule(4).workingEmployees, emp1);
	}

	// starttime = endtime + 1
	@Test
	public void Test_border13() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], 5, 4));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// starttime = MAX_INT
	@Test
	public void Test_border14() {
		ws.setRequiredNumber(2, 0, 4);
		assertFalse(ws.addWorkingPeriod(emp1[0], Integer.MAX_VALUE, 4));
		assertTrue(verifyHours(2, 0, 0, 4));
	}

	// HOUR = STARTTIME
	@Test
	public void Test_border15() {
		ws.setRequiredNumber(1, 0, 0);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 0));
		assertTrue(verifyHours(1, 1, 0, 0));
		assertTrue(verifyHours(0, 0, 1, 4));
		assertFalse(ws.addWorkingPeriod(emp2[0], 0, 0));
		assertTrue(verifyHours(1, 1, 0, 0));
		assertTrue(verifyHours(0, 0, 1, 4));
	}

	// HOUR = ENDTIME
	@Test
	public void Test_border16() {
		ws.setRequiredNumber(1, 4, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 4, 4));
		assertTrue(verifyHours(1, 1, 4, 4));
		assertTrue(verifyHours(0, 0, 0, 3));
		assertFalse(ws.addWorkingPeriod(emp2[0], 4, 4));
		assertTrue(verifyHours(1, 1, 4, 4));
		assertTrue(verifyHours(0, 0, 1, 3));
	}

	// HOUR = STARTTIME
	@Test
	public void Test_border17() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 0));
		assertTrue(verifyHours(2, 1, 0, 0));
		assertTrue(verifyHours(2, 0, 1, 4));
		assertTrue(ws.addWorkingPeriod(emp2[0], 0, 0));
		assertTrue(verifyHours(2, 2, 0, 0));
		assertTrue(verifyHours(2, 0, 1, 4));
	}

	// HOUR = ENDTIME
	@Test
	public void Test_border18() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 4, 4));
		assertTrue(verifyHours(2, 1, 4, 4));
		assertTrue(verifyHours(2, 0, 0, 3));
		assertTrue(ws.addWorkingPeriod(emp2[0], 4, 4));
		assertTrue(verifyHours(2, 2, 4, 4));
		assertTrue(verifyHours(2, 0, 1, 3));
	}

	// THIS IS PARTITION10
	// hour = starttime
	@Test
	public void Test_border19() {
		ws.setRequiredNumber(2, 0, 0);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 0));
		assertTrue(verifyHours(2, 1, 0, 0));
		assertTrue(verifyHours(0, 0, 1, 4));
		assertFalse(ws.addWorkingPeriod(emp1[0], 0, 0));
		assertTrue(verifyHours(2, 1, 0, 0));
		assertTrue(verifyHours(0, 0, 1, 4));
	}

	// THIS IS PARTITION10
	// hour = endtime
	@Test
	public void Test_border20() {
		ws.setRequiredNumber(2, 4, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 4, 4));
		assertTrue(verifyHours(2, 1, 4, 4));
		assertTrue(verifyHours(0, 0, 0, 3));
		assertFalse(ws.addWorkingPeriod(emp1[0], 4, 4));
		assertTrue(verifyHours(2, 1, 4, 4));
		assertTrue(verifyHours(0, 0, 0, 3));
	}

	//HOUR = STARTTIME
	@Test
	public void Test_border21() {
		ws.setRequiredNumber(2, 0, 4);
		assertTrue(ws.addWorkingPeriod(emp1[0], 0, 0));
		assertTrue(verifyHours(2, 1, 0, 0));
		assertTrue(verifyHours(2, 0, 1, 4));
		assertTrue(ws.addWorkingPeriod(emp2[0], 0, 0));
		assertTrue(verifyHours(2, 2, 0, 0));
		assertTrue(verifyHours(2, 0, 1, 4));
		assertArrayEquals("Test", ws.readSchedule(0).workingEmployees, emp);
	}
	
	//HOUR = ENDTIME
		@Test
		public void Test_border22() {
			ws.setRequiredNumber(2, 0, 4);
			assertTrue(ws.addWorkingPeriod(emp1[0], 4, 4));
			assertTrue(verifyHours(2, 1, 4, 4));
			assertTrue(verifyHours(2, 0, 0, 3));
			assertTrue(ws.addWorkingPeriod(emp2[0], 4, 4));
			assertTrue(verifyHours(2, 2, 4, 4));
			assertTrue(verifyHours(2, 0, 0, 3));
			assertArrayEquals("Test", ws.readSchedule(4).workingEmployees, emp);
		}

}

