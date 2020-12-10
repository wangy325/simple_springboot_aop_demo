
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/maven-plugin/)

---

# 基于Spring Boot的Spring AOP demo

有一个cd接口，其实体类用于播放歌曲，同时我们想在播放歌曲的时候记录每个曲目的播放次数。看起来，记录次数这个事和播放曲目是不相干的事情，当然，我们可以在每首歌曲播放完成之后记录，但是
更好的办法是使用一个切面，切入到播放方法中，来完成这件事，这样可以减少无关逻辑对代码的侵入。

此程序分别使用了基于@Aspect注解和基于XML配置文件2种方式进行了切面注入，2种方式效果是等同的。

此程序使用的是Spring AOP，并没有使用功能更加丰富的AspectJ，Spring AOP很大部分借鉴了AspectJ，如果只是简单的方法层面的织入，那么Spring AOP就能够满足需求。如果需要构造器或者属性拦截，或者需要
为spring bean引入新方法，那么就需要使用AspectJ了

# 开始

从[start.spring.io](https://start.spring.io)下载空项目，引入Spring AOP依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

# 配置

## 基于Java的配置

1. 使用[DiskConfig](src/main/java/com/wangy/aop/DiskConfig.java)配置bean注入
2. 使用`@Aspect`注解将`TrackCounter` bean声明为一个切面，同时使用`@Pointcut`注解声明切点，再使用对应的通知注解声明通知，
配置参考[TrackCounter](src/main/java/com/wangy/aop/TrackCounter.java)
 
   - @Before
   - @After
   - @AfterReturning
   - @AfterThrowing
   - @Around

切入点表达式

    execution( * com.wangy.aop.disk.BlankDisk.playTrack(int)) && args(trackNumber)

前半部分是常见的切面表达式，用于指定切入点；

- 第一个 `*` 指示任意返回类型
- 使用全限定名指定类和方法名，括号内的`int`指定参数列表，可以使用`(..)`来匹配任意参数

更多关于切入点表达式的内容：

- https://www.cnblogs.com/liaojie970/p/7883687.html
- https://howtodoinjava.com/spring-aop/aspectj-pointcut-expressions/
- https://www.baeldung.com/spring-aop-pointcut-tutorial

`&&`连接符后面的内容是什么意思？

这里需要提及的是， Spring AOP支持AspectJ切点指示器的子集，除了**最常用**的`execution()`指示器之外，还有其他的指示器：

|AspectJ指示器|描述|
|:-----:|:---:|
|args()|限制连接点匹配参数为指定类型的执行方法|
|@args()|限制连接点匹配参数由指定注解标注的执行方法|
|execution()|用于匹配是连接点的执行方法|
|this()|限制连接点匹配AOP代理的bean引用为指定类型的类|
|target|限制连接点匹配目标对象为指定类型的类|
|@target()|限制连接点匹配特定的执行对象，这些对象对应的类需要有指定类型的注解|
|within()|限制连接点匹配指定的类型|
|@within()|限制连接点匹配指定注解所标注的类型（当使用Spring AOP时，方法定义在由指定的注解所标注的类里）|
|@annotation|限制匹配带有指定注解的连接点|

所以这里的`args(trackNumber)`限定符，表明传递给连接点（切入点）`playTrack(int)`的int类型参数也会传递到通知中去。

> 关于`args()`条件的作用，sping官方文档有说明：
>https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj-advice-params

这是基于Java的切面配置，将之前的切面装配到Spring容器中，同时初始化了一个cd bean到容器中。

需要注意到[启动类](src/main/java/com/wangy/aop/AopApplication.java)中使用了`@EnableAspectJAutoProxy`注解，
这意味着开启AspectJ自动代理，使得Spring框架拥有AOP能力。

## 使用xml配置

xml等效配置在`test`包中：[基于xml的等效配置](src/test/resources/spring-aop.xml)

# 测试

测试包中提供了2个测试类，分别用于测试基于`javaBean`+注解、基于xml文件的aop配置；

- [TrackCounterTest](/src/test/java/com/wangy/aop/TrackCountTest.java)用于测试基于javaBean和注解实现的aop，这是推荐的方式
- [TrackCountTestWithXml](/src/test/java/com/wangy/aop/TrackCountTestWithXml.java)用于测试基于xml配置的aop，在运行此测试时，
需要注释掉[TrackCount](src/main/java/com/wangy/aop/TrackCounter.java)类上的`@Aspect`注解，以免Application Context注入2个切面

# 参考

spring in action (4th edition)
