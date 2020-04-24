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


mybatis-spring包主要作用：
1. 将SqlSession 等对象交给Spring管理，由SpringIoC容器将SqlSession 对象注入到其他Spring Bean中
2. 将自动扫描mapper注入到SpringIoC容器
3. 将MyBatis的事务交给Spring 来管理

## 如何管理SqlSession、mapper
在没有spring之前如何使用mybatis
```java
@Test
public void testSelectByExample() {
    InputStream in = DopAppRecordMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/DopAppRecordMapperTestConfiguration.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    DopAppRecordMapper mapper = sqlSessionFactory.getConfiguration().getMapper(DopAppRecordMapper.class, sqlSession);
    List<DopAppRecord> dopAppRecord = mapper.selectByExample(new DopAppRecordExample());
}
```
所以自然而然的spring要管理SqlSession，**spring就要解决初始化，才能管理SqlSessionFactory，才能管理sqlSession，进而才能管理mapper**

### 1. SqlSessionFactoryBean -- 管理SqlSessionFactory

**SqlSessionFactoryBean**这个类实现了三个接口：InitializingBean、FactoryBean、ApplicationListener   
**InitializingBean**接口：实现了这个接口，那么当bean初始化的时候，spring就会调用该接口的实现类的afterPropertiesSet方法，去实现当spring初始化该Bean 的时候所需要的逻辑。   
**FactoryBean**接口：实现了该接口的类，在调用getBean的时候会返回该工厂返回的实例对象，也就是再调一次getObject方法返回工厂的实例。   
**ApplicationListener**接口：实现了该接口，如果注册了该监听的话，那么就可以了监听到Spring的一些事件，然后做相应的处理   

在spring调用InitializingBean、FactoryBean接口的时候，SqlSessionFactoryBean最后都会执行一个私有方法：buildSqlSessionFactory()    
SqlSessionFactoryBean有下面这些字段，和Configuration都差不多，buildSqlSessionFactory()方法作用就是从原有的Configuration或者xml配置文件里构建Configuration，   
最后会走`new DefaultSqlSessionFactory(config)`来创建SqlSessionFactory
![](/styles/images/java/mybatis/SqlSessionFactoryBean.png)

### 2. SqlSessionTemplate -- 管理sqlSession
SqlSessionTemplate 是MyBatis-Spring 的核心，代替MyBatis 中的DefaultSqlSession 的功能，所以可以通过
SqlSessionTemplate 对象完成指定的数据库操作。SqlSessionTemplate 是**线程安全**(why?)的，可以在DAO(Data Access Object，数据访问对象)之间共享使用
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
        // 事务不由Spring 进行管理，sqlSession提交事务
        if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
          // force commit even on non-dirty sessions because some databases require
          // a commit/rollback before calling close()
          sqlSession.commit(true);
        }
        return result;
    }
  }

```

SqlSessionUtils.getSqlSession()方法： **sessionFactory.openSession() 在这里调用**
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

## 3. SqlSessionDaoSupport、MapperFactoryBean -- 管理mapper
通过sqlSession 字段维护了一个SqlSessionTemplate，现在dao层就可以下面这么写，注入到springIoc容器中
```java
@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
    public User getUser (String userId) {
        return (User) getSqlSession().selectOne("com.xqw.mapper.UserMapper.getUser", userId) ;
    }
}
```
但是每个类的要这么写很麻烦，要手动注入，MapperProxy也没用到，引出MapperFactoryBean

MapperFactoryBean实现FactoryBean，spring通过FactoryBean.getObject()可以将Mapper 接口注入到Service 层的Bean中
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

spring要调用FactoryBean.getObject()，就必须写xml配置才会调，才能注入到springIoc容器中：
```
<！-配置id 为userMapper 的Bean ->
<bean id ＝ "userMapper" class ＝ "org.mybatis.spring.mapper.MapperFactoryBean">
<！ -配置Mapper 接口->
<property name = "mapperinterface" value = "com.xqw.mapper.UserMapper"/>
<！ -配置SqlSessionFactory ，用于创建底层的SqlSessionTemplate ->
<property name = "sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>
```

## 4. MapperScannerConfigurer -- 自动扫描mapper
自动扫描包下的mapper
MapperScannerConfigurer 实现了BeanDefinitionRegistryPostProcessor 接口，该接口中的
postProcessBeanDefinitionRegistry()方法会在系统初始化的过程中被调用

最后会走ClassPathMapperScanner.processBeanDefinitions()方法，会对doScan()方法中扫描到的   
BeanDefinition集合进行修改，主要是将其中记录的接口类型改造为MapperFactoryBean 类型，   
井填充MapperFactoryBean所需的相关信息，这样，后续即可通过MapperFactoryBean完成相应功能了。


## 如何管理事务?

### SpringManagedTransaction
在buildSqlSessionFactory()中有以下代码，如果配置文件中没有指定transactionFactory，那么会用默认的SpringManagedTransactionFactory
```java
targetConfiguration.setEnvironment(new Environment(this.environment,
        this.transactionFactory == null ? new SpringManagedTransactionFactory() : this.transactionFactory,
        this.dataSource));

//SpringManagedTransactionFactory没啥特殊的，实现了org.apache.ibatis.transaction.TransactionFactory，工厂直接调用SpringManagedTransaction的构造方法
public class SpringManagedTransactionFactory implements TransactionFactory {
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new SpringManagedTransaction(dataSource);
    }
}
```

SpringManagedTransaction的实现：  
**SpringManagedTransaction的connection 字段维护的JDBC 连接来自Spring 事务管理器，当应用不再使用该连接时，会将其返还给Spring 事务管理器**
```java

public class SpringManagedTransaction implements Transaction {
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
}
```
