package se.chalmers.tdv.minimize;

import java.lang.reflect.Array;


public class Util<E> {

	public E[] concat(Class<E> clazz, E[] left, E[] right){
		@SuppressWarnings("unchecked")
		E[] res = (E[]) Array.newInstance(clazz, left.length + right.length);
		for(int i = 0 ; i < left.length ; i++){
			res[i] = left[i];
		}
		for(int i = 0 ; i < right.length ; i++){
			res[i + left.length] = right[i];
		}
		return res;
	}
	
	
	public static int min(int a, int b) {
		return a <= b ? a : b;
	}
	
	public static int max(int a, int b) {
		return a <= b ? b : a;
	}

}
