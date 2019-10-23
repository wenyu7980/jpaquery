package com.wenyu7980;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 * 逻辑表达式
 * @author:wenyu
 * @date:2019/10/22
 */
public class QueryLogic implements QueryLogicExpress {
    protected enum Logic {
        AND() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder,
                    Predicate... predicates) {
                return criteriaBuilder.and(predicates);
            }
        },
        OR() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder,
                    Predicate... predicates) {
                return criteriaBuilder.or(predicates);
            }
        },
        NOT() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder,
                    Predicate... predicates) {
                return predicates[0].not();
            }
        };

        /**
         * predicate
         * @param criteriaBuilder
         * @param predicates
         * @return
         */
        public abstract Predicate predicate(
                final CriteriaBuilder criteriaBuilder, Predicate... predicates);
    }

    /** 逻辑符号 */
    private Logic logic;
    /** 逻辑列表 */
    private List<QueryLogicExpress> expresses = new ArrayList<>();

    private QueryLogic(Logic logic, QueryLogicExpress... express) {
        this.logic = logic;
        this.expresses.addAll(Arrays.asList(express));
    }

    /**
     * 与
     * @param expresses
     * @return
     */
    public static QueryLogic and(QueryLogicExpress... expresses) {
        return new QueryLogic(Logic.AND, expresses);
    }

    /**
     * 或
     * @param expresses
     * @return
     */
    public static QueryLogic or(QueryLogicExpress... expresses) {
        return new QueryLogic(Logic.OR, expresses);
    }

    /**
     * 非
     * @param express
     * @return
     */
    public static QueryLogic not(QueryLogicExpress express) {
        return new QueryLogic(Logic.NOT, express);
    }

    @Override
    public Predicate predicate(final From<?, ?> from,
            final CriteriaBuilder criteriaBuilder) {
        if (!this.nonNull()) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        final List<QueryLogicExpress> expressList = this.expresses.stream()
                .filter(QueryLogicExpress::nonNull)
                .collect(Collectors.toList());
        Predicate[] predicates = new Predicate[expressList.size()];
        for (int i = 0; i < expressList.size(); i++) {
            predicates[i] = expressList.get(i).predicate(from, criteriaBuilder);
        }
        return this.logic.predicate(criteriaBuilder, predicates);
    }

    @Override
    public boolean nonNull() {
        for (QueryLogicExpress express : this.expresses) {
            if (express.nonNull()) {
                return true;
            }
        }
        return false;
    }
}
