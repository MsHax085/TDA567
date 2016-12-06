package se.chalmers.tdv.hashtable;

import java.util.Arrays;

public class LLHashTable {

	private class HashEntry {
		public final int key;
		public int value;
		public HashEntry next;

		public HashEntry(int key, int value, HashEntry next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public String toString() {
			return "(" + key + ", " + value + ") :: "
					+ (next != null ? next.toString() : "null");
		}

		private boolean contains(int key, int value) {
			if (this.key == key && this.value == value)
				return true;
			if (this.next == null)
				return false;
			return this.next.contains(key, value);
		}

		private boolean containedBy(HashEntry other) {
			if (other == null)
				return false;

			if (!other.contains(key, value))
				return false;

			if (this.next != null)
				return this.next.containedBy(other);

			return true;
		}

		public boolean equals(Object o) {
			if (!(o instanceof HashEntry))
				return false;
			HashEntry other = (HashEntry) o;

			return this.containedBy(other) && other.containedBy(this);
		}
	}

	private HashEntry[] buffer;

	private int nrBuckets;

	public LLHashTable(int nrBuckets) throws IllegalArgumentException {
		if (nrBuckets < 1)
			throw new IllegalArgumentException();
		this.nrBuckets = nrBuckets;
		this.buffer = new HashEntry[nrBuckets];
	}

	public void put(int key, int value) {
		int index = hashOf(key);
		this.buffer[index] = new HashEntry(key, value, this.buffer[index]);
	}

	private HashEntry find(int key) {
		int index = hashOf(key);
		HashEntry el = this.buffer[index];
		while (el != null && el.key != key)
			el = el.next;
		return el;
	}

	public boolean contains(int key) {
		return find(key) != null;
	}

	public Integer get(int key) throws IllegalArgumentException {
		HashEntry found = find(key);
		if (found == null) {
			return null;
		} else {
			return found.key;
		}
	}

	public void remove(int key) {
	int index = hashOf(key);
	HashEntry el = this.buffer[index];
	while (el != null) {
		if(el.key == key){
			this.buffer[index] = el.next;
			return;
		}
		else if( el.next != null && el.next.key == key) {
			el.next = el.next.next;
			this.buffer[index].next = el;
			return;
		}
		el = el.next;
	}
	}

	public String toString() {
		return "{ nrBuckets: " + this.nrBuckets + ", buffer: "
				+ Arrays.toString(this.buffer) + "}";
	}

	public boolean equals(Object o) {
		if (!(o instanceof LLHashTable))
			return false;
		LLHashTable other = (LLHashTable) o;

		if (other.nrBuckets != this.nrBuckets
				|| this.buffer.length != other.buffer.length)
			return false;

		return Arrays.equals(this.buffer, other.buffer);
	}

	private int hashOf(int key) {
		return Math.abs(key) % this.nrBuckets;
	}
}
