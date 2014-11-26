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
import com.baidu.cc.common.SysUtils;
import com.baidu.cc.configuration.bo.AccessSetting;
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.bo.Project;
import com.baidu.cc.configuration.dao.ProjectDao;
import com.baidu.cc.configuration.dao.ProjectMapper;
import com.baidu.cc.configuration.service.AccessSettingService;
import com.baidu.cc.configuration.service.EnvironmentService;
import com.baidu.cc.configuration.service.ProjectService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implement class for model : com.baidu.cc.configuration.bo.ProjectBase
 * 
 * @author BJF
 */
@Service("projectService")
public class ProjectServiceImpl extends GenericSqlMapServiceImpl<Project, Long>
        implements ProjectService {
    /**
     * Dao 'projectDao' reference.
     */
    @Resource(name = "projectDao")
    private ProjectDao projectDao;

    /**
     * Mapper 'projectMapper' reference.
     */
    @Autowired
    private ProjectMapper projectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<Project, Long> getDao() {
        return projectDao;
    }

    /** EnvironmentService. */
    @Resource
    private EnvironmentService environmentService;

    /** AccessSettingService. */
    @Resource
    private AccessSettingService accessSettingService;

    /**
     * 根据projectId删除本项目所有配置 env、version、config_group、config_item等全部.
     * 
     * @param userId
     *            userId
     * @param projectId
     *            projectId
     * @return true/false
     */
    @Override
    public void deleteProjectCascadeById(Long userId, Long projectId) {
        // 先级联删除相关的环境子项
        List<Environment> environmentList = environmentService
                .findByProjectId(projectId);
        List<Long> envIds = new ArrayList<Long>();
        for (Environment environment : environmentList) {
            envIds.add(environment.getId());
        }
        environmentService.deleteEnvCascadeByIds(envIds);

        List<AccessSetting> acList = accessSettingService.findByRefIdAndType(
                projectId, SysUtils.ACCESS_SETTING_TYPE_PROJECT);

        accessSettingService.deleteBatch(acList);

        projectDao.delete(projectId);
    }

    /**
     * 根据工程名称查找工程.
     * 
     * @param projectName
     *            工程名称
     * @return 工程对象或者为null表示不存在。
     */
    @Override
    public Project findByName(String projectName) {
        return projectMapper.findByName(projectName);
    }

    /**
     * 根据ID列表批量查询.
     * 
     * @param projectIds
     *            工程ID列表
     * @return List<Project>
     */
    @Override
    public List<Project> findByIds(List<Long> projectIds) {
        if (CollectionUtils.isEmpty(projectIds)) {
            return new ArrayList<Project>();
        }
        return projectMapper.findByIds(projectIds);
    }

    /**
     * 新增一个工程，并为操作者添加权限.
     * 
     * @param userId
     *            userId
     * @param project
     *            project
     * @return 新增后的主键
     */
    @Override
    public Long insertProject(Long userId, Project project) {
        Long projectId = saveEntity(project);
        saveAuth(userId, projectId);

        if (!SysUtils.OPADMIN_USERID.equals(userId)) {
            // 所有的项目都为opadmin添加权限
            saveAuth(SysUtils.OPADMIN_USERID, projectId);
        }
        return projectId;
    }

    /**
     * 添加工程的权限.
     * 
     * @param userId
     *            用户ID
     * @param projectId
     *            工程ID
     */
    private void saveAuth(Long userId, Long projectId) {
        AccessSetting accessSetting = new AccessSetting();
        accessSetting.setCreateTime(new Date());
        accessSetting.setUserId(userId);
        accessSetting.setRefId(projectId);
        accessSetting.setType((byte) SysUtils.ACCESS_SETTING_TYPE_PROJECT);
        accessSettingService.saveEntity(accessSetting);
    }
}