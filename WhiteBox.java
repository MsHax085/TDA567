package labb1;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import labb1.Set.*;

public class WhiteBox {

	private Set testSet = new Set();
	private int[] source = {1,2,4,5};
	
	@Before
	public void setUp(){
		testSet.insert(1);
		testSet.insert(2);
		testSet.insert(4);
		testSet.insert(5);
	}
	
	@Test
	public void SCBCinsert(){
		assertArrayEquals("Test", source, testSet.toArray());
		testSet.insert(3);
		testSet.insert(6);
		testSet.insert(4);
		int[] x = {1,2,3,4,5,6};
		//for(int i : testSet.toArray())
		//	System.out.println(i);
		assertArrayEquals("Test", x, testSet.toArray());
	}
	
	@Test public void SCBCmember(){
		assertArrayEquals("Test", source, testSet.toArray());
		assertFalse(testSet.member(0));
		assertTrue(testSet.member(4));
		assertFalse(testSet.member(6));
		assertArrayEquals("Test", source, testSet.toArray());
	}
	
	@Test public void SCBCsection(){
		int[] checkA ={0, 2, 4};
		int[] checkSection = {1, 5};
		Set setA = new Set();
		setA.insert(0);
		setA.insert(2);
		setA.insert(4);
		assertArrayEquals("Test", setA.toArray(), checkA);
		testSet.section(setA);
		//for(int x : testSet.toArray())
		//	System.out.println("Set: " + x );
		assertArrayEquals("Test", checkSection, testSet.toArray());

	}
	
	@Test public void SCBCarth(){
		Set setA = new Set();
		setA.insert(100);
		setA.insert(2);
		setA.insert(11);
		assertTrue(setA.containsArithTriple());
	}
}
