package com.wenyu7980;

import com.wenyu7980.entity.AddressEntity;
import com.wenyu7980.entity.InfoEntity;
import com.wenyu7980.entity.MobileEntity;
import com.wenyu7980.entity.UserEntity;
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
                .addAnnotatedClass(UserEntity.class).buildMetadata()
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
        QueryLogic logic = QueryLogic.and(QueryCondition
                        .of("username", QueryCompare.EQ, "username1"), QueryJoin
                        .join("info", QueryLogic.and(QueryCondition
                                .of("info", QueryCompare.LIKE, "info"), QueryJoin
                                .join("address", QueryLogic.and(QueryCondition
                                        .of("address", QueryCompare.NE, "add"))))),
                QueryJoin.join("mobile", QueryCondition
                        .of("mobile", QueryCompare.IN, "mobilex1")));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertTrue(list.size() == 1);
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
                        QueryCondition.of("mobile", QueryCompare.NOTNULL)));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertTrue(list.size() == 2);
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
        QueryLogic logic = QueryLogic
                .and(QueryCondition.of("username", QueryCompare.EQ, nullStr),
                        QueryJoin.join("info", QueryLogic.and(QueryCondition
                                        .of("info", QueryCompare.LIKE, nullStr),
                                QueryJoin.join("address", QueryLogic
                                        .and(QueryCondition
                                                .of("address", QueryCompare.NE,
                                                        nullStr))))), QueryJoin
                                .join("mobile", QueryCondition
                                        .of("mobile", QueryCompare.EQ,
                                                nullStr)));
        query.where(logic.predicate(root, criteriaBuilder));
        List<UserEntity> list = entityManager.createQuery(query)
                .getResultList();
        Assert.assertTrue(list.size() == 2);
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
        Assert.assertTrue(list.size() == 2);
    }

    public void insertData(int i) {

        //打开事务
        Transaction transaction = session.beginTransaction();
        AddressEntity addressEntity = new AddressEntity("address" + i,
                "addressx" + 1);
        InfoEntity infoEntity = new InfoEntity("info" + i, "infox" + i,
                addressEntity);
        MobileEntity mobileEntity = new MobileEntity("mobile" + i,
                "mobilex" + i);
        UserEntity userEntity = new UserEntity("user" + i, "username" + i,
                infoEntity, mobileEntity);
        session.save(addressEntity);
        session.save(infoEntity);
        session.save(mobileEntity);
        session.save(userEntity);
        transaction.commit();
    }

}
