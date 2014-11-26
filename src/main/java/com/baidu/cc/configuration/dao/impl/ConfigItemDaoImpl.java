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
package com.baidu.cc.configuration.dao.impl;

import com.baidu.bjf.dao.ConfigurableBaseSqlMapDao;
import com.baidu.bjf.dao.DaoMapper;
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.dao.ConfigItemDao;
import com.baidu.cc.configuration.dao.ConfigItemMapper;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Dao implement class for model : com.baidu.cc.configuration.bo.ConfigItemBase
 * 
 * @author BJF
 */
@Service("configItemDao")
public class ConfigItemDaoImpl extends
        ConfigurableBaseSqlMapDao<ConfigItem, Long> implements ConfigItemDao {
    /**
     * DaoMapper 'configItemMapper' reference.
     */
    @Autowired
    private ConfigItemMapper configItemMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.dao.ConfigurableBaseSqlMapDao#getDaoMapper()
     */
    @Override
    public DaoMapper<ConfigItem, Long> getDaoMapper() {
        return configItemMapper;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.bjf.dao.SqlMapDaoSupport#setSqlSessionFactory(org.apache.ibatis
     * .session.SqlSessionFactory)
     */
    @Resource(name = "sqlSessionFactory")
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        setSqlSessionFactoryInternal(sqlSessionFactory);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.bjf.dao.SqlMapDaoSupport#setSqlSessionTemplate(org.mybatis.
     * spring.SqlSessionTemplate)
     */
    @Resource(name = "sqlSessionTemplate")
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        setSqlSessionTemplateInternal(sqlSessionTemplate);
    }

    /**
     * 删除某groupId下所有的配置项.
     * 
     * @param groupId
     *            groupId
     */
    @Override
    public void deleteByGroupId(Long groupId) {
        configItemMapper.deleteByGroupId(groupId);
    }
}