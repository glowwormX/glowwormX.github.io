package com.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy {
//	interface Human {
//		void info();
//
//		void fly();
//	}

	// 被代理类，未实现接口
	static class SuperMan {
		public void info() {
			System.out.println("我是超人");
		}
		public void fly() {
			System.out.println("I can fly!");
		}
	}

	static class CGLibProxy implements MethodInterceptor {

		// CGLib需要代理的目标对象
		private Object targetObject;

		public Object bind(Object obj) {
			this.targetObject = obj;
			Enhancer enhancer = new Enhancer();
			// 设置父类--可以是类或者接口
			enhancer.setSuperclass(obj.getClass());
			enhancer.setCallback(this);
			Object proxyObj = enhancer.create();
			// 返回代理对象
			return proxyObj;
		}

		@Override
		public Object intercept(Object proxy, Method method, Object[] args,
								MethodProxy methodProxy) throws Throwable {
			Object obj = null;
			System.out.println("代理前操作。。。");

			// 执行目标目标对象方法
			obj = method.invoke(targetObject, args);

			System.out.println("代理后操作。。。");
			return obj;
		}
	}

	public static void main(String[] args) {
		SuperMan man = new SuperMan();//创建一个被代理类的对象

		// 添加如下代码，获取代理类源文件
		String path = CGLibProxy.class.getResource(".").getPath();
		System.out.println(path);
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, path);

		CGLibProxy cgLibProxy = new CGLibProxy();
		Object obj = cgLibProxy.bind(man);//返回一个代理类的对象
		System.out.println(obj.getClass());
		//class com.web.test.SuperMan$$EnhancerByCGLIB$$3be74240
		SuperMan hu = (SuperMan)obj;
		hu.info();//通过代理类的对象调用重写的抽象方法

		System.out.println();

		hu.fly();
	}
}