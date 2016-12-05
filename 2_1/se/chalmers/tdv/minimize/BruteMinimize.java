package se.chalmers.tdv.minimize;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BruteMinimize<E> implements Minimizer<E>{
	
	
	
	// Requires: !t.test(input) and 
	//           input is non-null and
	// Ensures: A (global) minimal subsequence res of input, such 
	//          that !t.test(res) 
	public E[] minimize(Class<E> clazz, Test<E[]> t, E[] input) {
		for(int i = 0 ; i < input.length; i++){
			E[] res = trySubsequence(clazz, t, new Stack<Integer>(), input, 0, i);
			if(res != null){
				return res;
			}
		}
		return input;
	}
	
	E[] trySubsequence(Class<E> clazz, Test<E[]> t, Stack<Integer> prefix, E[] input, int start, int nrToPick){
		if(nrToPick == 0){
			E[] prefixArr = (E[]) Array.newInstance(clazz, prefix.size());
			for(int i = 0 ; i < prefix.size(); i++){
				prefixArr[i] = input[prefix.get(i)];
			}
			return t.test(prefixArr) ? null: prefixArr;
		}
		for(int i = start ; i <= input.length - nrToPick; i++){
			prefix.push(i);
			E[] res = trySubsequence(clazz, t, prefix, input, i+1, nrToPick-1);
			if(res != null){
				return res;
			}
			prefix.pop();
		}
		return null;
	}
	
	


}
