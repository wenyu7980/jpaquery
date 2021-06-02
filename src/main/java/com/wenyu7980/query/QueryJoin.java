package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
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
 * 表连接表达式
 * @author:wenyu
 * @date:2019/10/22
 */
public final class QueryJoin implements QueryPredicateExpress, QueryPredicateMergeExpress {

    private final String name;
    private final QueryPredicateExpress express;

    private QueryJoin(String name, QueryPredicateExpress express) {
        this.name = name;
        this.express = express;
    }

    /**
     * 表连接
     * @param name
     * @param express
     * @return
     */
    public static QueryJoin join(String name, QueryPredicateExpress express) {
        return new QueryJoin(name, express);
    }

    @Override
    public Predicate predicate(From<?, ?> from, CriteriaBuilder criteriaBuilder) {
        return express.predicate(from.join(name), criteriaBuilder);
    }

    @Override
    public boolean nonNull() {
        return this.express.nonNull();
    }

    @Override
    public boolean merge(QueryPredicateExpress express) {
        if (express instanceof QueryJoin) {
            QueryJoin join = (QueryJoin) express;
            if (Objects.equals(join.name, this.name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public QueryPredicateExpress getExpress() {
        return this.express;
    }

    @Override
    public QueryPredicateExpress clone(QueryPredicateExpress express) {
        return join(this.name, express);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryJoin join = (QueryJoin) o;
        return Objects.equals(name, join.name) && Objects.equals(express, join.express);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, express);
    }
}
