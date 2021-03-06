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
			tr.put(Character.valueOf(table[i]), BigInteger.valueOf(i));
		}
	}

	public static String encode(long av) {
		return encode(BigInteger.valueOf(av));
	}

	public static String encode(BigInteger av) {
		av = av.xor(xor).add(add);
		char[] ret = "BV          ".toCharArray();

		for (int i = 0; i < 10; i++) {
			int num = av.divide(FIFTY_EIGHT.pow(i)).divideAndRemainder(FIFTY_EIGHT)[1].intValue();
			ret[s[i]] = table[num];
		}
		return new String(ret);
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