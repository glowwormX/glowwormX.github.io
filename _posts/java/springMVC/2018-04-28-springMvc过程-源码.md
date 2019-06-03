---
layout: post
title:  springMvc过程-源码
date:   2018-04-28 08:00:00 +0800
categories: spring
tag: springMvc
---

* content
{:toc}


## 过程：   

![](/styles/images/java/springMVC.png)   

第一步：发起请求到前端控制器(DispatcherServlet)         

第二步：前端控制器请求HandlerMapping查找 Handler   

         可以根据xml配置、注解进行查找   

第三步：处理器映射器HandlerMapping向前端控制器返回Handler   

第四步：前端控制器调用处理器适配器去执行Handler   

第五步：处理器适配器去执行Handler   

第六步：Handler执行完成给适配器返回ModelAndView   

第七步：处理器适配器向前端控制器返回ModelAndView   

         ModelAndView是springmvc框架的一个底层对象，包括 Model和view   

第八步：前端控制器请求视图解析器去进行视图解析   

         根据逻辑视图名解析成真正的视图(jsp)   

第九步：视图解析器向前端控制器返回View   

第十步：前端控制器进行视图渲染   

         视图渲染将模型数据(在ModelAndView对象中)填充到request域   

第十一步：前端控制器向用户响应结果   



## 组件：   

1、前端控制器DispatcherServlet（不需要程序员开发）   

作用接收请求，响应结果，相当于转发器，中央处理器。   

有了DispatcherServlet减少了其它组件之间的耦合度。   

    

2、处理器映射器HandlerMapping(不需要程序员开发)   

作用：根据请求的url查找Handler   

3、处理器适配器HandlerAdapter   

作用：按照特定规则（HandlerAdapter要求的规则）去执行Handler   


4、处理器Handler(需要程序员开发)   

注意：编写Handler时按照HandlerAdapter的要求去做，这样适配器才可以去正确执行Handler   

    

5、视图解析器View resolver(不需要程序员开发)   

作用：进行视图解析，根据逻辑视图名解析成真正的视图（view）   

    

6、视图View(需要程序员开发jsp)   

View是一个接口，实现类支持不同的View类型（jsp、freemarker、pdf...）   

## 推荐
[springmvc源码分析系列-请求处理流程](http://www.cnblogs.com/duanxiaojun/p/6591448.html)  
[SpringMVC工作原理解读--DispatcherServlet类源码解读（初级）](http://blog.csdn.net/wangyang1354/article/details/51999414)   
 
 
[springmvc源码分析----入门看springmvc的加载过程](https://www.cnblogs.com/duanxiaojun/p/6595419.html)   
[springmvc工作原理以及源码分析(基于spring3.1.0)](https://www.cnblogs.com/heavenyes/p/3905844.html)
