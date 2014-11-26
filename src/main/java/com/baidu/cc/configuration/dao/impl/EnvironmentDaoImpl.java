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
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.dao.EnvironmentDao;
import com.baidu.cc.configuration.dao.EnvironmentMapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Dao implement class for model : com.baidu.cc.configuration.bo.EnvironmentBase
 * 
 * @author BJF
 */
@Service("environmentDao")
public class EnvironmentDaoImpl extends
        ConfigurableBaseSqlMapDao<Environment, Long> implements EnvironmentDao {
    /**
     * DaoMapper 'environmentMapper' reference.
     */
    @Autowired
    private EnvironmentMapper environmentMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.dao.ConfigurableBaseSqlMapDao#getDaoMapper()
     */
    @Override
    public DaoMapper<Environment, Long> getDaoMapper() {
        return environmentMapper;
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
     * com.baidu.cc.configuration.dao.EnvironmentDao#findByProjectId(java.lang
     * .Long)
     */
    @Override
    public List<Environment> findByProjectId(Long projectId) {
        return environmentMapper.findByProjectId(projectId);
    }

    /**
     * 根据projectId删除env.
     * 
     * @param projectId
     *            projectId
     */
    @Override
    public void deleteEnvByProjectId(Long projectId) {
        environmentMapper.deleteEnvByProjectId(projectId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.cc.configuration.dao.EnvironmentDao#findByProjectIds(java.util
     * .List)
     */
    @Override
    public List<Environment> findByProjectIds(List<Long> projectIds) {
        return environmentMapper.findByProjectIds(projectIds);
    }
}