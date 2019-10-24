package com.wenyu7980;

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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

/**
 * 逻辑表达式-异或
 * @author:wenyu
 * @date:2019/10/22
 */
public class QueryLogicXor implements QueryPredicateExpress {
    /** 左 */
    private QueryPredicateExpress left;
    /** 右 */
    private QueryPredicateExpress right;

    private QueryLogicXor(QueryPredicateExpress left,
            QueryPredicateExpress right) {
        this.left = left;
        this.right = right;
    }

    public static QueryLogicXor xor(QueryPredicateExpress left,
            QueryPredicateExpress right) {
        return new QueryLogicXor(left, right);
    }

    @Override
    public Predicate predicate(From<?, ?> from,
            CriteriaBuilder criteriaBuilder) {
        Predicate left = this.left.predicate(from, criteriaBuilder);
        Predicate right = this.right.predicate(from, criteriaBuilder);
        return criteriaBuilder.or(criteriaBuilder.and(left, right.not()),
                criteriaBuilder.and(left.not(), right));
    }

    @Override
    public boolean nonNull() {
        // 两个表达式同时为非空时，才为非空
        return left.nonNull() && right.nonNull();
    }
}
