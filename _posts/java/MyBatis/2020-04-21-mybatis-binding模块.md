---
layout: post
title:  mybatis-binding模块
date:   2020-04-21 08:00:00 +0800
categories: mybatis
tag:
- mybatis
---

* content
{:toc}

## binding模块
### 1. MapperRegistry
![](/styles/images/java/mybatis/MapperRegistry.png)
 config：保存所有mybatis的配置信息，全局唯一   
 knownMapper：该集合的key是Mapper接口对应的Class对象， 
 value为MapperProxyFactory工厂对象，可以为Mapper接口创建代理对象   
 
 `void addMappers(String packageName)` 扫描packageName下所有mapper接口
 
 `getMapper(Class<T> type, SqlSession sqlSession)` 获取代理对象
 
  核心代码：`mapperProxyFactory.new/nstance(sqlSession)`

### 2. MapperProxyFactory
 
 ```java
public T new/nstance(SqlSession sqlSession) {
    final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapper/nterface, methodCache);
    return new/nstance(mapperProxy);
}
protected T new/nstance(MapperProxy<T> mapperProxy) {
    return (T) Proxy.newProxy/nstance(mapper/nterface.getClassLoader(), new Class[] { mapper/nterface }, mapperProxy);
}
```
最后走了java动态代理`Proxy.newProxy/nstance` ，在此之前需要初始化一个/nvocationHandler接口的实现类MapperProxy
[Java代理](/2019/08/04/Java代理/)

### 3. MapperProxy 
```java
@Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    try {
      if (Object.class.equals(method.getDeclaringClass())) { //如采目标方法继承自Object ，则直接调用目标方法
        return method.invoke(this, args);
      } else if (isDefaultMethod(method)) { //java7以上静态方法、default方法也直接调用
        return invokeDefaultMethod(proxy, method, args);
      }
    } catch (Throwable t) {
      throw ExceptionUtil.unwrapThrowable(t);
    }
    //从缓存中获取MapperMethod对象，如采缓存中没有，则创建新的MapperMethod对象并添加到缓存中
    final MapperMethod mapperMethod = cachedMapperMethod(method);
    return mapperMethod.execute(sqlSession, args);
  }

  private MapperMethod cachedMapperMethod(Method method) {
    return methodCache.compute/fAbsent(method, k -> new MapperMethod(mapper/nterface, method, sqlSession.getConfiguration()));
  }
```

### 4. MapperMethod
连接Mapper 接口以及映射配置文件中定义的SQL 语句的桥梁。
![](/styles/images/java/mybatis/MapperMethod.png)

#### SqlCommand 
记录sql信息，主要name type两个字段
name：xxxMapper.insert，
type：insert


#### MethodSignature

    boolean returnsMany; //返回值类型是否为Collection 类型或是数组类型
    boolean returnsMap ; //返回值类型是否为Map 类型
    boolean returnsVoid; //返回值类型是否为VO 工d
    boolean returnsCursor ; // 返回值是否为Cursor 类型
    Class<?> returnType ; // 返回值类型
    String mapKey; //如果返回值类型是Map ，则该字段记录了作为key的列名
    /nteger resultHandlerlndex;  //用来标记该方法参数列表中ResultHandler 类型参数的位置
    /nteger rowBoundsindex ;  //用来标记该方法参数列表中RowBounds 类型参数的位置
    ParamNameResolver：//参数解析   主要用到`Object getNamedParams(Object[] args)`方法 会返回单一对象或者name->Object 的map对象

```java
public Object execute(SqlSession sqlSession, Object[] args) {
    Object result;
    switch (command.getType()) {
      case /NSERT: {
    	Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.insert(command.getName(), param));
        break;
      }
      case UPDATE: 
      case DELETE: 
      //UPDATE、DELETE省略，和/NSERT差不多
      case SELECT: 
        //处理返回值为void 且ResultSet 通过ResultHandler 处理的方法
        if (method.returnsVoid() && method.hasResultHandler()) {
          executeWithResultHandler(sqlSession, args);
          result = null;
        } else if (method.returnsMany()) {
          result = executeForMany(sqlSession, args);
        } else if (method.returnsMap()) {
          result = executeForMap(sqlSession, args);
        } else if (method.returnsCursor()) {
          result = executeForCursor(sqlSession, args);
        } else { //处理返回值为单一对象的方法
          Object param = method.convertArgsToSqlCommandParam(args);
          result = sqlSession.selectOne(command.getName(), param);
          if (method.returnsOptional() &&
              (result == null || !method.getReturnType().equals(result.getClass()))) {
            result = Optional.ofNullable(result);
          }
        }
        break;
      case FLUSH:
        result = sqlSession.flushStatements();
        break;
      default:
        throw new BindingException("Unknown execution method for: " + command.getName());
    }
    if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
      throw new BindingException("Mapper method '" + command.getName()
          + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
    }
    return result;
  }
```
execute方法最后都会调用调用SqlSession 对应的方法完成数据库操作
最后用一些rowCountResult、executeForMany方法处理返回值，转换成代理对象对应的返回值

## Cache
```java
public interface Cache {
    String getId () ; // 该缓存对象的id
    void putObject(Object key, Object value ); // 向缓存中添加数据，一般情况下， key 是CacheKey , value是查询结果
    Object getObject(Object key); // 根据指定的key ，在缓存中查找对应的结果对象
    Object removeObject(Object key); // 删除key 对应的缓存项
    void clear(); // 清空缓存
    int getSize();// 缓存项的个数，该方法不会被MyBatis 核心代码使用，所以可提供空实现
    ReadWriteLock getReadWriteLock();//获取读写锁，该方法不会被MyBatis 核心代码使用，所以可提供空实现
}
```
PerpetualCache：HashMap简单实现

装饰类，都有`final Cache delegate;`字段，构造器都要传入被装饰类Cache

具体装饰类：   
BlockingCache：   
FifoCache   
LoggingCache   
LruCache   
ScheduledCache   
SerializedCache   
SoftCache   
SynchronizedCache   
TransactionalCache   
WeakCache   