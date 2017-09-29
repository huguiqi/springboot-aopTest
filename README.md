# spring-boot-aop
spring boot + aop

# AOP原理详解

我们都知道，aop的原理就是动态代理，所以首先来介绍下java的动态代理

## 动态代理方式
主要有两种：
1. jdk自带动态代理
2. cglib

### JDK自带动态代理

>> JDK自带的动态代理方式必须要被代理对象实现某些接口；

JDK动态代理所用到的代理类在程序调用到代理类对象时才由JVM真正创建，JVM根据传进来的 业务实现类对象 以及 方法名 ，动态地创建了一个代理类的class文件并被字节码引擎执行，然后通过该代理类对象进行方法调用。我们需要做的，只需指定代理类的预处理、调用后操作即可。


#### 关键代码

1. 实现InvocationHandler接口
2. 调用java.lang.reflect.Proxy类的newProxyInstance或者getProxyClass方法

将被代理类传给即将生成的代理类
Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);

3. 重载invoke方法，将要处理的统一事件填入
    
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {.....}

#### 关键源码分析
    
    newProxyInstance ---> getProxyClass0 --->proxyClassCache.get(loader, interfaces)--->Object subKey = Objects.requireNonNull(subKeyFactory.apply(key, parameter));---->ProxyClassFactory.apply 

#### apply方法里关键代码：

//生成代理类class文件
    
    long num = nextUniqueNumber.getAndIncrement();     
    String proxyName = proxyPkg + proxyClassNamePrefix + num; 
    byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags);


### CGLIB

cglib是针对类来实现代理的，原理是对指定的业务类生成一个子类，并覆盖其中业务方法实现代理。因为采用的是继承，所以不能对final修饰的类进行代理。


demo中使用的是apache实现的cglib动态代理类：

github地址：

    https://github.com/cglib/cglib

在这个项目中，我用apache的cglib实现去模拟spring aop的工作原理

## 自定义类加载器

Javassist是一个编辑字节码的框架，可以让你很简单地操作字节码。它可以在运行期定义或修改Class。使用Javassist实现AOP的原理是在字节码加载前直接修改需要切入的方法。这比使用Cglib实现AOP更加高效，并且没太多限制

## 什么是spring的AOP

Aspect-Oriented Programming 
就是面向切面编程。

先引入官方文档的介绍：

我看的是4.3.12的文档，[地址在这里](https://docs.spring.io/spring/docs/4.3.12.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/#overview-aop-instrumentation)

>> AOP and Instrumentation
The spring-aop module provides an AOP Alliance-compliant aspect-oriented programming implementation allowing you to define, for example, method interceptors and pointcuts to cleanly decouple code that implements functionality that should be separated. Using source-level metadata functionality, you can also incorporate behavioral information into your code, in a manner similar to that of .NET attributes.
>>
>> The separate spring-aspects module provides integration with AspectJ.
>>
>> The spring-instrument module provides class instrumentation support and classloader implementations to be used in certain application servers. The spring-instrument-tomcat module contains Spring’s instrumentation agent for Tomcat.


也就是说，aop的核心是用AspectJ集成了spring-aspects，和spring-instrument工具模块组成。

那AspectJ是个什么玩意？

[看看它的官网](http://www.baeldung.com/aspectj)

看文档中介绍：
>> The separate spring-aspects module provides integration with AspectJ.

是不是aop的实现就是用的AspectJ的源码来实现的呢？
答案当然不是，而是用的AspectJ的织入风格。

看spring官网的说法：

>> Aspect: a modularization of a concern that cuts across multiple classes. Transaction management is a good example of a crosscutting concern in enterprise Java applications. In Spring AOP, aspects are implemented using regular classes (the schema-based approach) or regular classes annotated with the @Aspect annotation (the @AspectJ style).

看关键字，`the @AspectJ style`,用的是AspectJ的风格。

而他的实现是eclipse社区实现的：

>> @AspectJ refers to a style of declaring aspects as regular Java classes annotated with annotations. The @AspectJ style was introduced by the AspectJ project as part of the AspectJ 5 release. Spring interprets the same annotations as AspectJ 5, using a library supplied by AspectJ for pointcut parsing and matching. The AOP runtime is still pure Spring AOP though, and there is no dependency on the AspectJ compiler or weaver.
https://www.eclipse.org/aspectj/

用的是eclipse的aspectJ的5.0 Reliase版本。


[AspectJ的介绍](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-ataspectj)

[springAOP官网介绍](https://docs.spring.io/spring/docs/4.3.12.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/#aop-introduction)

[eclipse的AspectJ](https://www.eclipse.org/aspectj/)



## aspectJ什么风格？

aspectJ的官网上说：
>> AspectJ provides an implementation of AOP and has three core concepts:
>>   
>>   Join Point
>>   Pointcut
>>   Advice


也就是凡是包含这三种概念的，都是AspectJ的风格。

至于aspectJ的原理，其实也是基于JDK动态代理和cglib去实现的。








