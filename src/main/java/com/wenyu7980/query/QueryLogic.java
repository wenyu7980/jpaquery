package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.*;
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
public class QueryLogic implements QueryPredicateExpress {
    /** 逻辑符号 */
    private final Logic logic;
    /** 逻辑列表 */
    private final List<QueryPredicateExpress> expresses = new ArrayList<>();

    /**
     * 与
     * @param expresses
     * @return
     */
    public static QueryLogic and(QueryPredicateExpress... expresses) {
        return new QueryLogic(Logic.AND, expresses);
    }

    public static QueryLogic and(Collection<QueryPredicateExpress> expresses) {
        return new QueryLogic(Logic.AND, expresses);
    }

    /**
     * 或
     * @param expresses
     * @return
     */
    public static QueryLogic or(QueryPredicateExpress... expresses) {
        return new QueryLogic(Logic.OR, expresses);
    }

    public static QueryLogic or(Collection<QueryPredicateExpress> expresses) {
        return new QueryLogic(Logic.OR, expresses);
    }

    /**
     * 非
     * @param express
     * @return
     */
    public static QueryLogic not(QueryPredicateExpress express) {
        return new QueryLogic(Logic.NOT, express);
    }

    /**
     * 异或
     * @param left
     * @param right
     * @return
     */
    public static QueryLogic xor(QueryPredicateExpress left, QueryPredicateExpress right) {
        return new QueryLogic(Logic.XOR, left, right);
    }

    protected QueryLogic(Logic logic, Collection<QueryPredicateExpress> expresses) {
        this.logic = logic;
        Set<QueryPredicateExpress> set = expandExpresses(expresses);
        out:
        for (final QueryPredicateExpress e : set) {
            for (QueryPredicateExpress express : this.expresses) {
                if (express instanceof QueryPredicateMergeExpress && e instanceof QueryPredicateMergeExpress) {
                    QueryPredicateMergeExpress e1 = (QueryPredicateMergeExpress) express;
                    QueryPredicateMergeExpress e2 = (QueryPredicateMergeExpress) e;
                    if (e1.merge(e)) {
                        e1.setExpress(this.logic(e1.getExpress(), e2.getExpress()));
                    }
                    continue out;
                }
            }
            this.expresses.add(e);
        }
    }

    private Set<QueryPredicateExpress> expandExpresses(Collection<QueryPredicateExpress> expresses) {
        Set<QueryPredicateExpress> set = new HashSet<>();
        for (QueryPredicateExpress express : expresses) {
            if (express instanceof QueryLogic) {
                QueryLogic logic = (QueryLogic) express;
                if (logic.logic == this.logic) {
                    set.addAll(logic.expresses);
                    continue;
                }
            }
            set.add(express);
        }
        return set;
    }

    protected QueryLogic(Logic logic, QueryPredicateExpress... expresses) {
        this(logic, Arrays.asList(expresses));
    }

    @Override
    public Predicate predicate(final From<?, ?> from, final CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>(this.expresses.size());
        for (QueryPredicateExpress express : expresses) {
            if (express.nonNull()) {
                predicates.add(express.predicate(from, criteriaBuilder));
            }
        }
        return this.logic.predicate(criteriaBuilder, predicates.toArray(new Predicate[predicates.size()]));
    }

    @Override
    public boolean nonNull() {
        for (QueryPredicateExpress express : this.expresses) {
            if (express.nonNull()) {
                return true;
            }
        }
        return false;
    }

    private QueryPredicateExpress logic(QueryPredicateExpress e1, QueryPredicateExpress e2) {
        assert this.logic != Logic.XOR && this.logic != Logic.NOT;
        return new QueryLogic(this.logic, e1, e2);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        QueryLogic logic1 = (QueryLogic) object;
        if (logic != logic1.logic || this.expresses.size() != logic1.expresses.size()) {
            return false;
        }
        return this.expresses.containsAll(logic1.expresses) && logic1.expresses.containsAll(this.expresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logic, expresses);
    }

    protected enum Logic {
        /** 与 */
        AND() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return criteriaBuilder.and(predicates);
            }
        },
        /** 或 */
        OR() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return criteriaBuilder.or(predicates);
            }
        },
        /** 非 */
        NOT() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return predicates[0].not();
            }
        },
        // 异或
        XOR() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return criteriaBuilder.or(criteriaBuilder.and(predicates[0], predicates[1].not()),
                  criteriaBuilder.and(predicates[0].not(), predicates[1]));
            }
        };

        /**
         * 判定表达式转换为JPA的判定表单式
         * @param criteriaBuilder
         * @param predicates
         * @return
         */
        public abstract Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates);
    }
}
