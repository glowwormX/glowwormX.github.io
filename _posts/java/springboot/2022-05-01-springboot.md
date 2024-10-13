---
layout: post
title:  springboot
date:   2022-05-01 08:00:00 +0800
categories: springboot
tag: spring
---


* spring springboot区别
  springboot：快速开发spring应用的脚手架；   
  自动配置；   
  简化开发；   
  内置web服务器；   
  监控功能；   
  
* 自动装配流程原理：
  @SpringbootApplication   
  SpringbootApplication:@EnableAutoConfiguration、@ComponentScan   
  EnableAutoConfiguration: @Import  DeferredImportSelector   
  DeferredImportSelector:加载 META-INF/spring.factories   
  加载并通过ConditionOnXXX过滤所有AutoConfiguration   
  
* 为什么springBoot能直接java -jar 执行
   插件封装了一层

* springboot 内置tomcat启动原理   
  接口ServletWebserverFactory，核心方法getWebServer(ApplicationContext)      
  自动配置TomcatServletWebserverFactory，实现getWebServer时new Tomcat()并且start再await   
  集成springMVC DispatcherServlet
  
* 读取配置文件的原理
  基于监听器

* springboot监听与事件   
  [深入SpringBoot源码学习之——监听器与事件机制](https://github.com/coderbruis/JavaSourceCodeLearning/blob/master/note/SpringBoot/%E6%B7%B1%E5%85%A5SpringBoot%E6%BA%90%E7%A0%81%E5%AD%A6%E4%B9%A0%E4%B9%8B%E2%80%94%E2%80%94%E7%9B%91%E5%90%AC%E5%99%A8%E4%B8%8E%E4%BA%8B%E4%BB%B6%E6%9C%BA%E5%88%B6.md)
  
* springboot日志框架，默认   
  sl4j是桥接器，logback是默认实现    
  如果用了log4j，则log4j-api -> log4j-to-sl4j -> sl4j -> logback    
  如果要用则log4j2实现，有springboot-starter-log4j2，替换springboot-starter-longing   
