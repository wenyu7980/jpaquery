package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Copyright wenyu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 条件表单式
 * @author:wenyu
 * @date:2019/10/22
 */
public class QueryCondition<T extends Comparable<T>> implements QueryPredicateExpress {

    private final boolean condition;
    private final String name;
    private final QueryComparable compare;
    private final List<T> values;

    private QueryCondition(boolean condition, String name, QueryComparable compare, List<T> values) {
        this.name = name;
        this.condition = condition;
        this.compare = compare;
        this.values = values;
    }

    /**
     * 查询条件
     * @param condition
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> of(boolean condition, String name,
      QueryComparable compare, @NotNull Collection<T> values) {
        assert values != null;
        List<T> collect = values.stream().filter(v -> v != null || !compare.removeNull()).collect(Collectors.toList());
        return new QueryCondition<>(condition && (compare.nullable() || collect.size() > 0), name, compare, collect);
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> of(String name, QueryComparable compare,
      @NotNull Collection<T> values) {
        return of(true, name, compare, values);
    }

    /**
     *
     * @param condition
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> of(boolean condition, String name,
      QueryComparable compare, T... values) {
        assert values.length > 0;
        return QueryCondition.of(condition, name, compare, Arrays.asList(values));
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> of(String name, QueryComparable compare, T... values) {
        return of(true, name, compare, values);
    }

    /**
     *
     * @param condition
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> ofNullable(boolean condition, String name,
      QueryComparable compare, Collection<T> values) {
        List<T> collect = new ArrayList<>();
        if (values != null) {
            collect = values.stream().filter(v -> v != null || !compare.removeNull()).collect(Collectors.toList());
        }
        if (collect.size() == 0) {
            collect.add(null);
        }
        return new QueryCondition<>(condition, name, compare, collect);
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> ofNullable(String name, QueryComparable compare,
      Collection<T> values) {
        return ofNullable(true, name, compare, values);
    }

    /**
     *
     * @param condition
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> ofNullable(boolean condition, String name,
      QueryComparable compare, T... values) {
        return ofNullable(condition, name, compare, Arrays.asList(values));
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> ofNullable(String name, QueryComparable compare,
      T... values) {
        return ofNullable(true, name, compare, values);
    }

    @Override
    public Predicate predicate(final From<?, ?> from, final CriteriaBuilder criteriaBuilder) {
        return compare.predicate(from.get(this.name), criteriaBuilder, this.values);
    }

    @Override
    public boolean nonNull() {
        return this.condition;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        QueryCondition<?> that = (QueryCondition<?>) object;
        return Objects.equals(name, that.name) && compare == that.compare && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, compare, values);
    }
}
