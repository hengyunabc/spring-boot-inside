package com.example.demo404401.jsp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.WebResourceRoot.ResourceSetType;
import org.springframework.util.ResourceUtils;

/**
 * Add main class fat jar/exploded directory into tomcat ResourceSet.
 *
 * @author hengyunabc 2017-07-29
 *
 */
public class StaticResourceConfigurer implements LifecycleListener {

	private final Context context;

	StaticResourceConfigurer(Context context) {
		this.context = context;
	}

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
			URL location = this.getClass().getProtectionDomain().getCodeSource().getLocation();

			if (ResourceUtils.isFileURL(location)) {
				// when run as exploded directory
				String rootFile = location.getFile();
				if (rootFile.endsWith("/BOOT-INF/classes/")) {
					rootFile = rootFile.substring(0, rootFile.length() - "/BOOT-INF/classes/".length() + 1);
				}
				if (!new File(rootFile, "META-INF" + File.separator + "resources").isDirectory()) {
					return;
				}

				try {
					location = new File(rootFile).toURI().toURL();
				} catch (MalformedURLException e) {
					throw new IllegalStateException("Can not add tomcat resources", e);
				}
			}

			String locationStr = location.toString();
			if (locationStr.endsWith("/BOOT-INF/classes!/")) {
				// when run as fat jar
				locationStr = locationStr.substring(0, locationStr.length() - "/BOOT-INF/classes!/".length() + 1);
				try {
					location = new URL(locationStr);
				} catch (MalformedURLException e) {
					throw new IllegalStateException("Can not add tomcat resources", e);
				}
			}
			this.context.getResources().createWebResourceSet(ResourceSetType.RESOURCE_JAR, "/", location,
					"/META-INF/resources");

		}
	}
}