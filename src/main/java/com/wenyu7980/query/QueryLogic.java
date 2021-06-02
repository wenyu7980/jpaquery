package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
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
public final class QueryLogic implements QueryPredicateExpress {
    /** 逻辑符号 */
    private final QueryLogicOperator logic;
    /** 逻辑列表 */
    private final List<QueryPredicateExpress> expresses;
    private final boolean nonNull;

    public static QueryLogic of(@NotNull QueryLogicOperator logic, QueryPredicateExpress... expresses) {
        assert logic != null;
        return new QueryLogic(logic, Arrays.asList(expresses));
    }

    public static QueryLogic of(@NotNull QueryLogicOperator logic, Collection<QueryPredicateExpress> expresses) {
        assert logic != null;
        return new QueryLogic(logic, expresses);
    }

    /**
     * 与
     * @param expresses
     * @return
     */
    public static QueryLogic and(QueryPredicateExpress... expresses) {
        return of(Logic.AND, expresses);
    }

    public static QueryLogic and(Collection<QueryPredicateExpress> expresses) {
        return of(Logic.AND, expresses);
    }

    /**
     * 或
     * @param expresses
     * @return
     */
    public static QueryLogic or(QueryPredicateExpress... expresses) {
        return of(Logic.OR, expresses);
    }

    public static QueryLogic or(Collection<QueryPredicateExpress> expresses) {
        return of(Logic.OR, expresses);
    }

    /**
     * 非
     * @param express
     * @return
     */
    public static QueryLogic not(QueryPredicateExpress express) {
        return of(Logic.NOT, express);
    }

    /**
     * 异或
     * @param left
     * @param right
     * @return
     */
    public static QueryLogic xor(QueryPredicateExpress left, QueryPredicateExpress right) {
        return of(Logic.XOR, left, right);
    }

    private QueryLogic(QueryLogicOperator logic, Collection<QueryPredicateExpress> expresses) {
        Set<QueryPredicateExpress> set = expandExpresses(logic, expresses);
        this.expresses = new ArrayList<>();
        out:
        for (final QueryPredicateExpress e : set) {
            if (!e.nonNull()) {
                continue;
            }
            for (int i = 0; i < this.expresses.size(); i++) {
                QueryPredicateExpress express = this.expresses.get(i);
                if (express instanceof QueryPredicateMergeExpress && e instanceof QueryPredicateMergeExpress) {
                    QueryPredicateMergeExpress e1 = (QueryPredicateMergeExpress) express;
                    QueryPredicateMergeExpress e2 = (QueryPredicateMergeExpress) e;
                    if (e1.merge(e)) {
                        this.expresses.set(i, e1.clone(of(logic, e1.getExpress(), e2.getExpress())));
                        continue out;
                    }
                }
            }
            this.expresses.add(e);
        }
        this.logic = logic;
        this.nonNull = this.expresses.size() > 0;
    }

    private static Set<QueryPredicateExpress> expandExpresses(QueryLogicOperator logic,
      Collection<QueryPredicateExpress> expresses) {
        Set<QueryPredicateExpress> set = new LinkedHashSet<>();
        for (QueryPredicateExpress express : expresses) {
            if (logic.expand() && express instanceof QueryLogic) {
                QueryLogic queryLogic = (QueryLogic) express;
                if (Objects.equals(queryLogic.logic, logic)) {
                    set.addAll(expandExpresses(logic, queryLogic.expresses));
                    continue;
                }
            }
            set.add(express);
        }
        return set;
    }

    @Override
    public Predicate predicate(final From<?, ?> from, final CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>(this.expresses.size());
        for (QueryPredicateExpress express : expresses) {
            predicates.add(express.predicate(from, criteriaBuilder));
        }
        return this.logic.predicate(criteriaBuilder, predicates.toArray(new Predicate[predicates.size()]));
    }

    @Override
    public boolean nonNull() {
        return this.nonNull;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryLogic that = (QueryLogic) o;
        return nonNull == that.nonNull && Objects.equals(logic, that.logic) && Objects
          .equals(expresses, that.expresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nonNull, logic, expresses);
    }

    protected enum Logic implements QueryLogicOperator {
        /** 与 */
        AND() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return criteriaBuilder.and(predicates);
            }

            @Override
            public boolean expand() {
                return true;
            }
        },
        /** 或 */
        OR() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return criteriaBuilder.or(predicates);
            }

            @Override
            public boolean expand() {
                return true;
            }
        },
        /** 非 */
        NOT() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return predicates[0].not();
            }

            @Override
            public boolean expand() {
                return false;
            }
        },
        // 异或
        XOR() {
            @Override
            public Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates) {
                return criteriaBuilder.or(criteriaBuilder.and(predicates[0], predicates[1].not()),
                  criteriaBuilder.and(predicates[0].not(), predicates[1]));
            }

            @Override
            public boolean expand() {
                return false;
            }
        };

    }
}
