## Mongo

### 服务器操作
``` 
      ./mongod <-port 27017> 服务启动
      备份
      mongodump -h IP --port 端口 -u 用户名 -p 密码 -d 数据库 -o 文件存在路径 
      （./mongodump -d mbxt）
      （./mongodump -d mbxt -o /home/hlkj/Documents/mongobak/shellbak/test）
      还原
        整库
        mongorestore -h IP --port 端口 -u 用户名 -p 密码 -d 数据库 (--drop) 文件夹路径
        单表
        mongorestore -h IP --port 端口 -u 用户名 -p 密码 -d 数据库 -c 表名 文件路径
      （./mongorestore -d mbxt dump/mbxt/）
      （./mongorestore -d mbxt /home/hlkj/Documents/mongobak/shellbak/test）
      导出表，或者表中部分字段
      mongoexport -h IP --port 端口 -u 用户名 -p 密码 -d 数据库 -c 表名 -f 字段
```

#### 

### 副本集搭建
``` 
   启动三台 replSet设置同一个名字
    ./mongod --port 2001 --bind_ip 0.0.0.0 --dbpath /data/db/ --replSet rs0
    ./mongod --port 2002 --bind_ip 0.0.0.0 --dbpath /data/db/ --replSet rs0
    ./mongod --port 2003 --bind_ip 0.0.0.0 --dbpath /data/db/ --replSet rs0

    主服务连接进去设置：
    mongo --port 2001
    rs.initiate()
    rs.add("<hostname>:2002")
    rs.add("<hostname>:2003")
    rs.conf()
    
    rs.initiate({"_id":"rsMbxt","members":[
        {"_id":1,
        "host":"ip:2001",
        "priority":1
        },
        {"_id":2,
        "host":"ip:2002",
        "priority":1
        },
        {"_id":3,
        "host":"ip:2003",
        "priority":1
        }
        ]})
    
    副本集加权限
    先无权限中配置副本集、创建账号
    use admin
    db.createUser({user:"root",pwd:"mbxt123456",roles:["userAdminAnyDatabase"]})
    use mbxt
    db.createUser({user: "root",pwd: "mbxt123456",roles: [ { role: "readWrite", db: "mbxt" } ]})
    关闭后添加mongo启动配置文件中添加auth=true 和 keyFile=<path>(创建ssl文件供副本集之间认证)
    再次启动所有服务，登录后使用admin数据库为账号添加修改副本集的权限
    use admin
    db.auth('<user>','<psw>')
    db.grantRolesToUser( "root" , [ { role: "dbOwner", db: "admin" },{ "role": "clusterAdmin", "db": "admin" },
    { "role": "userAdminAnyDatabase", "db": "admin" },
    { "role": "dbAdminAnyDatabase", "db": "admin" },
    { role: "root", db: "admin" } ])
    参考：
    https://www.cnblogs.com/Joans/p/7724144.html

    修改配置
    var config = rs.config()
    config.members[0].host = 'ip:2001'
    config.members[1].host = 'ip:2002'
    config.members[2].host = 'ip:2003'
    rs.reconfig(config)

    
    2.1.0 spring-mongo-data 注入

    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Transactional
    public void do(){}
``` 
### 分片
```
机器 10.20.11.225 :1个mongos路由进程，3个配置服务器进行
机器 10.20.20.239 :第一个分片
机器 10.20.21.27 :第二个分片
机器 10.20.23.50 :第三个分片
1、配置服务器(mongod configsvr = true)
2、路由服务器(mongos 不需要dbpath configdb=10.20.11.225:20000,10.20.11.225:20001,10.20.11.225:20002)
3、分片服务器(mongod)
4、登录mongos，添加配置分片服务器
https://www.jianshu.com/p/6648efd24f25
```

### update更新

1. 普通批量更新
    
        db.getCollection('device').updateMany({"_id" : "5cce2db3d8c51e3f70f707f4"},{$set:{"name" : "液位监控"}})
1. 复制一个字段 取不到字段值只能遍历一遍

        db.getCollection('device').find().forEach(
          function (e) {
            e.name1 = e.name;//将name复制成name1
            db.events.save(e);
          }
        )
        update $rename命令

1. 更新List中符合条件的内容 

        //以下代码为更新人员id为staffId, 图片list(imgs)中type为'type'的人员图片信息
        Update update = new Update();
        update.set("imgs.$.photo", img.getPhoto());
        update.set("imgs.$.type", img.getType());
        
        Query query = Query.query(new Criteria().andOperator(Criteria.where("id").is(staffId), Criteria.where("imgs").elemMatch(Criteria.where("type").is(type))));
        
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Staff.class);
1. 向List中新增数据
       
        //$push向数组末尾添加元素
        db.post.update({"id":1},{$push:{"comments": "test3"}})   
        //使用$each一次性添加多个值：
        db.post.update({"id":1},{$push:{"comments":{$each:["test4","test5","test6"]}}})   
        //从数组末尾删除一个值：
        db.post.update({"id":1},{$pop:{"comments":1}})   
        //从数组开头删除一个值：
        db.post.update({"id":1},{$pop:{"comments":-1}})    
        //删除数组中一个指定的值：
        db.post.update({"id":1},{$pull:{"comments":"test3"}})   
        //基于数组下标位置修改：
        db.post.update({"id":1},{$set:{"comments.1":"test9"}})   
1. more


### find查询
1. 条件为or

        Query query = new Query();
        query.addCriteria(new Criteria()
                          .orOperator(
                                  Criteria.where("name").regex(pattern),
                                  Criteria.where("mobilePhone").regex(pattern),
                                  Criteria.where("policeNumber").regex(pattern)

1. spring-mongodb-data  返回指定字段

        DBObject dbObject = new BasicDBObject();
        //dbObject.put("name", "zhangsan");  
        //查询条件 BasicDBObject fieldsObject=new BasicDBObject();
        //指定返回的字段fieldsObject.put("name", true); 
        fieldsObject.put("age", true);  
        fieldsObject.put("sex", true);   
        query = new BasicQuery(dbObject,fieldsObject);
        List<Person> user = mongoTemplate.find(query, Person.class);
        
        第二种方式
        
        Query query = Query.query(Criteria.where("staffId").is(staffId).and("delete").is(MbxtConstant.IS_NO_DELETE));
        //elemMatch 只能返回一个
        query.fields().include("staffId").elemMatch("signDetails",Criteria.where("delete").is(MbxtConstant.IS_NO_DELETE));
        return mongoTemplate.findOne(query, ExpressSigned.class);

 ## 聚合查询
 ### 关联查询 问题：_id为ObjectId类型 projectId为String 不能装换
```
  db.projectStorage.aggregate([
{ "$addFields": { "projectId": { "$toObjectId": "$projectId" }}},
{
  $lookup:
    {
      from: "project",
      localField: "projectId",
      foreignField: "_id",
      as: "project"
    }
  },
{ "$match" : { "project.name" : { "$regex" : "^.*项目.*$", "$options" : "i" } } }
]).pretty()
```
```
update:2019/04/24 
 //将关联后的 project和父级字段合并
 {$replaceRoot: { newRoot: { $mergeObjects: [ { $arrayElemAt: [ "$project", 0 ] }, "$$ROOT" ] } }},
 //再删除project
 { $project: { project: 0 } }
```
spring-data-mongo：addFields springdata暂不支持
1. 使用$project替代，缺点除了projectId外都不显示，需要一个一个project("field"...)显示
 project().and(ConvertOperators.valueOf("projectId").convertToObjectId()).as("projectId")
1. 新建一个类对原生命令行解析

```java
public class JsonAggOperation implements AggregationOperation {
    private String jsonOperation;

    public JsonAggOperation(String jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    @Override
    public Document toDocument(AggregationOperationContext aggregationOperationContext) {
        return aggregationOperationContext.getMappedObject(Document.parse(jsonOperation));
    }
}
```
```java
        Aggregation agg = Aggregation.newAggregation(
                new JsonAggOperation("{ '$addFields': { 'projectId': { '$toObjectId': '$projectId' }}}"),
//                使用$project
//                project("blastCompanyId", "projectId")
//                        .and(ConvertOperators.valueOf("projectId").convertToObjectId()).as("projectId"),
                lookup("project", "projectId", "_id" , "project"),
                match(Criteria.where("project.name").regex("^.*项目.*$")),
                unwind("project")
        );
        List<ProjectStorage.ProjectStorageProject> projectStorage = mongoTemplate.aggregate(agg, "projectStorage", ProjectStorage.ProjectStorageProject.class).getMappedResults();


```

       
 ### 对查询的结果的字段进行过滤	
``` 
//将列表signDetails只显示delete为0的
db.expressSigned.aggregate([
    {
        $project: {
            "signDetails": {
                $filter: {
                    input: "$signDetails",
                    as: "item",
                    cond: { 
                        $eq: [ '$$item.delete', 0 ]
                    }
                }
            }
        }
    }
])
``` 
``` java 	
//将列表signDetails只显示delete为0的
       Aggregation project = newAggregation(
                project().and(filter("signDetails")
                        .as("item")
                        .by(valueOf( "item.delete").equalToValue(0)))
                        .as("signDetails")
        );
      ExpressSigned expressSigned2 = mongoTemplate.aggregate(project, "expressSigned",ExpressSigned.class).getUniqueMappedResult();

      
//将列表details只显示realTime再某个区间内的
      Aggregation aggregation = newAggregation(match(Criteria.where("deviceId").is(deviceId)),
        project().and(filter("details")
                .as("item")
                .by(BooleanOperators.And.and(ComparisonOperators.Lte.valueOf("item.realTime").lessThanEqualToValue(endTime + 60000),
                        ComparisonOperators.Gte.valueOf("item.realTime").greaterThanEqualToValue(startTime - 60000))))
                .as("details")

      );
      List<DeviceStatusDataLog> results = mongoTemplate.aggregate(aggregation, "deviceStatusDataLog", DeviceStatusDataLog.class).getMappedResults();
```

```
$addFields 增加字段
$replaceRoot 可以将该字段成为父节点而忽略其他字段
$project 控制是否忽略字段，修改字段值等，也可以将多个属性变成数组（官方文档栗子）
$lookup 关联查询 可以在addFileds或者$project使用$toObjectId转换id
$redact 对当前字段和所有内嵌文档的字段进行过滤
$sample 随机取样
$limit取前几 $skip 跳过前几
$unwind 将数组降维，共同字段冗余
$sortByCount 对数组进行统计
$arrayElemAt 取数组中的索引   { $arrayElemAt: [ [ 1, 2, 3 ], 0 ] }  ：  1
$mergeObjects 合并各个对象字段，相同字段取后面的值    $mergeObjects：[{a：1}，{a：2，b：2}，{a：3，c：3}]   :  {a：3，b：2，c：3} 

多个使用：
$ replaceRoot ： {  newRoot ： {  $ mergeObjects ： [  {  $ arrayElemAt ： [  “$ fromItems” ， 0  ]  }， “$$ ROOT”  ]  }  }

$arrayToObject 将数组转成object(list转成map, list中必须有k,v字段)
$concatArrays 连接数组
$objectToArray  
```
```
$group后转成map：
聚合最后再加：
{$group:{_id:null, 'data': { '$push': { 'k': '$_id', 'v': '$errorCount'}}}},
{$replaceRoot:{'newRoot':{'$arrayToObject':'$data'}}}
```

