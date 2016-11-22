import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class TestClassFunctB {
	private WorkSchedule ws;
	private String[] emp = { "Victor", "Richard" };
	private String[] emp1 = { "Victor" };
	private String[] emp2 = { "Richard" };
	private final static int SIZE = 3;

	@Before
	public void setUp() {
		ws = new WorkSchedule(SIZE);
		ws.setRequiredNumber(2, 0, 2);
		ws.addWorkingPeriod(emp[0], 1, 1);
		ws.addWorkingPeriod(emp[1], 1, 1);
	}

	// ----------------------------------------------------------------
	// Partition tests
	// ----------------------------------------------------------------

	@Test
	public void test_partition1() {
		boolean bool = false;
		bool |= containsString(emp[0], ws.workingEmployees(1, 2));
		bool |= containsString(emp[1], ws.workingEmployees(1, 2));
		assertTrue(bool);
		assertTrue(verifyHours(2, 2, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 2));
	}

	@Test
	public void test_partition2() {
		assertNull(ws.workingEmployees(4, 2));
		assertTrue(verifyHours(2, 2, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 2));
	}

	// STARTTIME = 0
	@Test
	public void test_border1() {
		boolean bool = false;
		bool |= containsString(emp[0], ws.workingEmployees(0, 2));
		bool |= containsString(emp[1], ws.workingEmployees(0, 2));
		assertTrue(bool);
		assertTrue(verifyHours(2, 2, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 2));
	}

	// STARTTIME = ENDTIME
	@Test
	public void test_border2() {
		boolean bool = false;
		bool |= containsString(emp[0], ws.workingEmployees(2, 2));
		bool |= containsString(emp[1], ws.workingEmployees(2, 2));
		assertFalse(bool);
		assertTrue(verifyHours(2, 2, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 2));
	}

	// STARTTIME = ENDTIME+1
	@Test
	public void test_border3() {
		assertNull(ws.workingEmployees(3, 2));
		assertTrue(verifyHours(2, 2, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 2));
	}

	// STARTTIME = MAX_INT
	@Test
	public void test_border4() {
		assertNull(ws.workingEmployees(Integer.MAX_VALUE, 2));
		assertTrue(verifyHours(2, 2, 1, 1));
		assertTrue(verifyHours(2, 0, 0, 0));
		assertTrue(verifyHours(2, 0, 2, 2));
	}

	private boolean containsString(String search, String[] array) {
		for (String content : array)
			if (content.equals(search))
				return true;
		return false;
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
}
