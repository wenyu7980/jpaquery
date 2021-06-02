package com.wenyu7980.query;

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
 * 可合并
 * @author:wenyu
 * @date:2021/5/31
 */
public interface QueryPredicateMergeExpress {

    /**
     * 是否可以合并
     * @param express
     * @return true 可合并
     *         false 不可合并
     */
    boolean merge(QueryPredicateExpress express);

    /**
     * 合并用Expresses
     * @return
     */
    QueryPredicateExpress getExpress();

    /**
     * 设置Expresses
     * @param express
     */
    QueryPredicateExpress clone(QueryPredicateExpress express);
}
