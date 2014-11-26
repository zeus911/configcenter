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
package com.baidu.cc.configuration.service.impl;

import com.baidu.bjf.dao.SqlMapDao;
import com.baidu.bjf.service.GenericSqlMapServiceImpl;
import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.dao.ConfigGroupDao;
import com.baidu.cc.configuration.dao.ConfigItemDao;
import com.baidu.cc.configuration.service.ConfigGroupService;
import com.baidu.cc.configuration.service.ConfigItemService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * Service implement class for model :
 * com.baidu.cc.configuration.bo.ConfigGroupBase
 * 
 * @author BJF
 */
@Service("configGroupService")
public class ConfigGroupServiceImpl extends
        GenericSqlMapServiceImpl<ConfigGroup, Long> implements
        ConfigGroupService {
    /**
     * Dao 'configGroupDao' reference.
     */
    @Resource(name = "configGroupDao")
    private ConfigGroupDao configGroupDao;

    /** ConfigItemService. */
    @Resource
    private ConfigItemService configItemService;

    /** ConfigItemDao. */
    @Resource
    private ConfigItemDao configItemDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<ConfigGroup, Long> getDao() {
        return configGroupDao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.cc.configuration.service.ConfigGroupService#findByVersionId
     * (java.lang.Long)
     */
    @Override
    public List<ConfigGroup> findByVersionId(Long versionId) {
        return configGroupDao.findByVersionId(versionId);
    }

    /**
     * 根据版本ID查询所有的群组ID列表.
     * 
     * @param versionIds
     *            versionIds
     * @return List<String>
     */
    @Override
    public List<Long> findConfigGroupIdsByVersionIds(List<Long> versionIds) {
        if (CollectionUtils.isEmpty(versionIds)) {
            return null;
        }
        return configGroupDao.findConfigGroupIdsByVersionIds(versionIds);
    }

    /**
     * 根据传入的主键列表，删除config_group表，并级联删除相关的config_item表.
     * 
     * @param configGroupIds
     *            configGroupIds
     */
    @Override
    public void deleteConfigGroupCascadeByIds(List<Long> configGroupIds) {
        if (CollectionUtils.isNotEmpty(configGroupIds)) {
            List<Long> configItemIds = configItemService
                    .findConfigItemIdsByConfigGroupIds(configGroupIds);
            configItemDao.deleteBatch(configItemIds.toArray(new Long[] {}));
            configGroupDao.deleteBatch(configGroupIds.toArray(new Long[] {}));
        }
    }

    /**
     * 根据versionId查询配置组列表.
     * 
     * @param verIds
     *            the ver ids
     * @return the list
     */
    @Override
    public List<ConfigGroup> findByVersionIds(List<Long> verIds) {
        if (CollectionUtils.isEmpty(verIds)) {
            return new ArrayList<ConfigGroup>();
        }
        return configGroupDao.findByVersionIds(verIds);
    }
}