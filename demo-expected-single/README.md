

## 写在前面

这个demo来说明怎么排查一个常见的spring expected single matching bean but found 2的异常。

## 调试排查 expected single matching bean but found 2 的错误

把工程导入IDE里，直接启动应用，抛出来的异常信息是：

```
Caused by: org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'javax.sql.DataSource' available: expected single matching bean but found 2: h2DataSource1,h2DataSource2
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveNamedBean(DefaultListableBeanFactory.java:1041) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:345) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:340) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1090) ~[spring-context-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.boot.autoconfigure.jdbc.DataSourceInitializer.init(DataSourceInitializer.java:71) ~[spring-boot-autoconfigure-1.4.7.RELEASE.jar:1.4.7.RELEASE]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_112]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_112]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_112]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_112]
	at org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor$LifecycleElement.invoke(InitDestroyAnnotationBeanPostProcessor.java:366) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor$LifecycleMetadata.invokeInitMethods(InitDestroyAnnotationBeanPostProcessor.java:311) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	at org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor.postProcessBeforeInitialization(InitDestroyAnnotationBeanPostProcessor.java:134) ~[spring-beans-4.3.9.RELEASE.jar:4.3.9.RELEASE]
	... 30 common frames omitted
```


很多人碰到这种错误时，就乱配置一通，找不到下手的办法。其实耐心排查下，是很简单的。

## 抛出异常的原因

异常信息写得很清楚了，在spring context里需要注入/获取到一个`DataSource`  bean，但是现在spring context里出现了两个，它们的名字是：h2DataSource1,h2DataSource2

那么有两个问题：

1. 应用是在哪里要注入/获取到一个`DataSource`  bean？
1. h2DataSource1,h2DataSource2 是在哪里定义的？

## 使用 Java Exception Breakpoint

在IDE里，新建一个断点，类型是`Java Exception Breakpoint`（如果不清楚怎么添加，可以搜索对应IDE的使用文档），异常类是上面抛出来的`NoUniqueBeanDefinitionException`。

当断点停住时，查看栈，可以很清楚地找到是在`DataSourceInitializer.init() line: 71	`这里要获取`DataSource`：


```
Thread [main] (Suspended (exception NoUniqueBeanDefinitionException))
	owns: ConcurrentHashMap<K,V>  (id=49)
	owns: Object  (id=50)
	DefaultListableBeanFactory.resolveNamedBean(Class<T>, Object...) line: 1041
	DefaultListableBeanFactory.getBean(Class<T>, Object...) line: 345
	DefaultListableBeanFactory.getBean(Class<T>) line: 340
	AnnotationConfigEmbeddedWebApplicationContext(AbstractApplicationContext).getBean(Class<T>) line: 1090
	DataSourceInitializer.init() line: 71
	NativeMethodAccessorImpl.invoke0(Method, Object, Object[]) line: not available [native method]
	NativeMethodAccessorImpl.invoke(Object, Object[]) line: 62
	DelegatingMethodAccessorImpl.invoke(Object, Object[]) line: 43
	Method.invoke(Object, Object...) line: 498
	InitDestroyAnnotationBeanPostProcessor$LifecycleElement.invoke(Object) line: 366
	InitDestroyAnnotationBeanPostProcessor$LifecycleMetadata.invokeInitMethods(Object, String) line: 311
	CommonAnnotationBeanPostProcessor(InitDestroyAnnotationBeanPostProcessor).postProcessBeforeInitialization(Object, String) line: 134
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).applyBeanPostProcessorsBeforeInitialization(Object, String) line: 409
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).initializeBean(String, Object, RootBeanDefinition) line: 1620
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).doCreateBean(String, RootBeanDefinition, Object[]) line: 555
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).createBean(String, RootBeanDefinition, Object[]) line: 483
	AbstractBeanFactory$1.getObject() line: 306
	DefaultListableBeanFactory(DefaultSingletonBeanRegistry).getSingleton(String, ObjectFactory<?>) line: 230
	DefaultListableBeanFactory(AbstractBeanFactory).doGetBean(String, Class<T>, Object[], boolean) line: 302
	DefaultListableBeanFactory(AbstractBeanFactory).getBean(String, Class<T>, Object...) line: 220
	DefaultListableBeanFactory.resolveNamedBean(Class<T>, Object...) line: 1018
	DefaultListableBeanFactory.getBean(Class<T>, Object...) line: 345
	DefaultListableBeanFactory.getBean(Class<T>) line: 340
	DataSourceInitializerPostProcessor.postProcessAfterInitialization(Object, String) line: 62
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).applyBeanPostProcessorsAfterInitialization(Object, String) line: 423
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).initializeBean(String, Object, RootBeanDefinition) line: 1633
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).doCreateBean(String, RootBeanDefinition, Object[]) line: 555
	DefaultListableBeanFactory(AbstractAutowireCapableBeanFactory).createBean(String, RootBeanDefinition, Object[]) line: 483
	AbstractBeanFactory$1.getObject() line: 306
	DefaultListableBeanFactory(DefaultSingletonBeanRegistry).getSingleton(String, ObjectFactory<?>) line: 230
	DefaultListableBeanFactory(AbstractBeanFactory).doGetBean(String, Class<T>, Object[], boolean) line: 302
	DefaultListableBeanFactory(AbstractBeanFactory).getBean(String) line: 197
	DefaultListableBeanFactory.preInstantiateSingletons() line: 761
	AnnotationConfigEmbeddedWebApplicationContext(AbstractApplicationContext).finishBeanFactoryInitialization(ConfigurableListableBeanFactory) line: 867
	AnnotationConfigEmbeddedWebApplicationContext(AbstractApplicationContext).refresh() line: 543
	AnnotationConfigEmbeddedWebApplicationContext(EmbeddedWebApplicationContext).refresh() line: 122
	SpringApplication.refresh(ApplicationContext) line: 762
	SpringApplication.refreshContext(ConfigurableApplicationContext) line: 372
	SpringApplication.run(String...) line: 316
	SpringApplication.run(Object[], String[]) line: 1187
	SpringApplication.run(Object, String...) line: 1176
	DemoExpectedSingleApplication.main(String[]) line: 17
```

### 定位哪里要注入/使用`DataSource`

要获取`DataSource`具体的代码是：

```java
//org.springframework.boot.autoconfigure.jdbc.DataSourceInitializer.init()
	@PostConstruct
	public void init() {
		if (!this.properties.isInitialize()) {
			logger.debug("Initialization disabled (not running DDL scripts)");
			return;
		}
		if (this.applicationContext.getBeanNamesForType(DataSource.class, false,
				false).length > 0) {
			this.dataSource = this.applicationContext.getBean(DataSource.class);
		}
		if (this.dataSource == null) {
			logger.debug("No DataSource found so not initializing");
			return;
		}
		runSchemaScripts();
	}
```

`this.applicationContext.getBean(DataSource.class);` 要求spring context里只有一个`DataSource`的bean，但是应用里有两个，所以抛出了`NoUniqueBeanDefinitionException`。


### 从`BeanDefinition`获取bean具体定义的代码

我们再来看 h2DataSource1,h2DataSource2 是在哪里定义的？

上面进程断在了`DefaultListableBeanFactory.resolveNamedBean(Class<T>, Object...)` 函数里的 `throw new NoUniqueBeanDefinitionException(requiredType, candidates.keySet());` 这一行。

那么我们在这里执行一下（如果不清楚，先搜索下IDE怎么在断点情况下执行代码）：

```java
this.getBeanDefinition("h2DataSource1")
```

返回的信息是：

```
Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=demoExpectedSingleApplication; factoryMethodName=h2DataSource1; initMethodName=null; destroyMethodName=(inferred);
defined in com.example.demo.expected.single.DemoExpectedSingleApplication
```

可以很清楚地定位到`h2DataSource1`这个bean是在 `com.example.demo.expected.single.DemoExpectedSingleApplication`里定义的。

所以上面两个问题的答案是：

1. 是spring boot代码里的`DataSourceInitializer.init() line: 71	`这里要获取`DataSource`，并且只允许有一个`DataSource`实例
2. h2DataSource1,h2DataSource2 是在`com.example.demo.expected.single.DemoExpectedSingleApplication`里定义的

## 解决问题

上面排查到的原因是：应用定义了两个`DataSource`实例，但是spring boot却要求只有一个。那么有两种办法来解决：

1. 使用`@Primary`来指定一个优先使用的`DataSource`，这样子spring boot里自动初始的代码会获取到`@Primary`的bean
2. 把spring boot自动初始化`DataSource`相关的代码禁止掉，应用自己来控制所有的`DataSource`相关的bean


禁止的办法有两种：

1. 在main函数上配置exclude

      ```
          @SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
      ```
2. 在application.properties里配置：

      ```
          spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
      ```

## 总结

* 排查spring初始化问题时，灵活使用Java Exception Breakpoint
* 从异常栈上，可以很容易找到哪里要注入/使用bean
* 从`BeanDefinition`可以找到bean是在哪里定义的（哪个Configuration类/xml）

