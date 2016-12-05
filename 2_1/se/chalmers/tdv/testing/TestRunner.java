package se.chalmers.tdv.testing;

import java.util.Arrays;
import java.util.NoSuchElementException;

import se.chalmers.tdv.minimize.BruteMinimize;
import se.chalmers.tdv.minimize.DDMin;
import se.chalmers.tdv.minimize.Minimizer;
import se.chalmers.tdv.minimize.Test;
import se.chalmers.tdv.minimize.Tuple;
import se.chalmers.tdv.minimize.Util;

public class TestRunner<Action,Output> {
	

	
	@SuppressWarnings("serial")
	static class CaughtException extends RuntimeException {

		final RuntimeException originalException;
		final boolean inLeft;
		
		public CaughtException(RuntimeException originalException, boolean inLeft) {
			super();
			this.originalException = originalException;
			this.inLeft = inLeft;
		}
	}

	public TestRunner( Class<Action> clazz,Interpreter<Action, Output> interpret) {
		super();
		this.minimize = new BruteMinimize<>();
		this.interpret = interpret;
		this.clazz = clazz;
	}

	final Minimizer<Action> minimize;
	final Interpreter<Action,Output> interpret;
	final Class<Action> clazz;
	

	Tuple<Output,Output> runTestOutput(TestCase<Action> testCase) {
		Util<Action> util = new Util<Action>();
		Action[] intructionsLeft = util.concat(clazz,util.concat(clazz, testCase.prefix,testCase.commandsLeft), testCase.postfix);
		Action[] intructionsRight = util.concat(clazz, util.concat(clazz,testCase.prefix,testCase.commandsRight), testCase.postfix);
		Output outLeft  = null;
		Output outRight = null;
		try{
			outLeft = interpret.interpret(intructionsLeft);
		} catch (RuntimeException e){
			throw new CaughtException(e, true);
		}
		try{
			outRight = interpret.interpret(intructionsRight);
		} catch (RuntimeException e){
			throw new CaughtException(e, false);
		}
		return new Tuple<Output,Output>(outLeft,outRight);
		
	}
	
	boolean runTest(TestCase<Action> testCase){
		Tuple<Output,Output> results = runTestOutput(testCase);
		return results.a.equals(results.b);
	}
	
	
	
	Action[] minimizePrefix(final TestCase<Action> testCase){
		return minimize.minimize(clazz, new Test<Action[]>() {


			public boolean test(Action[] prefix) {
				return runTest(
						new TestCase<Action>(
								prefix, 
								testCase.commandsLeft, testCase.commandsRight, 
								testCase.postfix)
						);
					
			}
			
		}, testCase.prefix);

	}
	

	Action[] minizePostfix(final TestCase<Action> testCase){
		return minimize.minimize(clazz, new Test<Action[]>() {


			public boolean test(Action[] postfix)  {
				return runTest(
						new TestCase<Action>(
								testCase.prefix, 
								testCase.commandsLeft, testCase.commandsRight, 
								postfix)
						);
					
			}
			
		}, testCase.postfix);
		
	}

	
	// runs the testcase, and if it fails returns a minimized variant, otherwise returns null
	TestCase<Action> runAndMinimize(boolean printCode,TestCase<Action> testCase){
		try{
			if(runTest(testCase)){
				return null;
			} else {
				
				
				System.out.print(" Bug found! Shrinking .. ");
				shrinkShrinkLevel(testCase);
				Action[] prefix = minimizePrefix(testCase);
				Action[] postfix = minizePostfix(
						 new TestCase<>(prefix, testCase.commandsLeft, testCase.commandsRight, testCase.postfix));
				System.out.println("Shrunk from a context of " + (testCase.prefix.length + testCase.postfix.length) + " steps, to " + (prefix.length + postfix.length) + " steps.");
				return new TestCase<>(prefix, testCase.commandsLeft, testCase.commandsRight, postfix);
			}
		} catch (CaughtException e){
			handleException(printCode,testCase, e);
			return null;

		}
		
	}

	private void shrinkShrinkLevel(TestCase<Action> testCase) {
		int shrinkLevel = interpret.getShrinkLevel();
		for(int i = 1 ; i <= shrinkLevel; i++){
			interpret.setShrinkLevel(i);
			if(!runTest(testCase)){
				return;
			}
		}
		
	}

	private void shrinkShrinkLevelException(Action[] all) {
		int shrinkLevel = interpret.getShrinkLevel();
		for(int i = 1 ; i <= shrinkLevel; i++){
			interpret.setShrinkLevel(i);
			if(!runForException(all)){
				return;
			}
		}
		
	}
	
	void printCode(String name , Action[] actions ){
		System.out.println("\t" + interpret.getHeader(name));
		for(Action a : actions){
			System.out.println("\t" + name + "." + a.toString() + ";");
		}
	}
	
	private void handleException(boolean printCode,TestCase<Action> testCase, CaughtException caught) {
		Util<Action> util = new Util<Action>();
		Action[] commandsFrom = caught.inLeft ? testCase.commandsLeft : testCase.commandsRight;
		Action[] all = 
				util.concat(clazz,util.concat(clazz, testCase.prefix,commandsFrom), testCase.postfix);
		System.out.print(" Exception bug found! Shrinking .. ");
		shrinkShrinkLevelException(all);
		Action[] minimal = minimizeExceptionFail(all);
		System.out.println("Shrunk from " + (all.length) + " steps, to " + (minimal.length) + " steps.");
		System.out.println(" " + interpret.shrinkLevelName() + "= " + interpret.getShrinkLevel());
		System.out.println(" " + Arrays.toString(minimal));
		if(printCode){
			System.out.println(" ------- Code -------- ");
			printCode("x", minimal);
			System.out.println(" --------------------- ");
		}
		try{
			interpret.interpret(minimal);
			throw new NoSuchElementException("Unexpected lack of exception!");
		} catch (Exception e){
			throw e;
		}
	}
	
	boolean runForException(Action[] all){
		try { 
			 interpret.interpret(all);
			 return true;
		 } catch (RuntimeException e){
			 return false;
		 }
	}
	
	private Action[] minimizeExceptionFail(Action[] all)  {
		BruteMinimize<Action> minimizer = new BruteMinimize<Action>();
		return minimizer.minimize(clazz, new Test<Action[]>() {


			public boolean test(Action[] all)  {
				return runForException(all);
					
			}
		}, all);
	}

	/** Prints a description of this test case to the standard out. */
	public void print(TestCase<Action> testCase) {
		Tuple<Output,Output> outputs = runTestOutput(testCase);
		System.out.println(" " + interpret.shrinkLevelName() + "= " + interpret.getShrinkLevel());
		if (testCase.prefix.length > 0)
			System.out
					.println("  Prefix:     " + Arrays.toString(testCase.prefix));

		System.out.println("  Commands 1: " + Arrays.toString(testCase.commandsLeft));
		System.out.println("  Commands 2: " + Arrays.toString(testCase.commandsRight));
		if (testCase.postfix.length > 0)
			System.out
					.println("  Suffix:     " + Arrays.toString(testCase.postfix));
		System.out.println("  Result 1:   " + outputs.a.toString());
		System.out.println("  Result 2:   " + outputs.b.toString());
	}


	public boolean runTestPrint(boolean printCode,TestCase<Action> testCase){
		TestCase<Action> res = runAndMinimize(printCode,testCase);
		if(res != null){
			print(res);
			if(printCode){
				System.out.println(" ------- Code -------- ");
				Util<Action> util = new Util<>();
				Action[] intructionsLeft = util.concat(clazz,util.concat(clazz, testCase.prefix,testCase.commandsLeft), testCase.postfix);
				Action[] intructionsRight = util.concat(clazz, util.concat(clazz,testCase.prefix,testCase.commandsRight), testCase.postfix);
				printCode("a", intructionsLeft);
				System.out.println();
				
				printCode("b", intructionsRight);
				System.out.println(" --------------------- ");
			}
			return false;
		} else {
			return true;
		}
	}
	
	
	
}
