package com.wenyu7980;

import javax.persistence.criteria.*;
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
 *
 * @author:wenyu
 * @date:2019/10/22
 */
public class QueryExists implements QueryLogicExpress {
    private String name;
    private String subName;
    private QueryLogicExpress express;
    private Class<?> clazz;

    public QueryExists(String name, String subName, Class<?> clazz,
            QueryLogicExpress express) {
        this.name = name;
        this.subName = subName;
        this.express = express;
        this.clazz = clazz;
    }

    public static QueryExists exists(String name, String subName,
            Class<?> clazz, QueryLogicExpress express) {
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
}