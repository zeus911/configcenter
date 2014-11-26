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
package com.baidu.cc.configuration.service;

import com.baidu.bjf.service.GenericService;
import com.baidu.cc.configuration.bo.ConfigItem;

import java.util.List;

/**
 * Service interface class for model
 * com.baidu.cc.configuration.bo.ConfigItemBase
 * 
 * @author BJF
 */
public interface ConfigItemService extends GenericService<ConfigItem, Long> {

    /**
     * find {@link ConfigItem} list by related version id.
     * 
     * @param id
     *            version id
     * @param resolveReference
     *            if resolve reference value.
     * @return {@link ConfigItem} list
     */
    List<ConfigItem> findByVersionId(Long id, boolean resolveReference);

    /**
     * 根据groupId查询配置项列表.
     * 
     * @param groupId
     *            groupId
     * @param resolveReference
     *            是否查询引用
     * @return List<ConfigItem>
     */
    List<ConfigItem> findByGroupId(Long groupId, boolean resolveReference);

    /**
     * 保存变更，整合方法，含CRUD.
     * 
     * @param insertConfigItemList
     *            insert列表
     * @param updateConfigItemList
     *            update列表
     * @param delConfigItemIds
     *            删除ID列表
     * @return true/false
     */
    boolean saveConfigItems(List<ConfigItem> insertConfigItemList,
            List<ConfigItem> updateConfigItemList, List<Long> delConfigItemIds);

    /**
     * 根据configGroupIds查询所有相关的configItemIds.
     * 
     * @param configGroupIds
     *            configGroupIds
     * @return List<Long>
     */
    List<Long> findConfigItemIdsByConfigGroupIds(List<Long> configGroupIds);

    /**
     * find {@link ConfigItem} by version id and name.
     * 
     * @param versionId
     *            version id
     * @param name
     *            configuration item name to filter
     * @param resolveReference
     *            if resolve reference value.
     * @return {@link ConfigItem}
     */
    ConfigItem findByVersionIdAndName(Long versionId, String name,
            boolean resolveReference);

    /**
     * 将properties中的内容批量新增配置项.
     * 
     * @param groupId
     *            要新增到的groupId
     * @param versionId
     *            要新增到的versionId
     * @param content
     *            properties中的内容
     */
    public void batchInsertConfigItems(Long groupId, Long versionId,
            String content);
}