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
public class QueryExists implements QueryPredicateExpress {
    private String name;
    private String subName;
    private QueryPredicateExpress express;
    private Class<?> clazz;

    public QueryExists(String name, String subName, Class<?> clazz,
            QueryPredicateExpress express) {
        this.name = name;
        this.subName = subName;
        this.express = express;
        this.clazz = clazz;
    }

    public static QueryExists exists(String name, String subName,
            Class<?> clazz, QueryPredicateExpress express) {
        return new QueryExists(name, subName, clazz, express);
    }

    @Override
    public Predicate predicate(From<?, ?> from,
            CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<?> criteria = criteriaBuilder.createQuery(clazz);
        Subquery<?> subquery = criteria.subquery(clazz);
        Root<?> subRoot = subquery.from(clazz);
        subquery.select(subRoot.get(subName));
        subquery.where(criteriaBuilder
                .and(express.predicate(subRoot, criteriaBuilder),
                        criteriaBuilder
                                .equal(from.get(name), subRoot.get(subName))));
        return criteriaBuilder.exists(subquery);
    }

    @Override
    public boolean nonNull() {
        return this.express.nonNull();
    }

    @Override
    public boolean merge(QueryPredicateExpress express,
            QueryPredicateExpress parent) {
        if (express instanceof QueryExists) {
            QueryExists exists = (QueryExists) express;
            if (Objects.equals(exists.name, this.name) && Objects
                    .equals(exists.subName, this.subName) && Objects
                    .equals(exists.clazz, exists.clazz)) {
                this.express = parent.logic(this.express, exists.express);
                return true;
            }
        }
        return false;
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
        return Objects.equals(name, exists.name) && Objects
                .equals(subName, exists.subName) && Objects
                .equals(express, exists.express) && Objects
                .equals(clazz, exists.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subName, express, clazz);
    }
}
