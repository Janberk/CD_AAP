package de.canberk.uni.cd_aap.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import android.util.Log;
import de.canberk.uni.cd_aap.BuildConfig;

public class Logger { // http://stackoverflow.com/questions/2018263/android-logging

	public enum Level {
		error, warn, info, debug, trace
	}

	private static final String DEFAULT_TAG = "Project";

	private static final Level CURRENT_LEVEL = BuildConfig.DEBUG ? Level.trace
			: Level.info;

	private static boolean isEnabled(Level l) {
		return CURRENT_LEVEL.compareTo(l) >= 0;
	}

	static {
		Log.i(DEFAULT_TAG, "log level: " + CURRENT_LEVEL.name());
	}

	private String classname = DEFAULT_TAG;

	public void setClassName(Class<?> c) {
		classname = c.getSimpleName();
	}

	public String getClassname() {
		return classname;
	}

	public boolean isError() {
		return isEnabled(Level.error);
	}

	public boolean isWarn() {
		return isEnabled(Level.warn);
	}

	public boolean isInfo() {
		return isEnabled(Level.info);
	}

	public boolean isDebug() {
		return isEnabled(Level.debug);
	}

	public boolean isTrace() {
		return isEnabled(Level.trace);
	}

	public void error(Object... args) {
		if (isError())
			Log.e(buildTag(), build(args));
	}

	public void warn(Object... args) {
		if (isWarn())
			Log.w(buildTag(), build(args));
	}

	public void info(Object... args) {
		if (isInfo())
			Log.i(buildTag(), build(args));
	}

	public void debug(Object... args) {
		if (isDebug())
			Log.d(buildTag(), build(args));
	}

	public void trace(Object... args) {
		if (isTrace())
			Log.v(buildTag(), build(args));
	}

	public void error(String msg, Throwable t) {
		if (isError())
			error(buildTag(), msg, stackToString(t));
	}

	public void warn(String msg, Throwable t) {
		if (isWarn())
			warn(buildTag(), msg, stackToString(t));
	}

	public void info(String msg, Throwable t) {
		if (isInfo())
			info(buildTag(), msg, stackToString(t));
	}

	public void debug(String msg, Throwable t) {
		if (isDebug())
			debug(buildTag(), msg, stackToString(t));
	}

	public void trace(String msg, Throwable t) {
		if (isTrace())
			trace(buildTag(), msg, stackToString(t));
	}

	private String buildTag() {
		String tag;
		if (BuildConfig.DEBUG) {
			StringBuilder b = new StringBuilder(20);
			b.append(getClassname());

			StackTraceElement stackEntry = Thread.currentThread()
					.getStackTrace()[4];
			if (stackEntry != null) {
				b.append('.');
				b.append(stackEntry.getMethodName());
				b.append(':');
				b.append(stackEntry.getLineNumber());
			}
			tag = b.toString();
		} else {
			tag = DEFAULT_TAG;
		}
		return tag;
	}

	private String build(Object... args) {
		if (args == null) {
			return "null";
		} else {
			StringBuilder b = new StringBuilder(args.length * 10);
			for (Object arg : args) {
				if (arg == null) {
					b.append("null");
				} else {
					b.append(arg);
				}
			}
			return b.toString();
		}
	}

	private String stackToString(Throwable t) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
		baos.toString();
		t.printStackTrace(new PrintStream(baos));
		return baos.toString();
	}
}