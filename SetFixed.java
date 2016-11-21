package labb1;
import java.util.ArrayList;

public class SetFixed {
	private ArrayList<Integer> a;

	public SetFixed() {
		a = new ArrayList<Integer>();
	}

	public int[] toArray() {
		int[] ia = new int[a.size()];
		for (int i = 0; i < ia.length; i++) {
			ia[i] = a.get(i);
		}
		return ia;
	}

	public void insert(int x) {
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > x) {
				a.add(i, x);
				return;
			} else {
				if (a.get(i) == x) {
					return;
				}
			}
		}
		a.add(x);
	}

	public boolean member(int x) {
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) > x) {
				return false;
			} else {
				if (a.get(i) == x) {
					return true;
				}
			}
		}
		return false;
	}

	public void section(SetFixed s) {
		for (int i = 0, j = 0; i < a.size() && j < s.a.size();) {
			if (a.get(i).equals(s.a.get(j))) {
				a.remove(i);
				j++;
			} else {
				if (a.get(i) < s.a.get(j)) {
					i++;
				} else {
					j++;
				}
			}
		}
	}

	public boolean containsArithTriple() {
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j <= i; j++) {
				if (member(2 * a.get(i) - a.get(j)))
					return true;
			}
		}
		return false;
	}
}