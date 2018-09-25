利用Arthas排查Spring Boot应用NoSuchMethodError问题
====


## 前言

有时spring boot应用会遇到`java.lang.NoSuchMethodError`的问题，下面以具体的demo来说明怎样利用[arthas](https://github.com/alibaba/arthas)来排查。

## 在应用的main函数里catch住异常，保证进程不退出

很多时候当应用抛出异常后，进程退出了，就比较难排查问题。可以先改下main函数，把异常catch住：

```java
	public static void main(String[] args) throws IOException {
		try {
			SpringApplication.run(DemoNoSuchMethodErrorApplication.class, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// block
		System.in.read();
	}
```

Demo启动之后，抛出的异常是：

```java
java.lang.NoSuchMethodError: org.springframework.core.annotation.AnnotationAwareOrderComparator.sort(Ljava/util/List;)V
	at org.springframework.boot.SpringApplication.getSpringFactoriesInstances(SpringApplication.java:394)
	at org.springframework.boot.SpringApplication.getSpringFactoriesInstances(SpringApplication.java:383)
	at org.springframework.boot.SpringApplication.initialize(SpringApplication.java:249)
	at org.springframework.boot.SpringApplication.<init>(SpringApplication.java:225)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1118)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1107)
	at com.example.demoNoSuchMethodError.DemoNoSuchMethodErrorApplication.main(DemoNoSuchMethodErrorApplication.java:13)
```

显然，异常的意思是`AnnotationAwareOrderComparator`缺少`sort(Ljava/util/List;)V`这个函数。

## 安装arthas

参考：https://alibaba.github.io/arthas/install-detail.html

## 使用sc命令查找类所在的jar包

应用需要抛出了异常，但是进程还没有退出，我们用arthas来attach上去。比如在mac下面：

```bash
./as.sh
```

然后选择`com.example.demoNoSuchMethodError.DemoNoSuchMethodErrorApplication`进程。

再执行[sc](https://alibaba.github.io/arthas/sc.html)命令来查找类：

```java
$ sc -d org.springframework.core.annotation.AnnotationAwareOrderComparator
 class-info        org.springframework.core.annotation.AnnotationAwareOrderComparator
 code-source       /Users/hengyunabc/.m2/repository/org/springframework/spring/2.5.6.SEC03/spring-2.5.6.SEC03.jar
 name              org.springframework.core.annotation.AnnotationAwareOrderComparator
 isInterface       false
 isAnnotation      false
 isEnum            false
 isAnonymousClass  false
 isArray           false
 isLocalClass      false
 isMemberClass     false
 isPrimitive       false
 isSynthetic       false
 simple-name       AnnotationAwareOrderComparator
 modifier          public
 annotation
 interfaces
 super-class       +-org.springframework.core.OrderComparator
                     +-java.lang.Object
 class-loader      +-sun.misc.Launcher$AppClassLoader@5c647e05
                     +-sun.misc.Launcher$ExtClassLoader@689e3d07
 classLoaderHash   5c647e05

Affect(row-cnt:1) cost in 41 ms.
```

可以看到`AnnotationAwareOrderComparator`是从`spring-2.5.6.SEC03.jar`里加载的。

## 使用jad查看反编绎的源代码

下面使用[jad](https://alibaba.github.io/arthas/jad.html)命令来查看`AnnotationAwareOrderComparator`的源代码

```
$ jad org.springframework.core.annotation.AnnotationAwareOrderComparator

ClassLoader:
+-sun.misc.Launcher$AppClassLoader@5c647e05
  +-sun.misc.Launcher$ExtClassLoader@689e3d07

Location:
/Users/hengyunabc/.m2/repository/org/springframework/spring/2.5.6.SEC03/spring-2.5.6.SEC03.jar

/*
 * Decompiled with CFR 0_132.
 */
package org.springframework.core.annotation;

import java.lang.annotation.Annotation;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

public class AnnotationAwareOrderComparator
extends OrderComparator {
    protected int getOrder(Object obj) {
        Order order;
        if (obj instanceof Ordered) {
            return ((Ordered)obj).getOrder();
        }
        if (obj != null && (order = obj.getClass().getAnnotation(Order.class)) != null) {
            return order.value();
        }
        return Integer.MAX_VALUE;
    }
}

Affect(row-cnt:1) cost in 286 ms.
```

可见，`AnnotationAwareOrderComparator`的确没有`sort(Ljava/util/List;)V`函数。

## 排掉依赖，解决问题

从上面的排查里，可以确定

* `AnnotationAwareOrderComparator`来自`spring-2.5.6.SEC03.jar`，的确没有`sort(Ljava/util/List;)V`函数。

所以，可以检查maven依赖，把spring 2的jar包排掉，这样子就可以解决问题了。

## 总结

* 仔细看`NoSuchMethodError`的异常信息，了解是什么类缺少了什么函数
* 利用arthas来查找类，反编绎源码，确认问题

## 链接

* [Arthas](https://alibaba.github.io/arthas)

