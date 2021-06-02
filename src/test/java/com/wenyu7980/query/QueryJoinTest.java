package com.wenyu7980.query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Objects;

public class QueryJoinTest {
    @Test
    public void testJoin() {
        QueryPredicateExpress express = Mockito.mock(QueryPredicateExpress.class);
        Mockito.when(express.nonNull()).thenReturn(true);
        QueryJoin join = QueryJoin.join("name", express);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Join join1 = Mockito.mock(Join.class);
        Mockito.when(from.join("name")).thenReturn(join1);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(express.predicate(join1, criteriaBuilder)).thenReturn(predicate);
        Predicate result = join.predicate(from, criteriaBuilder);
        Assert.assertEquals(predicate, result);
        Assert.assertTrue(join.nonNull());
    }

    @Test
    public void testMerge() {
        QueryPredicateExpress express = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress express2 = Mockito.mock(QueryPredicateExpress.class);
        QueryJoin join1 = QueryJoin.join("name", express);
        QueryJoin join2 = QueryJoin.join("name", express2);
        Assert.assertTrue(join1.merge(join2));
        Assert.assertTrue(join2.merge(join1));
        Assert.assertFalse(join1.merge(express));
        Assert.assertEquals(express, join1.getExpress());
        Assert.assertEquals(QueryJoin.join("name", express), join1.clone(express));
    }

    @Test
    public void testHashEqual() {
        QueryPredicateExpress express = Mockito.mock(QueryPredicateExpress.class);
        QueryJoin join1 = QueryJoin.join("name", express);
        QueryJoin join2 = QueryJoin.join("name", express);
        Assert.assertTrue(join1.equals(join2));
        Assert.assertTrue(join2.equals(join1));
        Assert.assertTrue(join2.equals(join2));
        Assert.assertFalse(join1.equals(null));
        Assert.assertFalse(join1.equals(express));
        Assert.assertTrue(Objects.equals(join1.hashCode(), join2.hashCode()));
    }
}