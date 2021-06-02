package com.wenyu7980.query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.*;
import java.util.Objects;

public class QueryExistsTest {
    @Test
    public void testExists() {
        QueryPredicateExpress express = Mockito.mock(QueryPredicateExpress.class);
        Mockito.when(express.nonNull()).thenReturn(true);
        QueryExistPredicate qf = Mockito.mock(QueryExistPredicate.class);
        // call
        QueryExists exists = QueryExists.exists(Integer.class, qf, express);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = Mockito.mock(CriteriaQuery.class);
        Subquery subquery = Mockito.mock(Subquery.class);
        Root root = Mockito.mock(Root.class);
        Mockito.when(criteriaBuilder.createQuery(Integer.class)).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.subquery(Integer.class)).thenReturn(subquery);
        Mockito.when(subquery.from(Integer.class)).thenReturn(root);
        From from = Mockito.mock(From.class);
        Predicate jp = Mockito.mock(Predicate.class);
        Mockito.when(qf.apply(from, root, criteriaBuilder)).thenReturn(jp);
        Predicate ep = Mockito.mock(Predicate.class);
        Mockito.when(express.predicate(root, criteriaBuilder)).thenReturn(ep);
        Predicate wp = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.and(ep, jp)).thenReturn(wp);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.exists(subquery)).thenReturn(predicate);
        Predicate result = exists.predicate(from, criteriaBuilder);
        Assert.assertEquals(predicate, result);
        Mockito.verify(subquery, Mockito.times(1)).select(root);
        Mockito.verify(subquery, Mockito.times(1)).where(wp);
        Assert.assertTrue(exists.nonNull());
    }

    @Test
    public void testExistsNull() {
        QueryPredicateExpress express = Mockito.mock(QueryPredicateExpress.class);
        Mockito.when(express.nonNull()).thenReturn(false);
        QueryExistPredicate qf = Mockito.mock(QueryExistPredicate.class);
        // call
        QueryExists exists = QueryExists.existsNull(Integer.class, qf, express);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = Mockito.mock(CriteriaQuery.class);
        Subquery subquery = Mockito.mock(Subquery.class);
        Root root = Mockito.mock(Root.class);
        Mockito.when(criteriaBuilder.createQuery(Integer.class)).thenReturn(criteriaQuery);
        Mockito.when(criteriaQuery.subquery(Integer.class)).thenReturn(subquery);
        Mockito.when(subquery.from(Integer.class)).thenReturn(root);
        From from = Mockito.mock(From.class);
        Predicate jp = Mockito.mock(Predicate.class);
        Mockito.when(qf.apply(from, root, criteriaBuilder)).thenReturn(jp);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.exists(subquery)).thenReturn(predicate);
        Predicate result = exists.predicate(from, criteriaBuilder);
        Assert.assertEquals(predicate, result);
        Mockito.verify(subquery, Mockito.times(1)).select(root);
        Mockito.verify(subquery, Mockito.times(1)).where(jp);
        Assert.assertTrue(exists.nonNull());
    }

    @Test
    public void testMerge() {
        QueryPredicateExpress express = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress express2 = Mockito.mock(QueryPredicateExpress.class);
        QueryExistPredicate qf = Mockito.mock(QueryExistPredicate.class);
        QueryExists exists = QueryExists.exists(Integer.class, qf, express);
        QueryExists exists2 = QueryExists.exists(Integer.class, qf, express2);
        Assert.assertTrue(exists.merge(exists2));
        Assert.assertTrue(exists2.merge(exists));
        Assert.assertFalse(exists2.merge(express));
        Assert.assertEquals(express, exists.getExpress());
        Assert.assertEquals(QueryExists.exists(Integer.class, qf, express), exists.clone(express));
    }

    @Test
    public void testHashEqual() {
        QueryPredicateExpress express = Mockito.mock(QueryPredicateExpress.class);
        QueryExistPredicate qf = Mockito.mock(QueryExistPredicate.class);
        QueryExists exists = QueryExists.exists(Integer.class, qf, express);
        QueryExists exists2 = QueryExists.exists(Integer.class, qf, express);
        Assert.assertTrue(exists.equals(exists2));
        Assert.assertTrue(exists.equals(exists));
        Assert.assertFalse(exists.equals(null));
        Assert.assertFalse(exists.equals(express));
        Assert.assertTrue(Objects.equals(exists.hashCode(), exists2.hashCode()));
    }
}