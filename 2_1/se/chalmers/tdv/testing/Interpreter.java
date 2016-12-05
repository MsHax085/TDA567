package se.chalmers.tdv.testing;

public interface Interpreter<Action,Output> {

	Output interpret(Action[] actions);
	String shrinkLevelName();
	int getShrinkLevel();
	void setShrinkLevel(int level);
	String getHeader(String name);
	
}
