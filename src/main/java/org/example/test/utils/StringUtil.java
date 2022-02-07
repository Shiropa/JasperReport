package org.example.test.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtil {

	public static String convertToBn(String s) {
		return s
				.replaceAll("0", "০")
				.replaceAll("1", "১")
				.replaceAll("2", "২")
				.replaceAll("3", "৩")
				.replaceAll("4", "৪")
				.replaceAll("5", "৫")
				.replaceAll("6", "৬")
				.replaceAll("7", "৭")
				.replaceAll("8", "৮")
				.replaceAll("9", "৯");
	}
}
