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
    public void testOfCollection() {
        List<Integer> list = Arrays.asList(0, 1);
        QueryCompare compare = Mockito.mock(QueryCompare.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);
        Mockito.when(from.get("name")).thenReturn(path);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.predicate(path, criteriaBuilder, list)).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.of("name", compare, list);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, list);
    }

    @Test
    public void testOfNullableCollection() {
        List<Integer> list = Arrays.asList(0, 1);
        QueryCompare compare = Mockito.mock(QueryCompare.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);
        Mockito.when(from.get("name")).thenReturn(path);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.predicate(path, criteriaBuilder, list)).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.ofNullable("name", compare, list);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertFalse(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, list);
    }

    @Test
    public void testOf() {
        QueryCompare compare = Mockito.mock(QueryCompare.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);
        Mockito.when(from.get("name")).thenReturn(path);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.predicate(path, criteriaBuilder, Arrays.asList(0))).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.of("name", compare, 0);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, Arrays.asList(0));
    }

    @Test
    public void testOfCondition() {
        QueryCompare compare = Mockito.mock(QueryCompare.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);
        Mockito.when(from.get("name")).thenReturn(path);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.predicate(path, criteriaBuilder, Arrays.asList(0))).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.ofNullable("name", compare, 0);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, Arrays.asList(0));
    }

    @Test
    public void testOfConditionNull() {
        QueryCompare compare = Mockito.mock(QueryCompare.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        List<Integer> list = new ArrayList<>();
        list.add(null);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);
        Mockito.when(from.get("name")).thenReturn(path);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.predicate(path, criteriaBuilder, list)).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.of("name", compare, list);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertTrue(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, list);
    }

    @Test
    public void testOfConditionNull2() {
        QueryCompare compare = Mockito.mock(QueryCompare.class);
        Mockito.when(compare.nullable()).thenReturn(false);
        Mockito.when(compare.removeNull()).thenReturn(true);
        List<Integer> list = new ArrayList<>();
        list.add(null);
        From from = Mockito.mock(From.class);
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);
        Mockito.when(from.get("name")).thenReturn(path);
        Predicate predicate = Mockito.mock(Predicate.class);
        Mockito.when(compare.predicate(path, criteriaBuilder, new ArrayList<Integer>())).thenReturn(predicate);
        QueryCondition<Integer> condition = QueryCondition.of("name", compare, list);
        Predicate result = condition.predicate(from, criteriaBuilder);
        Assert.assertFalse(condition.nonNull());
        Assert.assertEquals(predicate, result);
        Mockito.verify(from, Mockito.times(1)).get("name");
        Mockito.verify(compare, Mockito.times(1)).predicate(path, criteriaBuilder, new ArrayList<Integer>());
    }

    @Test
    public void testNonNull() {

    }
}