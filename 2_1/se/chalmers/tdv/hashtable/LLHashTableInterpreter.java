package se.chalmers.tdv.hashtable;

import java.util.ArrayList;
import se.chalmers.tdv.minimize.Tuple;
import se.chalmers.tdv.testing.Interpreter;

public class LLHashTableInterpreter implements Interpreter<LLHashTableAction, Tuple<LLHashTable,ArrayList<Integer>>> {

	private int nrBuckets;
	
	public LLHashTableInterpreter(int nrBuckets) {
		this.nrBuckets = nrBuckets;
	}
	
	@Override
	public Tuple<LLHashTable, ArrayList<Integer>> interpret(
			LLHashTableAction[] actions) {
		LLHashTable t = new LLHashTable(nrBuckets);
		ArrayList<Integer> output = new ArrayList<>();
		for(LLHashTableAction a : actions){
			switch (a.type) {
			case PUT:
				t.put(a.key, a.value);
				break;
			case REMOVE:
				t.remove(a.key);
				break;
			case GET:
				output.add(t.get(a.key));
				break;
			case OUTPUT:
				output.add(a.value);
				break;
			}
		}
		return new Tuple<LLHashTable, ArrayList<Integer>>(t, output);
	}

	@Override
	public String shrinkLevelName() {
		return "Number of buckets";
	}

	@Override
	public int getShrinkLevel() {
		return nrBuckets;
	}

	@Override
	public void setShrinkLevel(int level) {
		this.nrBuckets = level;
		
	}

	@Override
	public String getHeader(String name) {
		return "LLHashTable " + name + "= new LLHashTable(" + nrBuckets +");";
	}

}
