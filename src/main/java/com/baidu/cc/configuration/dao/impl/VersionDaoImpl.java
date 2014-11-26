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
import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.dao.VersionDao;
import com.baidu.cc.configuration.dao.VersionMapper;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Dao implement class for model : com.baidu.cc.configuration.bo.VersionBase
 * 
 * @author BJF
 */
@Service("versionDao")
public class VersionDaoImpl extends ConfigurableBaseSqlMapDao<Version, Long>
        implements VersionDao {
    /**
     * DaoMapper 'versionMapper' reference.
     */
    @Autowired
    private VersionMapper versionMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.dao.ConfigurableBaseSqlMapDao#getDaoMapper()
     */
    @Override
    public DaoMapper<Version, Long> getDaoMapper() {
        return versionMapper;
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
     * com.baidu.cc.configuration.dao.VersionDao#findVersionListByEnvId(java
     * .lang.Long)
     */
    @Override
    public List<Version> findVersionListByEnvId(Long environmentId) {
        return versionMapper.findVersionListByEnvId(environmentId);
    }

    /**
     * 根据环境ID列表删除所有相关的version.
     * 
     * @param envIds
     *            envIds
     */
    @Override
    public void deleteVersionCascadeByEnvIds(List<Long> envIds) {
        versionMapper.deleteVersionCascadeByEnvIds(envIds);
    }

    /**
     * 根据环境ID查询所有相关的versionId列表.
     * 
     * @param envIds
     *            环境ID列表
     * @return versionId列表
     */
    @Override
    public List<Long> findVersionIdsByEnvIds(List<Long> envIds) {
        return versionMapper.findVersionIdsByEnvIds(envIds);
    }

    /**
     * 根据envId列表查找版本列表.
     * 
     * @param envIds
     *            环境ID列表
     * @return version列表
     */
    @Override
    public List<Version> findVersionListByEnvIds(List<Long> envIds) {
        return versionMapper.findVersionListByEnvIds(envIds);
    }
}