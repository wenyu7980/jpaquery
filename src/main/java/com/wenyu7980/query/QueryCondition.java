package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
 * 条件表单式 非空生效
 * @author:wenyu
 * @date:2019/10/22
 */
public class QueryCondition<T extends Comparable<T>>
        implements QueryPredicateExpress {

    private String name;
    private QueryCompare compare;
    private List<T> values;

    private QueryCondition(String name, QueryCompare compare, List<T> values) {
        this.name = name;
        this.compare = compare;
        this.values = values.stream().filter(value -> Objects.nonNull(value))
                .collect(Collectors.toList());
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> of(String name,
            QueryCompare compare, List<T> values) {
        return new QueryCondition<>(name, compare, values);
    }

    /**
     * 查询条件
     * @param name
     * @param compare
     * @param values
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> QueryCondition<T> of(String name,
            QueryCompare compare, T... values) {
        return new QueryCondition<>(name, compare, Arrays.asList(values));
    }

    @Override
    public Predicate predicate(final From<?, ?> from,
            final CriteriaBuilder criteriaBuilder) {
        return compare
                .predicate(from.get(this.name), criteriaBuilder, this.values);

    }

    @Override
    public boolean nonNull() {
        return this.values != null && this.values.size() > 0;
    }
}
