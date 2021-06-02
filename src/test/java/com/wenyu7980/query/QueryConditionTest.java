package com.wenyu7980.query;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryConditionTest {
    @Test
    public void testOf() {
        From from = Mockito.mock(From.class);
        Path path = Mockito.mock(Path.class);
        QueryComparable compare = Mockito.mock(QueryComparable.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        Mockito.when(from.get("name")).thenReturn(path);
        Mockito.when(compare.predicate(path, criteriaBuilder, Arrays.asList(0, 1))).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.of("name", compare, 0, 1);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, Arrays.asList(0, 1));
    }

    @Test
    public void testOfCollection() {
        From from = Mockito.mock(From.class);
        Path path = Mockito.mock(Path.class);
        QueryComparable compare = Mockito.mock(QueryComparable.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        Mockito.when(from.get("name")).thenReturn(path);
        Mockito.when(compare.predicate(path, criteriaBuilder, Arrays.asList(0, 1))).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.of("name", compare, Arrays.asList(0, 1));
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, Arrays.asList(0, 1));
    }

    @Test
    public void testOfNullable() {
        From from = Mockito.mock(From.class);
        Path path = Mockito.mock(Path.class);
        QueryComparable compare = Mockito.mock(QueryComparable.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        Mockito.when(from.get("name")).thenReturn(path);
        Mockito.when(compare.predicate(path, criteriaBuilder, Arrays.asList(0, 1))).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.ofNullable("name", compare, 0, 1);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, Arrays.asList(0, 1));
    }

    @Test
    public void testOfNullableCollection() {
        From from = Mockito.mock(From.class);
        Path path = Mockito.mock(Path.class);
        QueryComparable compare = Mockito.mock(QueryComparable.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        Mockito.when(from.get("name")).thenReturn(path);
        Mockito.when(compare.predicate(path, criteriaBuilder, Arrays.asList(0, 1))).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.ofNullable("name", compare, Arrays.asList(0, 1));
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, Arrays.asList(0, 1));
    }

    @Test
    public void testOfNullableNull() {
        From from = Mockito.mock(From.class);
        Path path = Mockito.mock(Path.class);
        QueryComparable compare = Mockito.mock(QueryComparable.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.nullable()).thenReturn(true);
        Mockito.when(from.get("name")).thenReturn(path);
        List<Integer> list = new ArrayList<>();
        list.add(null);
        Mockito.when(compare.predicate(path, criteriaBuilder, list)).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.ofNullable("name", compare);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, list);
    }

    @Test
    public void testHashEqual() {
        QueryCondition<Integer> q1 = QueryCondition.of("name", QueryCompare.EQ, 0, 1);
        Assert.assertEquals(q1, q1);
        Assert.assertNotEquals(q1, null);
        QueryCondition<Integer> q2 = QueryCondition.of("name", QueryCompare.EQ, 0, 1);
        Assert.assertEquals(q1, q2);
        Assert.assertEquals(q1.hashCode(), q2.hashCode());
        QueryCondition<Integer> q3 = QueryCondition.of("name", QueryCompare.EQ, 1);
        Assert.assertNotEquals(q1, q3);
        Assert.assertNotEquals(q1.hashCode(), q3.hashCode());
        QueryCondition<Integer> q4 = QueryCondition.of("name", QueryCompare.EQ, 1, 0);
        Assert.assertNotEquals(q1, q4);
        Assert.assertNotEquals(q1.hashCode(), q4.hashCode());

    }

}