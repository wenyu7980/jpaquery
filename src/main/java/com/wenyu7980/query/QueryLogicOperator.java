package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public interface QueryLogicOperator {

    /**
     * 判定表达式转换为JPA的判定表单式
     * @param criteriaBuilder
     * @param predicates
     * @return
     */
    Predicate predicate(final CriteriaBuilder criteriaBuilder, Predicate... predicates);

    /**
     * 是否可以展开
     * @return
     */
    default boolean expand() {
        return false;
    }
}
