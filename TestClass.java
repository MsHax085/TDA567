import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class TestClass {

	private WorkSchedule ws;
	private String Employees[];

	@Before
	public void setUp() throws Exception {
		// 5 hours per day, 0 employees
		ws = new WorkSchedule(5);// Hours: 0 - 4
		ws.setRequiredNumber(2, 0, 4);
	}

	/*
	 * requires: employee is a non-null string ensures: if starttime < 0 or
	 * endtime >= size or starttime > endtime then returns false and the
	 * schedule is unchanged otherwise if for any hour in the interval starttime
	 * to endtime the length of workingEmployees is equal to requiredNumber then
	 * returns false and the schedule is unchanged otherwise if for any hour in
	 * the interval starttime to endtime there is a string in workingEmployees
	 * which equal employee then returns false and the schedule is unchanged
	 * otherwise returns true, for i between starttime and endtime,
	 * workingEmployees contain a string equal to employee and the rest of the
	 * schedule is unchanged
	 * 
	 * @Test public void testAddWorkingPeriod() { // if (starttime < 0) => False
	 * assertFalse(ws.addWorkingPeriod("Victor", -1, 0));
	 * assertTrue(verifyAllHoursClear());
	 * 
	 * // if (endtime >= size) => False
	 * assertFalse(ws.addWorkingPeriod("Victor", 0, 6));// Can also be 5 :)
	 * assertTrue(verifyAllHoursClear());
	 * 
	 * // if (starttime > endtime) => False
	 * assertFalse(ws.addWorkingPeriod("Victor", 6, 2));
	 * assertTrue(verifyAllHoursClear()); }
	 */

	/**
	 * requires: employee is a non-null string ensures: if starttime < 0 returns
	 * false and the schedule is unchanged
	 */
	@Test
	public void testStartLTzero() {
		assertFalse(ws.addWorkingPeriod("Victor", -1, 0));
		assertTrue(verifyAllHours(2,0));
	}

	/*
	 * requires: employee is a non-null string ensures: endtime >= size returns
	 * false and the schedule is unchanged
	 */
	@Test
	public void testEndMoreEqual() {
		assertFalse(ws.addWorkingPeriod("Victor", 0, 6));// Can also be 5 :)
		assertTrue(verifyAllHours(2,0));
	}

	/*
	 * starttime > endtime then returns false and the schedule is unchanged
	 */
	@Test
	public void testStartGTend() {
		assertFalse(ws.addWorkingPeriod("Victor", 6, 2));
		assertTrue(verifyAllHours(2,0));
	}

	@Test
	public void testAddonMax() {
		for (int i = 0; i < 5; i++) {
			assertFalse(ws.addWorkingPeriod("Victor", i, i) &&
					ws.addWorkingPeriod("Richard", i, i) &&
					ws.addWorkingPeriod("Anna", i, i));
		}
		assertTrue(verifyAllHours(2,2));
	}

	@Test
	public void testContainsEmployee() {
		for (int i = 0; i < 5; i++) {
			assertFalse(ws.addWorkingPeriod("Victor", i, i) && ws.addWorkingPeriod("Victor", i, i));
			System.out.println(ws.readSchedule(i).requiredNumber);
			for(String emp: ws.readSchedule(i).workingEmployees)
				System.out.println(emp);
		}
		assertTrue(verifyAllHours(2, 1));
	}
	
	@Test
	public void testAddWorkingPeriod(){
		ws.addWorkingPeriod("Victor", 0, 4);
		for(int i=0; i < 5; i++){
			String Employees[] = ws.readSchedule(i).workingEmployees;
			assertTrue(Employees[0].equals("Victor"));
		}
		assertTrue(verifyAllHours(2,1));
	}

	private boolean verifyAllHours(int reqNum, int size) {
		for (int i = 0; i < 5; i++) {
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

	/*
	 * requires: starttime >= 0 and endtime < size ensures: if starttime <=
	 * endtime then returns an array with distinct strings -- a string appears
	 * in the return array if and only if it appears in the workingEmployees of
	 * at least one hour in the interval starttime to endtime otherwise returns
	 * an empty array and in either case the schedule is unchanged
	 */
	@Test
	public void testWorkingEmployees() {
		assertTrue(true);
	}
}