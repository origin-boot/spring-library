package com.origin.library.infrastructure.utils;

public class TimeUtils {
	public static long getUnixTimestamp() {
		return System.currentTimeMillis() / 1000L;
	}
}
