package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;
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
public enum QueryCompare implements QueryComparable {
    /** 等于 */
    EQ() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.equal(expression, values.get(0));
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            return criteriaBuilder.equal(expression, values.get(0));
        }
    },
    /** 小于 */
    LT() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.lessThan(expression, values.get(0));
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            return criteriaBuilder.lessThan(expression, values.get(0));
        }
    },
    /** 不等于 */
    NE() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.notEqual(expression, values.get(0));
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            return criteriaBuilder.notEqual(expression, values.get(0));
        }
    },
    /** 小于等于 */
    LE() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.lessThanOrEqualTo(expression, values.get(0));
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            return criteriaBuilder.lessThanOrEqualTo(expression, values.get(0));
        }
    },
    /** 大于 */
    GT() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.greaterThan(expression, values.get(0));
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            return criteriaBuilder.greaterThan(expression, values.get(0));
        }
    },
    /** 大于等于 */
    GE() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.greaterThanOrEqualTo(expression, values.get(0));
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            return criteriaBuilder.greaterThanOrEqualTo(expression, values.get(0));
        }
    },
    /** 模糊查询 */
    LIKE() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.like((Expression<String>) expression, "%" + values.get(0) + "%");
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            throw new RuntimeException("Express Not Support");
        }
    },
    /** 包含 */
    IN() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return expression.in(values);
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            throw new RuntimeException("Express Not Support IN");
        }

        @Override
        public boolean removeNull() {
            return false;
        }
    },
    /** 空 */
    ISNULL() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.isNull(expression);
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            throw new RuntimeException("Express Not Support ISNULL");
        }

        @Override
        public boolean nullable() {
            return true;
        }
    },
    /** 非空 */
    NOTNULL() {
        @Override
        public <T extends Comparable<T>> Predicate predicate(Expression<T> expression, CriteriaBuilder criteriaBuilder,
          List<T> values) {
            return criteriaBuilder.isNotNull(expression);
        }

        @Override
        public <T extends Comparable<T>> Predicate predicateExpression(Expression<T> expression,
          CriteriaBuilder criteriaBuilder, List<Expression<T>> values) {
            throw new RuntimeException("Express Not Support NOTNULL");
        }

        @Override
        public boolean nullable() {
            return true;
        }
    };
}
