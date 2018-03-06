

## java.lang.ArrayStoreException 分析


```
Caused by: java.lang.ArrayStoreException: sun.reflect.annotation.TypeNotPresentExceptionProxy
	at sun.reflect.annotation.AnnotationParser.parseClassArray(AnnotationParser.java:724) ~[na:1.8.0_112]
	at sun.reflect.annotation.AnnotationParser.parseArray(AnnotationParser.java:531) ~[na:1.8.0_112]
	at sun.reflect.annotation.AnnotationParser.parseMemberValue(AnnotationParser.java:355) ~[na:1.8.0_112]
	at sun.reflect.annotation.AnnotationParser.parseAnnotation2(AnnotationParser.java:286) ~[na:1.8.0_112]
	at sun.reflect.annotation.AnnotationParser.parseAnnotations2(AnnotationParser.java:120) ~[na:1.8.0_112]
	at sun.reflect.annotation.AnnotationParser.parseAnnotations(AnnotationParser.java:72) ~[na:1.8.0_112]
	at java.lang.Class.createAnnotationData(Class.java:3521) ~[na:1.8.0_112]
	at java.lang.Class.annotationData(Class.java:3510) ~[na:1.8.0_112]
	at java.lang.Class.getDeclaredAnnotations(Class.java:3477) ~[na:1.8.0_112]
	at org.springframework.boot.autoconfigure.ImportAutoConfigurationImportSelector.collectAnnotations(ImportAutoConfigurationImportSelector.java:144) ~[spring-boot-autoconfigure-2.0.0.RELEASE.jar:2.0.0.RELEASE]
	at org.springframework.boot.autoconfigure.ImportAutoConfigurationImportSelector.getAnnotations(ImportAutoConfigurationImportSelector.java:137) ~[spring-boot-autoconfigure-2.0.0.RELEASE.jar:2.0.0.RELEASE]
	at org.springframework.boot.autoconfigure.ImportAutoConfigurationImportSelector.getCandidateConfigurations(ImportAutoConfigurationImportSelector.java:76) ~[spring-boot-autoconfigure-2.0.0.RELEASE.jar:2.0.0.RELEASE]
	at org.springframework.boot.autoconfigure.AutoConfigurationImportSelector.selectImports(AutoConfigurationImportSelector.java:95) ~[spring-boot-autoconfigure-2.0.0.RELEASE.jar:2.0.0.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassParser.processImports(ConfigurationClassParser.java:586) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	... 21 common frames omitted
```

原因是`@ImportAutoConfiguration`它的实现会直接调用 `java.lang.Class.getDeclaredAnnotations`，当annotation上的类找不到时，就会抛出这个异常。

如果改用`@Import`，则抛出来的异常信息则可以清楚看到类找不到：

```
Caused by: java.io.FileNotFoundException: class path resource [org/springframework/boot/actuate/endpoint/AbstractEndpoint.class] cannot be opened because it does not exist
	at org.springframework.core.io.ClassPathResource.getInputStream(ClassPathResource.java:180) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.core.type.classreading.SimpleMetadataReader.<init>(SimpleMetadataReader.java:51) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.core.type.classreading.SimpleMetadataReaderFactory.getMetadataReader(SimpleMetadataReaderFactory.java:103) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory.createMetadataReader(ConcurrentReferenceCachingMetadataReaderFactory.java:88) ~[spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
	at org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory.getMetadataReader(ConcurrentReferenceCachingMetadataReaderFactory.java:75) ~[spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
	at org.springframework.core.type.classreading.SimpleMetadataReaderFactory.getMetadataReader(SimpleMetadataReaderFactory.java:81) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassParser.asSourceClass(ConfigurationClassParser.java:699) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassParser$SourceClass.getSuperClass(ConfigurationClassParser.java:869) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassParser.doProcessConfigurationClass(ConfigurationClassParser.java:326) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassParser.processConfigurationClass(ConfigurationClassParser.java:241) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassParser.processImports(ConfigurationClassParser.java:606) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
	... 21 common frames omitted
```

可以增加`java.lang.ArrayStoreException`的异常断点，然后可以用栈上找到具体的出错类。另外，据spring boot的javadoc，``@ImportAutoConfiguration``最好用在测试里。