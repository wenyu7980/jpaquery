package com.wenyu7980.query;

import com.wenyu7980.query.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author:wenyu
 * @date:2019/10/23
 */
public class QueryLogicTest {

    private static final String FILEPATH = "src/test/resources/hibernate.properties";
    private SessionFactory sessionFactory;
    private Session session;

    @Before
    public void init() {
        File file = new File(FILEPATH);
        //创建服务注册对象
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                //加载Hibernate配置文件
                .loadProperties(file).build();

        //创建会话工厂对象
        sessionFactory = new MetadataSources(registry)
                //将持久化类添加到元数据源
                .addAnnotatedClass(MobileEntity.class)
                .addAnnotatedClass(AddressEntity.class)
                .addAnnotatedClass(InfoEntity.class)
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(LabelEntity.class).buildMetadata()
                .buildSessionFactory();
        //打开会话
        session = sessionFactory.openSession();
        insertData(1);
        insertData(2);
    }

    @Test
    public void testLogicJoin() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.and(QueryConditionNull
                        .of("username", QueryCompare.EQ, "username1"), QueryJoin
                        .join("info", QueryLogic.and(QueryConditionNull
                                .of("info", QueryCompare.LIKE, "info"), QueryJoin
                                .join("address", QueryLogic.and(QueryConditionNull
                                        .of("address", QueryCompare.NE, "add"))))),
                QueryJoin.join("mobile", QueryConditionNull
                        .of("mobile", QueryCompare.IN, "mobilex1")));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(list.get(0).getId(), "user1");
    }

    @Test
    public void testLogicExists() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.and(QueryExists
                .exists("mobile", "id", MobileEntity.class,
                        QueryConditionNull.of("mobile", QueryCompare.NOTNULL)));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testLogicExistsMany() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.and(QueryExistsMany
                .exists("id", "id", "users", LabelEntity.class,
                        QueryConditionNull.of("id", QueryCompare.NOTNULL)));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testLogicExistsMany2() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.and(QueryExistsMany
                .exists("id", "id", "users", LabelEntity.class,
                        QueryLogic.and()));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testLogicExistsNull() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        String nullStr = null;
        QueryLogic logic = QueryLogic.and(QueryExistsNull
                .exists("mobile", "id", MobileEntity.class,
                        QueryCondition.of("mobile", QueryCompare.EQ, nullStr)));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testLogicNull2() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        String nullStr = null;
        QueryLogic logic = QueryLogic.and(QueryConditionNull
                        .of("username", QueryCompare.EQ, nullStr), QueryJoin
                        .join("info", QueryLogic.and(QueryConditionNull
                                .of("info", QueryCompare.LIKE, nullStr), QueryJoin
                                .join("address", QueryLogic.and(QueryConditionNull
                                        .of("address", QueryCompare.NE, nullStr))))),
                QueryJoin.join("mobile", QueryConditionNull
                        .of("mobile", QueryCompare.EQ, nullStr)));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testLogicNull() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.and();
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testLogicXor() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryPredicateExpress express = QueryLogicXor.xor(QueryConditionNull
                        .of("username", QueryCompare.EQ, "usernamex1"),
                QueryConditionNull
                        .of("username", QueryCompare.NE, "usernamex1"));
        query.where(express.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testLogicXor2() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryPredicateExpress express = QueryLogic.xor(QueryConditionNull
                        .of("username", QueryCompare.EQ, "usernamex1"),
                QueryConditionNull
                        .of("username", QueryCompare.NE, "usernamex1"));
        query.where(express.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testConditionNull() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        String nullStr = null;
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryPredicateExpress express = QueryConditionNull
                .of("username", QueryCompare.EQ, nullStr);
        query.where(express.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testConditionExpression() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryPredicateExpress express = QueryConditionExpression
                .of("username", QueryCompare.EQ, "username");
        query.where(express.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void testNonNull3() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        String nullStr = null;
        QueryLogic logic = QueryLogic.and(QueryConditionNull
                        .of("username", QueryCompare.EQ, nullStr), QueryJoin
                        .join("info", QueryLogic.and(QueryConditionNull
                                .of("info", QueryCompare.LIKE, nullStr), QueryJoin
                                .join("address", QueryLogic.and(QueryConditionNull
                                        .of("address", QueryCompare.NE, nullStr))))),
                QueryJoin.join("mobile", QueryConditionNull
                        .of("mobile", QueryCompare.EQ, nullStr)));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(0, list.size());
    }

    /**
     * user table
     * user1,username1
     *
     * address
     * address1,addressx1
     *
     * mobile
     * mobile1,mobilex1
     * @param i
     */
    public void insertData(int i) {
        //打开事务
        Transaction transaction = session.beginTransaction();
        LabelEntity labelEntity1 = new LabelEntity("L" + i, "LABEL1");
        session.save(labelEntity1);
        LabelEntity labelEntity2 = new LabelEntity("L" + i + "x", "LABEL1");
        session.save(labelEntity2);
        AddressEntity addressEntity = new AddressEntity("address" + i,
                "addressx" + 1);
        InfoEntity infoEntity = new InfoEntity("info" + i, "infox" + i,
                addressEntity);
        MobileEntity mobileEntity = new MobileEntity("mobile" + i,
                "mobilex" + i);
        UserEntity userEntity = new UserEntity("user" + i, "username" + i,
                infoEntity, mobileEntity,
                Arrays.asList(labelEntity1, labelEntity2));
        session.save(addressEntity);
        session.save(infoEntity);
        session.save(mobileEntity);
        session.save(userEntity);
        transaction.commit();
    }

    @Test
    public void testConditionEquals() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.and(QueryCondition
                        .of("username", QueryCompare.EQ, "username1"),
                QueryCondition.of("username", QueryCompare.EQ, "username1"));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testConditionEquals2() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.and(QueryLogic.and(QueryCondition
                .of("username", QueryCompare.EQ, "username1")), QueryLogic
                .and(QueryCondition
                        .of("username", QueryCompare.EQ, "username1")));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testMerge1() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.or(QueryLogic
                        .or(QueryCondition.of("username", QueryCompare.EQ, "username1"),
                                QueryCondition
                                        .of("username", QueryCompare.EQ, "username1")),
                QueryLogic.or(QueryCondition
                        .of("username", QueryCompare.EQ, "username1")),
                QueryCondition.of("username", QueryCompare.EQ, "username1"));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testMerge2() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic.or(QueryLogic
                        .or(QueryCondition.of("id", QueryCompare.EQ, "user1"),
                                QueryCondition
                                        .of("username", QueryCompare.EQ, "username1")),
                QueryLogic.or(QueryCondition
                        .of("username", QueryCompare.EQ, "username1")),
                QueryCondition.of("id", QueryCompare.EQ, "user1"));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testMergeJoin1() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryLogic logic = QueryLogic
                .or(QueryCondition.of("username", QueryCompare.EQ, "username1"),
                        QueryJoin.join("info", QueryLogic.and(QueryCondition
                                        .of("info", QueryCompare.EQ, "infox1"),
                                QueryJoin.join("address", QueryLogic
                                        .and(QueryCondition
                                                .of("address", QueryCompare.EQ,
                                                        "addressx1"))))),
                        QueryJoin.join("info", QueryLogic.and(QueryCondition
                                .of("id", QueryCompare.EQ, "info1"))));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(list.get(0).getId(), "user1");
    }

    @Test
    public void testMergeJoin2() {
        EntityManager entityManager = session.getEntityManagerFactory()
                .createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder
                .createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        QueryPredicateExpress logic = QueryLogic.and(QueryCondition
                .of("username", QueryCompare.EQ, "username1"), QueryJoin
                .join("info", QueryLogic.and(QueryCondition
                        .of("info", QueryCompare.EQ, "infox1"), QueryJoin
                        .join("address", QueryLogic.and(QueryCondition
                                .of("address", QueryCompare.EQ,
                                        "addressx1"))))), QueryJoin.join("info",
                QueryLogic.not(QueryCondition
                        .of("id", QueryCompare.IN, "info2"))));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(list.get(0).getId(), "user1");
    }
}
