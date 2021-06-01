package com.wenyu7980.query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

public class QueryCompareTest {

    @Test
    public void testNullable() {
        Assert.assertFalse(QueryCompare.EQ.nullable());
        Assert.assertTrue(QueryCompare.ISNULL.nullable());
        Assert.assertTrue(QueryCompare.NOTNULL.nullable());
    }

    @Test
    public void testQE() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.equal(expression, 0)).thenReturn(predicate);
        Predicate result = QueryCompare.EQ.predicate(expression, criteriaBuilder, Arrays.asList(0));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).equal(expression, 0);

    }

    @Test
    public void testQEExpression() {
        Expression e1 = Mockito.mock(Expression.class);
        Expression<Integer> e2 = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.equal(e1, e2)).thenReturn(predicate);
        Predicate result = QueryCompare.EQ.predicateExpression(e1, criteriaBuilder, Arrays.asList(e2));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).equal(e1, e2);
    }

    @Test
    public void testLT() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.lessThan(expression, 0)).thenReturn(predicate);
        Predicate result = QueryCompare.LT.predicate(expression, criteriaBuilder, Arrays.asList(0));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).lessThan(expression, 0);
    }

    @Test
    public void testLTExpression() {
        Expression e1 = Mockito.mock(Expression.class);
        Expression<Integer> e2 = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.lessThan(e1, e2)).thenReturn(predicate);
        Predicate result = QueryCompare.LT.predicateExpression(e1, criteriaBuilder, Arrays.asList(e2));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).lessThan(e1, e2);
    }

    @Test
    public void testNE() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.notEqual(expression, 0)).thenReturn(predicate);
        Predicate result = QueryCompare.NE.predicate(expression, criteriaBuilder, Arrays.asList(0));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).notEqual(expression, 0);
    }

    @Test
    public void testNEExpression() {
        Expression e1 = Mockito.mock(Expression.class);
        Expression<Integer> e2 = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.notEqual(e1, e2)).thenReturn(predicate);
        Predicate result = QueryCompare.NE.predicateExpression(e1, criteriaBuilder, Arrays.asList(e2));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).notEqual(e1, e2);
    }

    @Test
    public void testLE() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.lessThanOrEqualTo(expression, 0)).thenReturn(predicate);
        Predicate result = QueryCompare.LE.predicate(expression, criteriaBuilder, Arrays.asList(0));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).lessThanOrEqualTo(expression, 0);
    }

    @Test
    public void testLEExpression() {
        Expression e1 = Mockito.mock(Expression.class);
        Expression<Integer> e2 = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.lessThanOrEqualTo(e1, e2)).thenReturn(predicate);
        Predicate result = QueryCompare.LE.predicateExpression(e1, criteriaBuilder, Arrays.asList(e2));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).lessThanOrEqualTo(e1, e2);
    }

    @Test
    public void testGT() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.greaterThan(expression, 0)).thenReturn(predicate);
        Predicate result = QueryCompare.GT.predicate(expression, criteriaBuilder, Arrays.asList(0));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).greaterThan(expression, 0);
    }

    @Test
    public void testGTExpression() {
        Expression e1 = Mockito.mock(Expression.class);
        Expression<Integer> e2 = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.greaterThan(e1, e2)).thenReturn(predicate);
        Predicate result = QueryCompare.GT.predicateExpression(e1, criteriaBuilder, Arrays.asList(e2));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).greaterThan(e1, e2);
    }

    @Test
    public void testGE() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.greaterThanOrEqualTo(expression, 0)).thenReturn(predicate);
        Predicate result = QueryCompare.GE.predicate(expression, criteriaBuilder, Arrays.asList(0));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).greaterThanOrEqualTo(expression, 0);
    }

    @Test
    public void testGEExpression() {
        Expression e1 = Mockito.mock(Expression.class);
        Expression<Integer> e2 = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.greaterThanOrEqualTo(e1, e2)).thenReturn(predicate);
        Predicate result = QueryCompare.GE.predicateExpression(e1, criteriaBuilder, Arrays.asList(e2));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).greaterThanOrEqualTo(e1, e2);
    }

    @Test
    public void testLIKE() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.like(expression, "%x%")).thenReturn(predicate);
        Predicate result = QueryCompare.LIKE.predicate(expression, criteriaBuilder, Arrays.asList("x"));
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).like(expression, "%x%");
    }

    @Test(expected = RuntimeException.class)
    public void testLIKEExpression() {
        Expression e1 = Mockito.mock(Expression.class);
        Expression<Integer> e2 = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.greaterThanOrEqualTo(e1, e2)).thenReturn(predicate);
        QueryCompare.LIKE.predicateExpression(e1, criteriaBuilder, Arrays.asList(e2));
    }

    @Test
    public void testIN() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        List<Integer> list = Arrays.asList(0, 1);
        Mockito.when(expression.in(list)).thenReturn(predicate);
        Predicate result = QueryCompare.IN.predicate(expression, criteriaBuilder, list);
        Assert.assertEquals(predicate, result);
        Mockito.verify(expression, Mockito.times(1)).in(list);
    }

    @Test(expected = RuntimeException.class)
    public void testINExpression() {
        QueryCompare.IN.predicateExpression(null, null, null);
    }

    @Test
    public void testISNULL() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.isNull(expression)).thenReturn(predicate);
        Predicate result = QueryCompare.ISNULL.predicate(expression, criteriaBuilder, null);
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).isNull(expression);
    }

    @Test(expected = RuntimeException.class)
    public void testISNULLExpression() {
        QueryCompare.ISNULL.predicateExpression(null, null, null);
    }

    @Test
    public void testNOTNULL() {
        Expression expression = Mockito.mock(Expression.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilder.isNotNull(expression)).thenReturn(predicate);
        Predicate result = QueryCompare.NOTNULL.predicate(expression, criteriaBuilder, null);
        Assert.assertEquals(predicate, result);
        Mockito.verify(criteriaBuilder, Mockito.times(1)).isNotNull(expression);
    }

    @Test(expected = RuntimeException.class)
    public void testNOTNULLExpression() {
        QueryCompare.NOTNULL.predicateExpression(null, null, null);
    }

}