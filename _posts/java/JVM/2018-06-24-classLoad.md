---
layout: post
title:  类加载机制
date:   2018-06-24 08:00:00 +0800
categories: jvm
tag:
- JVM
---

* content
{:toc}

过程：
[](/styles/images/java/classLoad.jpg)

# 初始化
虚拟机规范有且只有5中情况进行初始化
1. new、getstatic、pubstatic、invokestatic 四个字节码指令会触发初始化   
调用静态方法字段会触发getstatic、pubstatic、invokestatic（final修饰的字段预编译是会放入常量池）
1. java.lang.reflect 放射调用时进行初始化
1. 初始化某个类时，其父类还未初始化则先初始化父类
1. 虚拟机启动时用户执行的主类（main方法类）
1. jdk1.7 动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getStatic、REF_putStatic、REFinvokeStatic 的句柄，且这个句柄所对应的类没有进行过初始化（？）

以上5种场景中的行为称为对一个类进行主动引用。除此之外，所有引用类的方式都不会触发初始化，称为被动引用。被动引用的常见例子包括：

1. 通过子类引用父类的静态字段，不会导致子类初始化。
1. 通过数组定义来引用类，不会触发此类的初始化，如SuperClass[] sca = new SuperClass[10]
1. 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。
接口的加载过程和类加载过程略有不同，它们真正的区别在于在前文提到的5种需要开始初始化场景中的第3种：当一个类在初始化时，要求其父类全部都已经初始化过了，但是一个接口在初始化时，并不要求其父接口全部都完成了初始化，只有在真正使用到父接口的时候（如引用接口中定义的常量）才会初始化。