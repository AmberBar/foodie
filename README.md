# foodie
## 使用JRebel热部署
### Jrebel激活
服务器地址：https://jrebel.qekang.com/{GUID}  
在线GUID地址：[在线生成GUID](https://www.guidgen.com/)

## 分页插件PageHelper
* 引入依赖
```
<!-- 分页插件 Mybatis PageHelper-->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.2.12</version>
</dependency>
```
* 配置
```yml
pagehelper:
  helper-dialect: mysql
  support-methods-arguments: true
```
* 使用分页插件
```java
PageHelper.startPage(page, pageSize);
```

* 转换
```java
PageInfo pageInfo = new PageInfo(list);
PageResult pageResult = new PageResult();
pageResult.setPage(page);
pageResult.setTotal(pageInfo.getPages());
pageResult.setRecords(pageInfo.getTotal());
pageResult.setRows(list);
```
## 内网穿刺
[https://natapp.cn/article/natapp_newbie](https://natapp.cn/article/natapp_newbie)

## redis
使用redis-cli查看数据时乱码的解决办法
```java
redis-cli --raw
```