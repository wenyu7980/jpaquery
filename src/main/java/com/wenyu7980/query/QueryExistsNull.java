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
 * 存在表达式
 * @author:wenyu
 * @date:2019/10/22
 */
public class QueryExistsNull<T> extends QueryExists<T> {
    private QueryExistsNull(Class<T> clazz, QueryExistPredicate joinPredicate, QueryPredicateExpress express) {
        super(clazz, joinPredicate, express);
    }

    public static <T> QueryExistsNull exists(Class<T> clazz, QueryExistPredicate joinPredicate,
      QueryPredicateExpress express) {
        return new QueryExistsNull(clazz, joinPredicate, express);
    }

    @Override
    public boolean nonNull() {
        return true;
    }

}
