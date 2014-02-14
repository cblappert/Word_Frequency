package phaseB;
import providedCode.Hasher;


public class StringHasher implements Hasher<String> {
	
	@Override
	public int hash(String s) {
		int val = 0;
		int multiple = 1;
		for(int i = 0; i < s.length(); i++) {
			val += s.charAt(i) * multiple;
			multiple *= 128;
		}
		return val;
	}
}
