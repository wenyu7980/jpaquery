package com.wenyu7980;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
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
 * 判定表单式
 * @author:wenyu
 * @date:2019/10/22
 */
public interface QueryPredicateExpress {
    /**
     * 判定表达式转换为JPA的判定表单式
     * @param from
     * @param criteriaBuilder
     * @return
     */
    Predicate predicate(final From<?, ?> from,
            final CriteriaBuilder criteriaBuilder);

    /**
     * 判定表达式的比较值是否为非空
     * @return
     */
    default boolean nonNull() {
        return true;
    }
}
