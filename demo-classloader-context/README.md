

## 目的

本工程有两个目的：

* 展示spring boot应用的ClassLoader继承关系，还有ClassLoader的urls
* 展示spring boot应用的spring context的继承关系

## 三种运行方式

* 导入IDE，run main 函数
* 以fat jar执行

    ```
    mvn clean package
    java -jar target/demo-classloader-context-0.0.1-SNAPSHOT.jar
    ```

* 以解压目录执行

    ```
    mvn clean package
    cd target
    unzip demo-classloader-context-0.0.1-SNAPSHOT.jar -d demo
    cd demo
    java org.springframework.boot.loader.PropertiesLauncher
    ```

## 查看ClassLoader的继承关系和urls

应用启动之后，在stdout会打印出ClassLoader的继承树，还有urls。

## 查看spring context的继承关系

应用启动之后，访问

* http://localhost:8080/
* http://localhost:8081/contexttree

> 如果想查看不启动spring cloud的情况，可以加上 `-Dspring.cloud.bootstrap.enabled=false` 参数。