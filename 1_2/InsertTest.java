package labb1;

import static org.junit.Assert.*;
import org.junit.*;

public class InsertTest {

	private SetFixed set;
	
	@Before public void init() {
		set = new SetFixed();
	}
	
	// 1. Statement coverage
	@Test public void insertToEmpty() {
		set.insert(8);
		
		int[] arr = set.toArray();
		assertTrue(arr.length == 1);
		assertTrue(arr[0] == 8);
	}
	
	// 2. Statement coverage
	@Test public void insertToNonEmpty() {
		set.insert(12);
		set.insert(8);
		
		int[] arr = set.toArray();
		assertTrue(arr.length == 2);
		assertTrue(arr[0] == 8);
		assertTrue(arr[1] == 12);
	}
	
	// 3. Statement coverage
	@Test public void insertExisting() {
		set.insert(12);
		set.insert(12);
		
		int[] arr = set.toArray();
		assertTrue(arr.length == 1);
		assertTrue(arr[0] == 12);
	}
	
	// 1. Branch coverage
	@Test public void insertMany() {
		set.insert(4);
		set.insert(6);
		set.insert(8);
		
		int[] arr = set.toArray();
	}
}
