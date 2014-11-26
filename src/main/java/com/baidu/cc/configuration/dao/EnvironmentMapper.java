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
import com.baidu.cc.configuration.bo.Environment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Mapper interface class for model
 * com.baidu.cc.configuration.bo.EnvironmentBase
 * 
 * @author BJF
 */
@SqlMapper
public interface EnvironmentMapper extends DaoMapper<Environment, Long> {

    /**
     * 根据projectId查询env列表.
     * 
     * @param projectId
     *            projectId
     * @return List<Environment>
     */
    List<Environment> findByProjectId(Long projectId);

    /**
     * 根据projectId删除env.
     * 
     * @param projectId
     *            projectId
     */
    void deleteEnvByProjectId(Long projectId);

    /**
     * 根据运行环境名称和工程id查询.
     * 
     * @param params
     *            运行环境名称和工程id
     * @return 运行环境对象或者为null表示不存在
     */
    Environment findByNameAndProjectId(Map<String, Object> params);

    /**
     * 根据projectId列表查询环境列表.
     * 
     * @param projectIds
     *            the project ids
     * @return the list
     */
    List<Environment> findByProjectIds(
            @Param("projectIds") List<Long> projectIds);

}