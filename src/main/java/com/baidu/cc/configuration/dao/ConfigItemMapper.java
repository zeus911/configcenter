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

import com.baidu.bjf.dao.DaoMapper;
import com.baidu.bjf.orm.mybatis.SqlMapper;
import com.baidu.cc.configuration.bo.ConfigItem;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Mapper interface class for model com.baidu.cc.configuration.bo.ConfigItemBase
 * 
 * @author BJF
 */
@SqlMapper
public interface ConfigItemMapper extends DaoMapper<ConfigItem, Long> {

    /**
     * find {@link ConfigItem} list by related version id.
     * 
     * @param id
     *            version id
     * @return {@link ConfigItem} list
     */
    List<ConfigItem> findByVersionId(Long id);

    /**
     * 根据groupId查询配置项列表.
     * 
     * @param groupId
     *            groupId
     * @return List<ConfigItem>
     */
    List<ConfigItem> findByGroupId(Long groupId);

    /**
     * 根据configGroupIds查询所有相关的configItemIds.
     * 
     * @param configGroupIds
     *            configGroupIds
     * @return List<Long>
     */
    List<Long> findConfigItemIdsByConfigGroupIds(
            @Param("configGroupIds") List<Long> configGroupIds);

    /**
     * 根据版本id和配置名称进行查询.
     * 
     * @param param
     *            versionId和name
     * @return {@link ConfigItem}
     */
    ConfigItem findByVersionIdAndName(Map<String, Object> param);

    /**
     * 删除某groupId下所有的configItems.
     * 
     * @param groupId
     *            groupId
     */
    void deleteByGroupId(@Param("groupId") Long groupId);
}