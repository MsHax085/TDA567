package se.chalmers.tdv.minimize;

public interface Minimizer<E> {
	
	E[] minimize(Class<E> clazz, Test<E[]> t, E[] input) ;
}
