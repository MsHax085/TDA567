package se.chalmers.tdv.hashtable;

import java.util.Random;

/**
 * Data object describing the modifying actions of a hash table.
 */
public class LLHashTableAction {

	/** Maximum size of the hash table under test. */
	public static final int MAX_NRBUCKETS = 5;

	/**
	 * Maximum value of the keys in the hash table. Recommended to keep low to
	 * get more interesting random behavior.
	 */
	public static final int MAX_KEY = 5;

	
	public enum ActionType {
		PUT, REMOVE, GET, OUTPUT
	};

	public final ActionType type;
	public final int key;
	public final Integer value;

	public LLHashTableAction(ActionType type, int key, Integer value) {
		this.type = type;
		this.key = key;
		this.value = value;
	}

	public String toString() {
		switch (this.type) {
		case PUT:
			return "put(" + this.key + ", " + this.value + ")";
		case REMOVE:
			return "remove(" + this.key + ")";
		case GET:
			return "get(" + this.key + ")";
		case OUTPUT:
			return "output(" + this.value + ")";
		default:
			throw new RuntimeException("Unknown action.");
		}
	}

	public static LLHashTableAction[] randomActions(int n) {
		Random rand = new Random();
		LLHashTableAction res[] = new LLHashTableAction[n];
		for (int i = 0; i < n; i++) {
			switch (rand.nextInt(3)) {
			case 0:
				res[i] = new LLHashTableAction(
						LLHashTableAction.ActionType.PUT,
						rand.nextInt(MAX_KEY), rand.nextInt(10));
				break;
			case 1:
				res[i] = new LLHashTableAction(
						LLHashTableAction.ActionType.REMOVE,
						rand.nextInt(MAX_KEY), 0);
				break;
			case 2:
				res[i] = new LLHashTableAction(
						LLHashTableAction.ActionType.GET,
						rand.nextInt(MAX_KEY), 0);
				break;
			}
		}
		return res;
	}

}
