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
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.dao.EnvironmentDao;
import com.baidu.cc.configuration.dao.EnvironmentMapper;
import com.baidu.cc.configuration.service.EnvironmentService;
import com.baidu.cc.configuration.service.VersionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implement class for model :
 * com.baidu.cc.configuration.bo.EnvironmentBase
 * 
 * @author BJF
 */
@Service("environmentService")
public class EnvironmentServiceImpl extends
        GenericSqlMapServiceImpl<Environment, Long> implements
        EnvironmentService {
    /**
     * Dao 'environmentDao' reference.
     */
    @Resource(name = "environmentDao")
    private EnvironmentDao environmentDao;

    /**
     * Mapper 'environmentMapper' reference.
     */
    @Autowired
    private EnvironmentMapper environmentMapper;

    /** VersionService. */
    @Resource
    private VersionService versionService;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<Environment, Long> getDao() {
        return environmentDao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.cc.configuration.service.EnvironmentService#findByProjectId
     * (java.lang.Long)
     */
    @Override
    public List<Environment> findByProjectId(Long projectId) {
        return environmentDao.findByProjectId(projectId);
    }

    /**
     * 删除env，包含version、config_group、config_item表的相关数据.
     * 
     * @param envIds
     *            envId列表
     */
    @Override
    public void deleteEnvCascadeByIds(List<Long> envIds) {
        List<Long> versionIds = versionService.findVersionIdsByEnvIds(envIds);
        versionService.deleteVersionCascadeByIds(versionIds);
        environmentDao.deleteBatch(envIds.toArray(new Long[] {}));
    }

    /**
     * 根据运行环境名称和工程id查询.
     * 
     * @param envName
     *            运行环境名称
     * @param projectId
     *            工程id
     * @return 运行环境对象或者为null表示不存在
     */
    @Override
    public Environment findByNameAndProjectId(String envName, Long projectId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("envName", envName);
        params.put("projectId", projectId);
        return environmentMapper.findByNameAndProjectId(params);
    }

    /**
     * 根据projectId列表查询环境列表.
     * 
     * @param projectIds
     *            the project ids
     * @return the list
     */
    @Override
    public List<Environment> findByProjectIds(List<Long> projectIds) {
        if (CollectionUtils.isEmpty(projectIds)) {
            return new ArrayList<Environment>();
        }
        return environmentDao.findByProjectIds(projectIds);
    }
}