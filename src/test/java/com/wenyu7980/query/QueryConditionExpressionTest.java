package com.wenyu7980.query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;

public class QueryConditionExpressionTest {
    @Test
    public void testOf() {
        From from = Mockito.mock(From.class);
        Path<Integer> p1 = Mockito.mock(Path.class);
        Path<Integer> p2 = Mockito.mock(Path.class);
        QueryComparable compare = Mockito.mock(QueryComparable.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        Mockito.when(from.get("p1")).thenReturn(p1);
        Mockito.when(from.get("p2")).thenReturn(p2);
        Mockito.when(compare.predicateExpression(p1, criteriaBuilder, Arrays.asList(p2))).thenReturn(predicate);
        QueryPredicateExpress condition = QueryConditionExpression.of("p1", compare, "p2");
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("p1");
        Mockito.verify(from, Mockito.times(1)).get("p2");
        Mockito.verify(compare, Mockito.times(1)).predicateExpression(p1, criteriaBuilder, Arrays.asList(p2));
    }

    @Test
    public void testHashEqual() {
        QueryConditionExpression<Integer> q1 = QueryConditionExpression.of("p1", QueryCompare.EQ, "p2");
        Assert.assertEquals(q1, q1);
        Assert.assertNotEquals(q1, null);
        QueryConditionExpression<Integer> q2 = QueryConditionExpression.of("p1", QueryCompare.EQ, "p2");
        Assert.assertEquals(q1, q2);
        Assert.assertEquals(q1.hashCode(), q2.hashCode());
        QueryConditionExpression<Integer> q3 = QueryConditionExpression.of("p1", QueryCompare.EQ, "p3");
        Assert.assertNotEquals(q1, q3);
        Assert.assertNotEquals(q1.hashCode(), q3.hashCode());

    }
}