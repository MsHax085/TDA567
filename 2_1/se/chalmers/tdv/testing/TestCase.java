package se.chalmers.tdv.testing;

public class TestCase<Action> {

	final Action[] prefix, postfix;

	final Action[] commandsLeft, commandsRight;
	
	public TestCase(Action[] prefix, 
			Action[] commandsLeft, Action[] commandsRight, Action[] postfix) {
		super();
		this.prefix = prefix;
		this.postfix = postfix;
		this.commandsLeft = commandsLeft;
		this.commandsRight = commandsRight;
	}
}
