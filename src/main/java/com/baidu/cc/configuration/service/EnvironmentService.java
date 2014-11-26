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
import com.baidu.cc.configuration.bo.Environment;

import java.util.List;

/**
 * Service interface class for model
 * com.baidu.cc.configuration.bo.EnvironmentBase
 * 
 * @author BJF
 */
public interface EnvironmentService extends GenericService<Environment, Long> {

    /**
     * 根据projectId查询工程列表.
     * 
     * @param projectId
     *            projectId
     * @return List<Environment>
     */
    List<Environment> findByProjectId(Long projectId);

    /**
     * 删除env，包含version、config_group、config_item表的相关数据.
     * 
     * @param envIds
     *            envId列表
     */
    void deleteEnvCascadeByIds(List<Long> envIds);

    /**
     * 根据运行环境名称和工程id查询.
     * 
     * @param envName
     *            运行环境名称
     * @param id
     *            工程id
     * @return 运行环境对象或者为null表示不存在
     */
    Environment findByNameAndProjectId(String envName, Long id);

    /**
     * 根据projectId列表查询环境列表.
     * 
     * @param projectIds
     *            the project ids
     * @return the list
     */
    List<Environment> findByProjectIds(List<Long> projectIds);
}