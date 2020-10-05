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
 * ManyToMany
 * @author:wenyu
 * @date:2019/10/22
 */
public class QueryExistsManyNull implements QueryPredicateExpress {
    private String id;
    private String subId;
    private String subJoin;
    private QueryPredicateExpress express;
    private Class<?> clazz;

    private QueryExistsManyNull(String id, String subId, String subJoin,
            Class<?> clazz, QueryPredicateExpress express) {
        this.id = id;
        this.subId = subId;
        this.subJoin = subJoin;
        this.express = express;
        this.clazz = clazz;
    }

    public static QueryExistsManyNull exists(String id, String subId,
            String subJoin, Class<?> clazz, QueryPredicateExpress express) {
        return new QueryExistsManyNull(id, subId, subJoin, clazz, express);
    }

    @Override
    public Predicate predicate(From<?, ?> from,
            CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<?> criteria = criteriaBuilder.createQuery(clazz);
        Subquery<?> subquery = criteria.subquery(clazz);
        Root<?> subRoot = subquery.from(clazz);
        subquery.select(subRoot.get(subId));
        subquery.where(criteriaBuilder
                .and(express.predicate(subRoot, criteriaBuilder),
                        criteriaBuilder.equal(from.get(this.id),
                                subRoot.join(this.subJoin).get(this.id))));
        return criteriaBuilder.exists(subquery);
    }

    @Override
    public boolean merge(QueryPredicateExpress express,
            QueryPredicateExpress parent) {
        if (express instanceof QueryExistsManyNull) {
            QueryExistsManyNull exists = (QueryExistsManyNull) express;
            if (Objects.equals(this.clazz, exists.clazz)) {
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
        QueryExistsManyNull exists = (QueryExistsManyNull) object;
        return Objects.equals(id, exists.id) && Objects
                .equals(subId, exists.subId) && Objects
                .equals(subJoin, exists.subJoin) && Objects
                .equals(express, exists.express) && Objects
                .equals(clazz, exists.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subId, subJoin, express, clazz);
    }
}
