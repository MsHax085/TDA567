package se.chalmers.tdv.hashtable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import se.chalmers.tdv.hashtable.LLHashTableAction.ActionType;
import se.chalmers.tdv.minimize.Tuple;
import se.chalmers.tdv.testing.Property;
import se.chalmers.tdv.testing.TestCase;
import se.chalmers.tdv.testing.TestRunner;

@SuppressWarnings("unchecked")
public class LLHashTableTestSuite {
	// set this to true  to generate code to past into a file to use the debugger
	static final boolean PRINT_CODE = false; 
	static final int MAX_PREFIX_LENGTH = 5;
	static final int MAX_NR_BUCKETS = 3;
	static final int MAX_POSTFIX_LENGTH = 5;
	static final int MAX_KEY = LLHashTableAction.MAX_KEY;
	static final int MAX_NR_TESTS = 1000;
	static final int MAX_RANDOM_INT = 10;
	static Integer A = 1;
	static Integer NOT_A = 2;
	static Integer B = 3;
	static Integer C = 4;
	static Integer D = 5;
	
	// Action generator methods:

	public static LLHashTableAction put(int key, int value) {
		return new LLHashTableAction(ActionType.PUT, key, value);
	}

	public static LLHashTableAction remove(int key) {
		return new LLHashTableAction(ActionType.REMOVE, key, 0);
	}

	public static LLHashTableAction get(int key) {
		return new LLHashTableAction(ActionType.GET, key, 0);
	}

	public static LLHashTableAction output(Integer value) {
		return new LLHashTableAction(ActionType.OUTPUT, 0, value);	}
	
	
	
	/**
	 * Testing that put(Ka,Va), put(Kb,Vb) <==> put(Kb,Vb), put(Ka, Va) iff Ka
	 * != Kb.
	 */
	
	static final Property<LLHashTableAction> commutePut = new Property<>("commutePut", true, true, 
			new LLHashTableAction[]{ put(A,C), put(NOT_A, D)}, 
			new LLHashTableAction[]{ put(NOT_A,D), put(A, C)});
	
	/**
	* Testing that put(Ka,Va), put(Ka,Vb), put(Ka,Vc), remove(Kb) <==> put(Ka,Vc) iff (Vc != Vb) || (Vc !=Va)
	*/
	static final Property<LLHashTableAction> put = new Property<>("put", false, true, 
		new LLHashTableAction[]{ put(1,1), put(1,2), put(1,3), remove(3)}, 
		new LLHashTableAction[]{ put(1,3)});
	
	/**
	 * Testing that put(Ka,Va), put(Kb,Vb), remove(Ka) <==> put(Kb,Vb) iff Ka != Kb
	 */
	static final Property<LLHashTableAction> removeUnmodified = new Property<>("removeUnmodified", false, true, 
			new LLHashTableAction[]{ put(1,1), put(2,2), remove(1)}, 
			new LLHashTableAction[]{ put(2,2)});
	
	/**
	 * Testing that put(Ka,Va), put(Kb,Vb), remove(Ka) <==> put(Kb,Vb) iff Ka != Kb
	 *while value of key:1 variated
	 */
	static final Property<LLHashTableAction> removeModified = new Property<>("removeModified", true, true, 
			new LLHashTableAction[]{ put(1,1), put(2,2), remove(1)}, 
			new LLHashTableAction[]{ put(2,2), remove(1)});
	
	/**
	* Testing that put(Ka,Va), put(Kb, Vb) <==> put(Ka,Va) iff (Ka == Kb) && (Va == Vb) 
	*/
	static final Property<LLHashTableAction> addDuplicates = new Property<>("addDuplicates", true, true, 
			new LLHashTableAction[]{ put(1,1), put(1,1)}, 
			new LLHashTableAction[]{ put(1,1)});
	
	/**
	* Testing that put(Ka,Va), get(Ka) <==> put(Ka,Va), output(Ka)
	*/
	static final Property<LLHashTableAction> findZ = new Property<>("findZ", true, true, 
			new LLHashTableAction[]{ put(1,1), get(1)}, 
			new LLHashTableAction[]{ put(1,1), output(1)});

	// Java generic and arrays = headache
	static final Object[] allPropertiesObjects = new Object[]
			{ commutePut, put, removeUnmodified, removeModified, addDuplicates, findZ  };
	
	static final Property<LLHashTableAction>[] allProperties;
	
	static {
		allProperties = (Property<LLHashTableAction>[]) Array.newInstance(Property.class, allPropertiesObjects.length);
		for(int i = 0 ; i < allProperties.length; i++){
			allProperties[i] = (Property<LLHashTableAction>) allPropertiesObjects[i];
		}
	}
	

	static TestCase<LLHashTableAction> generate(Property<LLHashTableAction> property){
		LLHashTableAction[] prefix  = property.prefix ? LLHashTableAction.randomActions(MAX_PREFIX_LENGTH)   : new LLHashTableAction[]{};
		LLHashTableAction[] postfix = property.postfix ? LLHashTableAction.randomActions(MAX_POSTFIX_LENGTH) : new LLHashTableAction[]{};
		return new TestCase<>(prefix, property.commandsLeft, property.commandsRight, postfix);
	}
	
	static void runTest(Property<LLHashTableAction> property){
		
		Random rand = new Random();
		
		System.out.println("Testing property: " + property.name);
		for(int i = 0 ; i < MAX_NR_TESTS; i++){
			int nrBuckets = rand.nextInt(MAX_NR_BUCKETS) + 1;
			TestRunner<LLHashTableAction, Tuple<LLHashTable,ArrayList<Integer>>> runner = new TestRunner<>(LLHashTableAction.class,new LLHashTableInterpreter(nrBuckets));
			A = new Integer(rand.nextInt(MAX_KEY));
			NOT_A = new Integer(rand.nextInt(MAX_KEY));
			if(A.compareTo(NOT_A) == 0) NOT_A = (A+1) % MAX_KEY;
			B = new Integer(rand.nextInt(MAX_KEY));
			C = new Integer(rand.nextInt(MAX_KEY));
			if(!runner.runTestPrint(PRINT_CODE,generate(property))){
				return;
			}
		}
		System.out.println(MAX_NR_TESTS + " test succeeded");
	}
	
	/** Runs until all test are successful or a bug is found. */
	static void runAllTests() {
		for(Property<LLHashTableAction> prop : allProperties){
			if(prop!=null){
				runTest(prop);
			}
		}
	}
	
	public static void main(String[] args){
		runAllTests();
	}
	
}

