package com.example.democlassloadercontext.utils;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderUtils {

	public static String tree() {

		StringBuilder result = new StringBuilder();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		result.append(classLoader.toString());

		String prefix = "- ";
		while (classLoader.getParent() != null) {
			classLoader = classLoader.getParent();
			result.append(System.lineSeparator()).append(prefix).append(classLoader.toString());
			prefix = "-" + prefix;
		}

		return result.toString();
	}

	public static String urls(URLClassLoader classLoader) {
		StringBuilder result = new StringBuilder();
		result.append("ClassLoader urls: " + classLoader.toString());
		for (URL url : classLoader.getURLs()) {
			result.append(System.lineSeparator()).append(url.toString());
		}
		return result.toString();
	}
}
