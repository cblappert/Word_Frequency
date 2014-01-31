package phaseA;
import providedCode.*;


/**
 * TODO: Replace this comment with your own as appropriate.
 * This comparator is used by the provided code for both data-counters and 
 * sorting. Because of how the output must be sorted in the case of ties, 
 * your implementation should return a negative number when the first argument
 * to compare comes first alphabetically. Do not use any String comparison 
 * provided in Java's standard library; the only String methods you should 
 * use are length and charAt.
 */

/**
 **   @author Christopher Blappert, Michael Mitasev
 **	  1/31/14
 **	  CSE 332 AB
 **	  Hye Kim
 **   Programming project 2a
 **
 **	  This class provides an implementation of a simple
 **   comparator for strings. Shorter strings and capital
 **   letters will come first
 **/

public class StringComparator implements Comparator<String>{
	
	// Pre: strings passed not null else throws IllegalArgumentException
	// Post: returns the comparison of the two strings - capitol letters 
	// 		 will come before lower case letters, and shorter strings
	// 		 will come before longer ones.
	@Override
	public int compare(String s1, String s2) {
		if(s1 == null || s2 == null) {
			throw new IllegalArgumentException();
		}
		int length = Math.min(s1.length(), s2.length());
		for(int i = 0; i < length; i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			if(c1 != c2) {
				return c1 - c2;
			}
		}
		return s1.length() - s2.length();
	}
}
