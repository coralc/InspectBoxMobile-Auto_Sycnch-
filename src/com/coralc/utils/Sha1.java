package com.coralc.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1 {
	public static String convert(byte[] convertme) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		byte[] b = md.digest(convertme);

		String result = "";
		for (int i = 0; i < b.length; ++i) {
			result = result
					+ Integer.toString((b[i] & 0xFF) + 256, 16).substring(1);
		}
		return result;
	}
}