package com.origin.library.infrastructure.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
	public static String getStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String stackTrace = sw.toString();
		return stackTrace;
	}

	public static String getStackTrace(Exception e, boolean removeNewLine) {
		return getStackTrace(e, removeNewLine, -1);
	}

	public static String getStackTrace(Exception e, boolean removeNewLine, int length) {
		String stackTrace = ExceptionUtils.getStackTrace(e);
		if (removeNewLine) {
			stackTrace = stackTrace.replaceAll(System.lineSeparator(), " \\\\n ");
		}
		if (length > 0) {
			stackTrace = stackTrace.substring(0, length);
		}
		return stackTrace;
	}
}
