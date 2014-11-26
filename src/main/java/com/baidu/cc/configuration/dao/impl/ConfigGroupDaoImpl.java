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
import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.dao.ConfigGroupDao;
import com.baidu.cc.configuration.dao.ConfigGroupMapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Dao implement class for model : com.baidu.cc.configuration.bo.ConfigGroupBase
 * 
 * @author BJF
 */
@Service("configGroupDao")
public class ConfigGroupDaoImpl extends
        ConfigurableBaseSqlMapDao<ConfigGroup, Long> implements ConfigGroupDao {
    /**
     * DaoMapper 'configGroupMapper' reference.
     */
    @Autowired
    private ConfigGroupMapper configGroupMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.dao.ConfigurableBaseSqlMapDao#getDaoMapper()
     */
    @Override
    public DaoMapper<ConfigGroup, Long> getDaoMapper() {
        return configGroupMapper;
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.cc.configuration.dao.ConfigGroupDao#findByVersionId(java.lang
     * .Long)
     */
    @Override
    public List<ConfigGroup> findByVersionId(Long versionId) {
        return configGroupMapper.findByVersionId(versionId);
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
        return configGroupMapper.findConfigGroupIdsByVersionIds(versionIds);
    }

    /**
     * 根据versionId列表查询配置组列表.
     * 
     * @param verIds
     *            the ver ids
     * @return the list
     */
    @Override
    public List<ConfigGroup> findByVersionIds(List<Long> verIds) {
        return configGroupMapper.findByVersionIds(verIds);
    }
}