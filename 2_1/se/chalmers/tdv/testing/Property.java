package se.chalmers.tdv.testing;


public class Property<Action> {
	
	public final String name;
	public final boolean prefix, postfix;
	public final Action[] commandsLeft, commandsRight;

	public Property(String name,boolean prefix, boolean postfix,
			Action[] commandsLeft, Action[] commandsRight) {
		this.name = name;
		this.prefix = prefix;
		this.postfix = postfix;
		this.commandsLeft = commandsLeft;
		this.commandsRight = commandsRight;
	}
	

}
