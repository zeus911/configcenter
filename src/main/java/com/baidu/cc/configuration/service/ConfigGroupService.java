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
import com.baidu.cc.configuration.bo.ConfigGroup;

import java.util.List;

/**
 * Service interface class for model
 * com.baidu.cc.configuration.bo.ConfigGroupBase
 * 
 * @author BJF
 */
public interface ConfigGroupService extends GenericService<ConfigGroup, Long> {

    /**
     * 根据versionId查询配置组列表.
     * 
     * @param versionId
     *            versionId
     * @return List<ConfigGroup>
     */
    public List<ConfigGroup> findByVersionId(Long versionId);

    /**
     * 根据版本ID查询所有的群组ID列表.
     * 
     * @param versionIds
     *            versionIds
     * @return List<String>
     */
    List<Long> findConfigGroupIdsByVersionIds(List<Long> versionIds);

    /**
     * 根据传入的主键列表，删除config_group表，并级联删除相关的config_item表.
     * 
     * @param configGroupIds
     *            configGroupIds
     */
    void deleteConfigGroupCascadeByIds(List<Long> configGroupIds);

    /**
     * 根据versionId查询配置组列表.
     * 
     * @param verIds
     *            verIds
     * @return List<ConfigGroup>
     */
    public List<ConfigGroup> findByVersionIds(List<Long> verIds);
}