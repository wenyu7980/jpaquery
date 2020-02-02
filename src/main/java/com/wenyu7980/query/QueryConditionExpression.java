package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
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
public class QueryConditionExpression<T extends Comparable<T>>
        implements QueryPredicateExpress {

    private String name;
    private QueryCompare compare;
    private String other;

    private QueryConditionExpression(String name, QueryCompare compare,
            String other) {
        this.name = name;
        this.compare = compare;
        this.other = other;
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param other
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryConditionExpression<T> of(
            String name, QueryCompare compare, String other) {
        return new QueryConditionExpression<>(name, compare, other);
    }

    @Override
    public Predicate predicate(final From<?, ?> from,
            final CriteriaBuilder criteriaBuilder) {
        List<Expression<T>> list = new ArrayList<>();
        list.add(from.get(other));
        return compare.predicateExpression(from.get(this.name), criteriaBuilder,
                list);
    }
}
