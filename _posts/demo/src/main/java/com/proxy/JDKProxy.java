package com.proxy;

import net.sf.cglib.core.DebuggingClassWriter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy {
	interface Human {
		void info();

		void fly();
	}

	// 被代理类
	static class SuperMan implements Human {
		public void info() {
			System.out.println("我是超人");
		}
		public void fly() {
			System.out.println("I can fly!");
		}
	}

	static class MyInvocationHandler implements InvocationHandler {

		// 被代理类对象的声明
		Object obj;

		// 动态的创建一个代理类的对象
		public Object bind(Object obj){
			this.obj = obj;
			//生成一个动态类
			return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj
					.getClass().getInterfaces(), this);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			System.out.println("代理前操作。。。");
			Object returnVal = method.invoke(obj, args);
			System.out.println("代理后操作。。。");

			return returnVal;
		}
	}

	public static void main(String[] args) {
        // 添加如下代码，获取代理类源文件
        String path = Human.class.getResource(".").getPath();
        System.out.println(path);
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, path);

		//创建一个被代理类的对象
		SuperMan man = new SuperMan();
		MyInvocationHandler handler = new MyInvocationHandler();
		//返回一个代理类的对象
		Object obj = handler.bind(man);
		System.out.println(obj.getClass());
		//代理实例 强转
		Human hu = (Human)obj;
		//通过代理类的对象调用重写的抽象方法
		hu.info();

		System.out.println();

		hu.fly();
	}
}