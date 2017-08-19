package com.example.democlassloadercontext.utils;

import org.springframework.context.ApplicationContext;

public class ContextUtils {

	public static String treeHtml(ApplicationContext context) {

		StringBuilder result = new StringBuilder();

		result.append("<ul>");

		result.append("<li>" + context.toString() + "</li>");

		while (context.getParent() != null) {
			context = context.getParent();
			result.append("<li>" + context.toString() + "</li>");
		}

		result.append("</ul>");
		return result.toString();
	}

}
