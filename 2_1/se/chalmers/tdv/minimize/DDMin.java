package se.chalmers.tdv.minimize;

import java.util.Arrays;


public class DDMin<E> implements Minimizer<E> {
	
	
	

	
	// Requires : s is non-null and chunk < nrChunks and nrChunks <= s.length()
	// Ensures : Gives a string such that if we divide the input string in n chunks
    //           the output is the input string with the chunk with index 
	//           chunk removed
	private E[] removeChuck(Class<E> clazz, E[] s,int nrChucks, int chunk){
		int chunkLength = s.length / nrChucks;
		int start = chunk * chunkLength;
		int end = Util.min (s.length, start + chunkLength);
		return new Util<E>().concat(clazz, Arrays.copyOfRange(s, 0, start), Arrays.copyOfRange(s, end, s.length));
		
	}
	
	

	// Requires: !t.test(input) and 
	//           input is non-null and
	//            nrChunks >= min(1, input.length) and
	//            nrChunks <= input.length
	// Ensures: A subsequence res of input, such 
	//          that !t.test(res) and res is 1-minimal
	private E[] ddMin(Class<E> clazz, Test<E[]> t, E[] input, int nrChucks) {
		for(int i = 0 ; i < nrChucks ; i++){
			E[] withoutChunk = removeChuck(clazz,input, nrChucks, i);
			if(!t.test(withoutChunk)){
				 return ddMin(clazz, t,withoutChunk,nrChucks-1);
			} 
		}
		if(nrChucks == input.length) return input; 
		return ddMin(clazz, t,input, Util.min(2*nrChucks, input.length));
	}
	
	
	// Requires: !t.test(input) and 
	//           input is non-null and
	// Ensures: A subsequence res of input, such 
	//          that !t.test(res) and res is 1-minimal
	public E[] minimize(Class<E> clazz, Test<E[]> t, E[] input) {
		E[] res = ddMin(clazz, t,input,Util.min(input.length,1));
		return res;
	}

	


	
}
