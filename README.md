

# JPA查询
```xml
<!-- https://mvnrepository.com/artifact/com.wenyu7980/statemachine -->
<dependency>
    <groupId>com.wenyu7980</groupId>
    <artifactId>jpaQuery</artifactId>
    <version>{version}</version>
</dependency>
```

v2.0.0

新增逻辑合并功能

v2.2.0

exist查询调整


-----------------

***JPA***是Java Persistence API的简称，中文名Java持久层API，是JDK 5.0注解或XML描述对象－关系表的映射关系（***ORM***），并将运行期的实体对象持久化到数据库中。

国内使用Mybatis的比较多，其中一个比较重要的原因是，因为Mybatis使用原生SQL来实现查询，所以写起来更加直观便捷，但是，JPA的话就没有那么便捷了（虽然JPA也可以去写原生的SQL，比如Spring Data中可以使用@Query(native））。

JPA 是一个标准，本库引用的是 org.hibernate.javax.persistence hibernate-jpa-2.1-api 是Hibernate的标准，Spring Data也是使用的此标准，所以本库可以配合Hibernate使用，也可以配合Spring Data使用(JpaSpecificationExecutor)。

----------------
## 接口 QueryPredicateExpress
基础接口
```java
/**
 * 判定表单式
 * @author:wenyu
 * @date:2019/10/22
 */
public interface QueryPredicateExpress {
    /**
     * 判定表达式转换为JPA的判定表单式
     * @param from 表
     * @param criteriaBuilder 标准构造器
     * @return
     */
    Predicate predicate(final From<?, ?> from,
            final CriteriaBuilder criteriaBuilder);

    /**
     * 判定表达式的比较值是否为非空
     * @return
     */
    default boolean nonNull() {
        return true;
    }
    
    /**
     * 合并
     * @param express
     * @param parent    上级
     * @return true 合并
     *         false 未合并
     */
    default boolean merge(QueryPredicateExpress express,
            QueryPredicateExpress parent) {
        return false;
    }

    /**
     * 逻辑运算
     * @param e1
     * @param e2
     * @return
     */
    default QueryPredicateExpress logic(QueryPredicateExpress e1,
            QueryPredicateExpress e2) {
        return null;
    }
}
```
## 具体实现类
+ QueryCondition
>条件判断
使用QueryCompare枚举，主要是等于，不等于，大于，Like，in,null 等
如果判断值为null时，则**不会**参与到判断中
+ QueryConditionExpression
>条件判断
使用QueryCompare枚举，主要是等于，不等于，大于，Like，in,null 等
判断值使用的是表中字段，例如 select * from A a where a.b = a.c
+ QueryConditionNull
>条件判断
使用QueryCompare枚举，主要是等于，不等于，大于，Like，in,null 等
如果判断值为null时，依旧会参与到判断中
+ QueryExists
>存在判断
如果logic的nonNull为false时，则**不会**参与到判断中
+ QueryExistsNull
>存在判断
存在判断
如果logic的nonNull为false时，依旧会参与到判断中
+ QueryJoin
>表拦截
如果logic的nonNull为false时，则**不会**参与到判断中
+ QueryLogic
>逻辑
与 and 或 or 非 not
QueryLogicXor
>异或 xor

## 使用
### 表结构 表A：
|id|name|age|
|----|----|----|
|1|Tom|18|
|2|Jerry|19|
### SQL：
SELECT * FROM A a WHERE a.id = ? and a.name = ? and a.age = ?;

### JPA查询：
```java
QueryPredicateExpress express = QueryLogic.and(
    QueryCondition.of("id",EQ,id),
    QueryCondition.of("name",EQ,name),
    QueryCondition.of("age",EQ,age)
);
```
### Spring Data：
```java
public class QueryService<T> {
    @Autowired
    private JpaSpecificationExecutor<T> executor;

    public Page<T> findByPredicateAndPage(QueryPredicateExpress express, Pageable page) {
      return executor.findAll(
      (Root<T> root,CriteriaQuery<?> query,CriteriaBuilder criteriaBuilder) ->{
            return criteriaBuilder
                    .and(express.predicate(root, criteriaBuilder));
        }, page);
    }
}
// 省略声明。。。
QueryPredicateExpress express = QueryLogic.and(
    QueryCondition.of("id",EQ,id),
    QueryCondition.of("name",EQ,name),
    QueryCondition.of("age",EQ,age)
);

// 省略声明。。。
service.findByPredicateAndPage(express,page);


```
