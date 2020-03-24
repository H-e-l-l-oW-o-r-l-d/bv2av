import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class BV2AV {
	private static final char[] table = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF".toCharArray();
	private static final int[] s = { 11, 10, 3, 8, 4, 6, 2, 9, 5, 7 };
	private static final Map<Character, BigInteger> tr = new HashMap<Character, BigInteger>();

	private static final BigInteger xor = new BigInteger("177451812");
	private static final BigInteger add = new BigInteger("100618342136696320");
	private static final BigInteger FIFTY_EIGHT = new BigInteger("58");

	static {
		for (int i = 0; i < 58; i++) {
			tr.put(table[i], new BigInteger(Integer.toString(i)));
		}
	}

	public static String encode(long av) {
		return encode(new BigInteger(Long.toString(av)));
	}

	public static String encode(BigInteger av) {
		av = av.xor(xor).add(add);
		StringBuilder ret = new StringBuilder(12).append("BV          ");

		for (int i = 0; i < 10; i++) {
			int num = av.divide(FIFTY_EIGHT.pow(i)).divideAndRemainder(FIFTY_EIGHT)[1].intValue();
			ret.setCharAt(s[i], table[num]);
		}
		return ret.toString();
	}

	public static BigInteger decode(String bv) {
		BigInteger av = BigInteger.ZERO;
		char[] bva = bv.toCharArray();
		for (int i = 0; i < 10; i++) {
			av = av.add(tr.get(bva[s[i]]).multiply(FIFTY_EIGHT.pow(i)));
		}
		return av.subtract(add).xor(xor);
	}
}