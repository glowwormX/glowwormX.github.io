---
layout: post
title:  spring-mybatis整合源码
date:   2020-04-23 08:00:00 +0800
categories: mybatis
tag:
- mybatis
---

* content
{:toc}


将MyBatis的事务交给Spring 来管理，还可以将SqlSession 等对象交给Spring管理，
并由SpringIoC容器将SqlSession 对象注入到其他Spring Bean中

## 1. SqlSessionFactoryBean

## 2. SpringManagedTransaction
```java
    private Connection connection; // 当前事务管理中维护的数据库连接对象
    private final DataSource dataSource; // 与当前数据库连接对象关联的数据派对象
    private boolean isConnectionTransactional ; // 标识该数据库连接对象是否由Spring 的事务管理器管理
    private boolean autoCommit ; // 事务是否自动提交

    @Override
    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
          openConnection();
        }
        return this.connection;
    }

    @Override
    private void openConnection() throws SQLException {
        //从Spring 事务管理器中获取数据库连接对象
        //首先尝试从事务上下文中获取数据库连接，如果获取成功则返回该连接，否则从数据源获取数据库连接并返回
        // 底层是通过基于TransactionSynchronizationManager.getResource()静态方法实现的，在
        // applicationContext.xml 中配置的事务管理器DataSourceTransactionManager 中，也是通
        // 过该静态方法获取事务对象，并完成开启/关闭事务功能的
        this.connection = DataSourceUtils.getConnection(this.dataSource);
        // 记录事务是否自动提交，当使用Spring 来管理事务时，并不会由SpringManagedTransaction 的
        // commit()和rollback()两个方法来管理事务
        this.autoCommit = this.connection.getAutoCommit();
        // 记录当前连接是否由Spring 事务管理器管理
        this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.connection, this.dataSource);
    }
```


## 3. SqlSessionTemplate
```java
public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,
      PersistenceExceptionTranslator exceptionTranslator) {

    notNull(sqlSessionFactory, "Property 'sqlSessionFactory' is required");
    notNull(executorType, "Property 'executorType' is required");

    this.sqlSessionFactory = sqlSessionFactory;
    this.executorType = executorType;
    this.exceptionTranslator = exceptionTranslator;
    //创建动态代理对象
    this.sqlSessionProxy = (SqlSession) newProxyInstance(
        SqlSessionFactory.class.getClassLoader(),
        new Class[] { SqlSession.class },
        new SqlSessionInterceptor());
  }

private class SqlSessionInterceptor implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //首先尝试从Spring事务管理器中获取SqlSession对象，如果获取成功则直接返回，
        //否则通过SqlSessionFactory 新建SqlSession 对象井将其交由Spring事务管理器管理后返回
        SqlSession sqlSession = SqlSessionUtils.getSqlSession(
          SqlSessionTemplate.this.sqlSessionFactory,
          SqlSessionTemplate.this.executorType,
          SqlSessionTemplate.this.exceptionTranslator);
        Object result = method.invoke(sqlSession, args);
        if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
          // force commit even on non-dirty sessions because some databases require
          // a commit/rollback before calling close()
          sqlSession.commit(true);
        }
        return result;
    }
  }

```

SqlSessionUtils.getSqlSession()方法：
```java
public static SqlSession getSqlSession(SqlSessionFactory sessionFactory, ExecutorType executorType, PersistenceExceptionTranslator exceptionTranslator) {
    //从Spring 事务管理器中获取SqlSessionHolder ，其中封装了SqlSession 对象
    SqlSessionHolder holder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
    //获取SqlSessionHolder 中封装的SqlSession 对象
    SqlSession session = sessionHolder(executorType, holder);
    if (session != null) {
      return session;
    }
    //获取SqlSessionHolder 中封装的SqlSession 对象
    session = sessionFactory.openSession(executorType);
    //将SqlSession 对象与Spring 事务管理器绑定
    registerSessionHolder(sessionFactory, executorType, exceptionTranslator, session);
    return session;
}
```
## 4. SqlSessionDaoSupport 
通过sqlSession 字段维护了一个SqlSessionTemplate，现在dao层就可以这么写，注入到springIoc容器中
但是每个类的要这么写很麻烦，MapperProxy也没用到，引出 MapperFactoryBean
```java
@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
    public User getUser (String userId) {
        return (User) getSqlSession().selectOne("com.xqw.mapper.UserMapper.getUser", userId) ;
    }
}
```

## 5. MapperFactoryBean
可以将Mapper 接口注入到Service 层的Bean中，如果
```java
public class MapperFactoryBean<T> extends SqlSessionDaoSupport implements FactoryBean<T> {
    @Override
    protected void checkDaoConfig() {
        Configuration configuration = getSqlSession().getConfiguration();
        if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
          try {
            configuration.addMapper(this.mapperInterface);
          } ...
        }
    }
    @Override
    public T getObject() throws Exception {
        return getSqlSession().getMapper(this.mapperInterface);
    }
}
```
有了MapperFactoryBean，可以生成代理类，但是还没注入到springIoc容器中，还是需要写xml配置
```
<！-配置id 为userMapper 的Bean ->
<bean id ＝ "userMapper" class ＝ "org.mybatis.spring.mapper.MapperFactoryBean">
<！ -配置Mapper 接口->
<property name = "mapperinterface" value = "com.xqw.mapper.UserMapper"/>
<！ -配置SqlSessionFactory ，用于创建底层的SqlSessionTemplate ->
<property name = "sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>
```

## 6. MapperScannerConfigurer
自动扫描包下的mapper
MapperScannerConfigurer 实现了BeanDefinitionRegistryPostProcessor 接口，该接口中的
postProcessBeanDefinitionRegistry()方法会在系统初始化的过程中被调用

最后会走ClassPathMapperScanner.processBeanDefinitions()方法，会对doScan()方法中扫描到的   
BeanDefinition集合进行修改，主要是将其中记录的接口类型改造为MapperFactoryBean 类型，   
井填充MapperFactoryBean所需的相关信息，这样，后续即可通过MapperFactoryBean完成相应功能了。