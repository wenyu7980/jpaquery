package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
public class QueryConditionExpression<T extends Comparable<T>> implements QueryPredicateExpress {

    private final boolean condition;
    private final String name;
    private final QueryComparable compare;
    private final List<String> others;

    private QueryConditionExpression(boolean condition, String name, QueryComparable compare, List<String> others) {
        this.condition = condition;
        this.name = name;
        this.compare = compare;
        this.others = others;
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param others
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryConditionExpression<T> of(String name, QueryComparable compare,
      String... others) {
        return of(true, name, compare, others);
    }

    public static <T extends Comparable<T>> QueryConditionExpression<T> of(boolean condition, String name,
      QueryComparable compare, String... other) {
        return new QueryConditionExpression<>(condition, name, compare, Arrays.asList(other));
    }

    @Override
    public Predicate predicate(final From<?, ?> from, final CriteriaBuilder criteriaBuilder) {
        List<Expression<T>> list = new ArrayList<>();
        for (String n : this.others) {
            list.add(from.get(n));
        }
        return compare.predicateExpression(from.get(this.name), criteriaBuilder, list);
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
        QueryConditionExpression<?> that = (QueryConditionExpression<?>) object;
        return Objects.equals(name, that.name) && compare == that.compare && Objects.equals(others, that.others);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, compare, others);
    }
}
