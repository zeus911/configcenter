/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baidu.cc.configuration.dao;

import com.baidu.bjf.dao.SqlMapDao;
import com.baidu.cc.configuration.bo.ConfigItem;

/**
 * Dao interface class for model com.baidu.cc.configuration.bo.ConfigItemBase
 * 
 * @author BJF
 */
public interface ConfigItemDao extends SqlMapDao<ConfigItem, Long> {

    /**
     * 删除某groupId下所有的配置项.
     * 
     * @param groupId
     *            groupId
     */
    void deleteByGroupId(Long groupId);
}