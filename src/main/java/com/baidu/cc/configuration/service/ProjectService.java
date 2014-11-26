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
import com.baidu.cc.configuration.bo.Project;

import java.util.List;

/**
 * Service interface class for model com.baidu.cc.configuration.bo.ProjectBase
 * 
 * @author BJF
 */
public interface ProjectService extends GenericService<Project, Long> {

    /**
     * 新增一个工程，并为操作者添加权限.
     * 
     * @param userId
     *            userId
     * @param project
     *            project
     * @return 新增后的主键
     */
    Long insertProject(Long userId, Project project);

    /**
     * 根据projectId删除本项目所有配置 env、version、config_group、config_item等全部.
     * 
     * @param userId
     *            userId
     * @param projectId
     *            projectId
     * @return true/false
     */
    void deleteProjectCascadeById(Long userId, Long projectId);

    /**
     * 根据工程名称查找工程.
     * 
     * @param projectName
     *            工程名称
     * @return 工程对象或者为null表示不存在。
     */
    Project findByName(String projectName);

    /**
     * 根据ID列表批量查询.
     * 
     * @param projectIds
     *            工程ID列表
     * @return List<Project>
     */
    List<Project> findByIds(List<Long> projectIds);
}