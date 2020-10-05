package com.wenyu7980.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface QueryExistPredicate {
    Predicate apply(From<?, ?> from, From<?, ?> subFrom,
            CriteriaBuilder builder);
}
