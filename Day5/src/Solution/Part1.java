package Solution;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Part1 {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String test = "abc";
		String actual = "abbhdwsy";
		
		System.out.println("test: " + String.join("", decode(test)));
		System.out.println("actual: " + String.join("", decode(actual)));
	}

	static String[] decode(String prefix) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		String[] result = new String[8];
		int suffix = 0;
		boolean isFull = false;
		while (!isFull) {
			m.update((prefix + suffix).getBytes());
			byte[] digest = m.digest();
			if (digest[0] == 0 && digest[1] == 0 && (digest[2] & 0xf8) == 0
					&& (result[digest[2] & 0x0f] == null)) {
				result[digest[2] & 0x0f] = Integer.toHexString((digest[3] & 0xf0) >>> 4);
				isFull = !Arrays.stream(result).anyMatch(str -> str == null);
			}
			suffix++;
			m.reset();
		}
		return result;
	}
}
