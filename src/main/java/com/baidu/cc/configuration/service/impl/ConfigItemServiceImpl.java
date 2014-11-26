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
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.dao.ConfigItemDao;
import com.baidu.cc.configuration.dao.ConfigItemMapper;
import com.baidu.cc.configuration.service.ConfigItemService;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implement class for model :
 * com.baidu.cc.configuration.bo.ConfigItemBase
 * 
 * @author BJF
 */
@Service("configItemService")
public class ConfigItemServiceImpl extends
        GenericSqlMapServiceImpl<ConfigItem, Long> implements ConfigItemService {
    /**
     * Dao 'configItemDao' reference.
     */
    @Resource(name = "configItemDao")
    private ConfigItemDao configItemDao;

    /** configItemMapper. */
    @Autowired
    private ConfigItemMapper configItemMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<ConfigItem, Long> getDao() {
        return configItemDao;
    }

    /**
     * find {@link ConfigItem} list by related version id.
     * 
     * @param id
     *            version id
     * @param resolveReference
     *            if resolve reference value.
     * @return {@link ConfigItem} list
     */
    public List<ConfigItem> findByVersionId(Long id, boolean resolveReference) {
        List<ConfigItem> ret;
        ret = configItemMapper.findByVersionId(id);

        if (!resolveReference) {
            return ret;
        }

        for (ConfigItem configItem : ret) {
            if (configItem.isRef()) {
                ConfigItem refItem = configItemDao.findById(NumberUtils
                        .toLong(configItem.getVal()));
                if (refItem != null) {
                    configItem.setVal(refItem.getVal());
                    configItem.setResolved(true);
                }
            }
        }

        return ret;
    }

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
    public ConfigItem findByVersionIdAndName(Long versionId, String name,
            boolean resolveReference) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("versionId", versionId);
        param.put("name", name);
        ConfigItem ret = configItemMapper.findByVersionIdAndName(param);

        if (ret == null) {
            return null;
        }

        if (!resolveReference) {
            return ret;
        }

        if (ret.isRef()) {
            ConfigItem refItem = configItemDao.findById(NumberUtils.toLong(ret
                    .getVal()));
            if (refItem != null) {
                ret.setVal(refItem.getVal());
                ret.setResolved(true);
            }
        }
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.cc.configuration.service.ConfigItemService#findByGroupId(java
     * .lang.Long, boolean)
     */
    @Override
    public List<ConfigItem> findByGroupId(Long groupId, boolean resolveReference) {
        return configItemMapper.findByGroupId(groupId);
    }

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
    @Override
    public boolean saveConfigItems(List<ConfigItem> insertConfigItemList,
            List<ConfigItem> updateConfigItemList, List<Long> delConfigItemIds) {
        if (CollectionUtils.isNotEmpty(delConfigItemIds)) {
            configItemDao.deleteBatch(delConfigItemIds.toArray(new Long[] {}));
        }
        if (CollectionUtils.isNotEmpty(insertConfigItemList)) {
            super.saveOrUpdateAll(insertConfigItemList);
        }
        if (CollectionUtils.isNotEmpty(updateConfigItemList)) {
            super.saveOrUpdateAll(updateConfigItemList);
        }

        return true;
    }

    /**
     * 根据configGroupIds查询所有相关的configItemIds.
     * 
     * @param configGroupIds
     *            configGroupIds
     * @return List<Long>
     */
    @Override
    public List<Long> findConfigItemIdsByConfigGroupIds(
            List<Long> configGroupIds) {
        if (CollectionUtils.isEmpty(configGroupIds)) {
            return null;
        }
        List<Long> configItemIdsByConfigGroupIds = configItemMapper
                .findConfigItemIdsByConfigGroupIds(configGroupIds);
        return configItemIdsByConfigGroupIds;
    }

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
    @Override
    public void batchInsertConfigItems(Long groupId, Long versionId,
            String content) {

        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        Properties properties = new Properties();

        try {
            propertiesConfiguration.load(new StringReader(content));
            properties.load(new StringReader(content));

            Date now = new Date();
            Iterator<String> keys = propertiesConfiguration.getKeys();
            List<ConfigItem> configItems = new ArrayList<ConfigItem>();

            while (keys.hasNext()) {
                String key = keys.next();
                String value = properties.getProperty(key);

                ConfigItem configItem = new ConfigItem();
                configItem.setName(key);
                configItem.setVal(value);
                configItem.setCreateTime(now);
                configItem.setUpdateTime(now);
                configItem.setGroupId(groupId);
                configItem.setVersionId(versionId);
                configItem.setShareable(false);
                configItem.setRef(false);
                configItems.add(configItem);
            }

            configItemDao.deleteByGroupId(groupId);
            saveOrUpdateAll(configItems);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
