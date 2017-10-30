
## 说明

官方例子来源：

* https://spring.io/guides/gs/service-registration-and-discovery/
* https://spring.io/guides/gs/client-side-load-balancing/

官方的示例ribbon和eureka都是单独的。

在官方的例子上增加用户把服务注册到eureka，再通过ribbon做负载均衡调用。

## 启动说明

0. 启动eureka server：gs-service-registration-and-discovery/eureka-service

0. 启动gs-client-side-load-balancing/say-hello ，把 say-hello服务注册到eureka上

0. 启动gs-client-side-load-balancing/user，访问 http://localhost:8888/hi

   user通过eureka server获取到say-hello服务的地址列表，再通过ribbon做负载均衡来访问。
