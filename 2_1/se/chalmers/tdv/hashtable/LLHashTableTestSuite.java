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
	
	

	// Java generic and arrays = headache
	static final Object[] allPropertiesObjects = new Object[]
			{ commutePut };
	
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

