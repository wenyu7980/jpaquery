package com.wenyu7980.query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;

public class QueryLogicTest {
    @Test
    public void testOf() {
        QueryLogicOperator logic = Mockito.mock(QueryLogicOperator.class);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e2 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e3 = Mockito.mock(QueryPredicateExpress.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Predicate p1 = Mockito.mock(Predicate.class);
        Predicate p2 = Mockito.mock(Predicate.class);
        Predicate p3 = Mockito.mock(Predicate.class);
        Mockito.when(e1.nonNull()).thenReturn(true);
        Mockito.when(e2.nonNull()).thenReturn(true);
        Mockito.when(e3.nonNull()).thenReturn(true);
        Mockito.when(e1.predicate(from, criteriaBuilder)).thenReturn(p1);
        Mockito.when(e2.predicate(from, criteriaBuilder)).thenReturn(p2);
        Mockito.when(e3.predicate(from, criteriaBuilder)).thenReturn(p3);
        Mockito.when(logic.predicate(criteriaBuilder, p1, p2, p3)).thenReturn(predicate);
        QueryLogic queryLogic = QueryLogic.of(logic, e1, e2, e3);
        Predicate result = queryLogic.predicate(from, criteriaBuilder);
        Assert.assertEquals(predicate, result);
        Assert.assertTrue(queryLogic.nonNull());
    }

    @Test
    public void testExpand() {
        QueryLogicOperator logic = Mockito.mock(QueryLogicOperator.class);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e2 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e3 = Mockito.mock(QueryPredicateExpress.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Predicate p1 = Mockito.mock(Predicate.class);
        Predicate p2 = Mockito.mock(Predicate.class);
        Predicate p3 = Mockito.mock(Predicate.class);
        Mockito.when(e1.nonNull()).thenReturn(true);
        Mockito.when(e2.nonNull()).thenReturn(true);
        Mockito.when(e3.nonNull()).thenReturn(true);
        Mockito.when(e1.predicate(from, criteriaBuilder)).thenReturn(p1);
        Mockito.when(e2.predicate(from, criteriaBuilder)).thenReturn(p2);
        Mockito.when(e3.predicate(from, criteriaBuilder)).thenReturn(p3);
        Mockito.when(logic.predicate(criteriaBuilder, p1, p2, p3)).thenReturn(predicate);
        Mockito.when(logic.expand()).thenReturn(true);
        QueryLogic queryLogic = QueryLogic.of(logic, QueryLogic.of(logic, e1), QueryLogic.of(logic, e2, e3));
        Predicate result = queryLogic.predicate(from, criteriaBuilder);
        Assert.assertEquals(predicate, result);
        Assert.assertTrue(queryLogic.nonNull());
    }

    @Test
    public void testMerge() {
        QueryLogicOperator logic = Mockito.mock(QueryLogicOperator.class);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        QueryPredicateMerge m1 = Mockito.mock(QueryPredicateMerge.class);
        QueryPredicateMerge m2 = Mockito.mock(QueryPredicateMerge.class);
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e2 = Mockito.mock(QueryPredicateExpress.class);
        Predicate p1 = Mockito.mock(Predicate.class);
        Predicate p2 = Mockito.mock(Predicate.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(m1.merge(m2)).thenReturn(true);
        Mockito.when(m1.getExpress()).thenReturn(e1);
        Mockito.when(m2.getExpress()).thenReturn(e2);
        Mockito.when(m1.nonNull()).thenReturn(true);
        Mockito.when(m2.nonNull()).thenReturn(true);
        Mockito.when(m1.predicate(from, criteriaBuilder)).thenReturn(p1);
        Mockito.when(m2.predicate(from, criteriaBuilder)).thenReturn(p2);
        Mockito.when(logic.predicate(criteriaBuilder, p1, p2)).thenReturn(predicate);
        QueryLogic of = QueryLogic.of(logic, m1, m2);
        Mockito.verify(m1, Mockito.times(1)).clone(QueryLogic.of(logic, e1, e2));
    }

    @Test
    public void testHashEqual() {
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        Mockito.when(e1.nonNull()).thenReturn(true);
        QueryLogic l1 = QueryLogic.and(e1);
        QueryLogic l2 = QueryLogic.and(e1);
        Assert.assertEquals(l1, l2);
        Assert.assertEquals(l1, l1);
        Assert.assertEquals(l1.hashCode(), l2.hashCode());
        Assert.assertNotEquals(l1, null);
    }

    @Test
    public void testAnd() {
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate p1 = Mockito.mock(Predicate.class);
        Predicate p2 = Mockito.mock(Predicate.class);
        Predicate p3 = Mockito.mock(Predicate.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.and(p1, p2, p3)).thenReturn(predicate);
        Predicate result = QueryLogic.Logic.AND.predicate(criteriaBuilder, p1, p2, p3);
        Assert.assertEquals(predicate, result);
    }

    @Test
    public void testAnd2() {
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e2 = Mockito.mock(QueryPredicateExpress.class);
        QueryLogic of = QueryLogic.of(QueryLogic.Logic.AND, e1, e2);
        QueryLogic and = QueryLogic.and(e1, e2);
        Assert.assertEquals(of, and);
        QueryLogic and2 = QueryLogic.and(Arrays.asList(e1, e2));
        Assert.assertEquals(of, and2);
    }

    @Test
    public void testOr() {
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate p1 = Mockito.mock(Predicate.class);
        Predicate p2 = Mockito.mock(Predicate.class);
        Predicate p3 = Mockito.mock(Predicate.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.or(p1, p2, p3)).thenReturn(predicate);
        Predicate result = QueryLogic.Logic.OR.predicate(criteriaBuilder, p1, p2, p3);
        Assert.assertEquals(predicate, result);
    }

    @Test
    public void testOr2() {
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e2 = Mockito.mock(QueryPredicateExpress.class);
        QueryLogic of = QueryLogic.of(QueryLogic.Logic.OR, e1, e2);
        QueryLogic or = QueryLogic.or(e1, e2);
        Assert.assertEquals(of, or);
        QueryLogic or2 = QueryLogic.or(Arrays.asList(e1, e2));
        Assert.assertEquals(of, or2);
    }

    @Test
    public void testNot() {
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate p1 = Mockito.mock(Predicate.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(p1.not()).thenReturn(predicate);
        Predicate result = QueryLogic.Logic.NOT.predicate(criteriaBuilder, p1);
        Assert.assertEquals(predicate, result);
    }

    @Test
    public void testNot2() {
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        QueryLogic of = QueryLogic.of(QueryLogic.Logic.NOT, e1);
        QueryLogic or = QueryLogic.not(e1);
        Assert.assertEquals(of, or);
    }

    @Test
    public void testXor() {
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate p1 = Mockito.mock(Predicate.class);
        Predicate p2 = Mockito.mock(Predicate.class);
        Predicate np1 = Mockito.mock(Predicate.class);
        Predicate np2 = Mockito.mock(Predicate.class);
        Predicate x1 = Mockito.mock(Predicate.class);
        Predicate x2 = Mockito.mock(Predicate.class);
        Mockito.when(p1.not()).thenReturn(np1);
        Mockito.when(p2.not()).thenReturn(np2);
        Mockito.when(criteriaBuilder.and(p1, np2)).thenReturn(x1);
        Mockito.when(criteriaBuilder.and(np1, p2)).thenReturn(x2);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.or(x1, x2)).thenReturn(predicate);
        Predicate result = QueryLogic.Logic.XOR.predicate(criteriaBuilder, p1, p2);
        Assert.assertEquals(predicate, result);
    }

    @Test
    public void testXor2() {
        QueryPredicateExpress e1 = Mockito.mock(QueryPredicateExpress.class);
        QueryPredicateExpress e2 = Mockito.mock(QueryPredicateExpress.class);
        QueryLogic of = QueryLogic.of(QueryLogic.Logic.XOR, e1, e2);
        QueryLogic or = QueryLogic.xor(e1, e2);
        Assert.assertEquals(of, or);
    }
}