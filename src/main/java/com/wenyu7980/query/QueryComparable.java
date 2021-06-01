package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;

public interface QueryComparable {

    /**
     * 判定表达式转换为JPA的判定表单式
     * @param expression
     * @param criteriaBuilder
     * @param values
     * @return
     */
    <T extends Comparable<T>> Predicate predicate(final Expression<T> expression, final CriteriaBuilder criteriaBuilder,
      List<T> values);

    /**
     * 判定表达式转换为JPA的判定表单式 空
     * @param expression
     * @param criteriaBuilder
     * @return
     */
    <T extends Comparable<T>> Predicate predicateExpression(final Expression<T> expression,
      final CriteriaBuilder criteriaBuilder, List<Expression<T>> values);

    /**
     * 比较对象是否可以为空
     * @return
     */
    default boolean nullable() {
        return false;
    }

    /**
     * 是否剔除比较对象中的null
     * @return
     */
    default boolean removeNull() {
        return true;
    }
}
