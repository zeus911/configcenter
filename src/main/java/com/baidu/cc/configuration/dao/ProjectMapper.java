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
import com.baidu.cc.configuration.bo.Project;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Mapper interface class for model com.baidu.cc.configuration.bo.ProjectBase
 * 
 * @author BJF
 */
@SqlMapper
public interface ProjectMapper extends DaoMapper<Project, Long> {

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
    List<Project> findByIds(@Param("projectIds") List<Long> projectIds);
}