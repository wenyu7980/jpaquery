package com.wenyu7980.query;

import javax.persistence.criteria.*;
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
 * 存在表达式
 * @author:wenyu
 * @date:2019/10/22
 */
public final class QueryExists<T> implements QueryPredicateExpress, QueryPredicateMergeExpress {
    private final boolean condition;
    private final QueryExistPredicate joinPredicate;
    private final QueryPredicateExpress express;
    private final Class<T> clazz;

    protected QueryExists(boolean condition, Class<T> clazz, QueryExistPredicate joinPredicate,
      QueryPredicateExpress express) {
        this.condition = condition;
        this.joinPredicate = joinPredicate;
        this.express = express;
        this.clazz = clazz;
    }

    public static <T> QueryExists exists(Class<T> clazz, QueryExistPredicate joinPredicate,
      QueryPredicateExpress express) {
        return exists(express.nonNull(), clazz, joinPredicate, express);
    }

    public static <T> QueryExists exists(boolean condition, Class<T> clazz, QueryExistPredicate joinPredicate,
      QueryPredicateExpress express) {
        return new QueryExists(condition, clazz, joinPredicate, express);
    }

    public static <T> QueryExists existsNull(boolean condition, Class<T> clazz, QueryExistPredicate joinPredicate,
      QueryPredicateExpress express) {
        return new QueryExists(condition, clazz, joinPredicate, express);
    }

    public static <T> QueryExists existsNull(Class<T> clazz, QueryExistPredicate joinPredicate,
      QueryPredicateExpress express) {
        return existsNull(true, clazz, joinPredicate, express);
    }

    @Override
    public Predicate predicate(From<?, ?> from, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<?> criteria = criteriaBuilder.createQuery(clazz);
        Subquery<T> subquery = criteria.subquery(clazz);
        Root<T> subRoot = subquery.from(clazz);
        subquery.select(subRoot);
        if (condition && express.nonNull()) {
            subquery.where(criteriaBuilder.and(express.predicate(subRoot, criteriaBuilder),
              this.joinPredicate.apply(from, subRoot, criteriaBuilder)));
        } else {
            subquery.where(this.joinPredicate.apply(from, subRoot, criteriaBuilder));
        }
        return criteriaBuilder.exists(subquery);
    }

    @Override
    public boolean nonNull() {
        return this.condition;
    }

    @Override
    public boolean merge(QueryPredicateExpress express) {
        if (express instanceof QueryExists) {
            QueryExists exists = (QueryExists) express;
            if (Objects.equals(this.clazz, exists.clazz) && Objects.equals(this.joinPredicate, exists.joinPredicate)) {
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
        return exists(this.clazz, this.joinPredicate, express);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        QueryExists exists = (QueryExists) object;
        return Objects.equals(joinPredicate, exists.joinPredicate) && Objects.equals(express, exists.express) && Objects
          .equals(clazz, exists.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(joinPredicate, express, clazz);
    }
}
