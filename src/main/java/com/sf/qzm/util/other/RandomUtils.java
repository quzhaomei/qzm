package com.sf.qzm.util.other;

import java.util.Random;

public class RandomUtils {
	public static final char[] RANDOM_CHAR_ARR = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'k',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '6', '7', '8', '1', '2', '3', '4', '5' };
	private static final Random random=new Random();
	
	public static String genertorRandomCode(int count) {
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < count; i++) {
			result.append(RANDOM_CHAR_ARR[random.nextInt(RANDOM_CHAR_ARR.length)]);
		}
		return result.toString();
	}
}
