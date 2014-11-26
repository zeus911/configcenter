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
package com.baidu.cc.web.action.tree;

import com.baidu.bjf.util.ThreadLocalInfo;
import com.baidu.cc.common.SysUtils;
import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.bo.Project;
import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.dto.TreeDto;
import com.baidu.cc.configuration.service.AccessSettingService;
import com.baidu.cc.configuration.service.ConfigGroupService;
import com.baidu.cc.configuration.service.EnvironmentService;
import com.baidu.cc.configuration.service.ProjectService;
import com.baidu.cc.configuration.service.VersionService;
import com.baidu.cc.web.action.tree.ListProjectActionBase.DataResult;
import com.baidu.cc.web.action.tree.ListProjectActionBase.ReqParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 功能：树形菜单列表.
 * 
 * @author BJF
 */
public class ListProjectAction extends
        ListProjectActionBase<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** ProjectService. */
    @Resource
    private ProjectService projectService;

    /** EnvironmentService. */
    @Resource
    private EnvironmentService environmentService;

    /** VersionService. */
    @Resource
    private VersionService versionService;

    /** ConfigGroupService. */
    @Resource
    private ConfigGroupService configGroupService;

    /** AccessSettingService. */
    @Resource
    private AccessSettingService accessSettingService;

    // step1 validate parameters. could be ignore

    /**
     * <pre>
     * 参数验证。此处根据用户需求修改，平台工具不支持增量维护。
     * 备注：自定义验证不支持前后端一致性验证功能支持
     * </pre>
     * 
     * .
     * 
     * @param reqParam
     *            request param
     * @return req parameters valid result.
     */
    protected Map<String, String> doValidate(ReqParam reqParam) {
        return null;
    }

    // step2 customize parse parameters. could be ignore

    /**
     * 自定义参数解析。此处根据用户需求修改，平台工具不支持增量维护。.
     * 
     * @param reqParam
     *            request param
     * @return request parameters after user process.
     */
    protected ReqParam doParseParam(ReqParam reqParam) {
        return reqParam;
    }

    // step3 call service component

    /**
     * 服务执行方法.
     * 
     * @param reqParam
     *            request param
     * @return 返回结果对象
     */
    @Override
    protected DataResult doExecuteService(ReqParam reqParam) {
        DataResult dataResult = new DataResult();

        Long userId = NumberUtils.toLong(ThreadLocalInfo.getThreadUuid());

        List<TreeDto> treeList = new ArrayList<TreeDto>();

        // 显示出用户有权限的树
        String uid = ThreadLocalInfo.getThreadUuid();
        List<Long> projectIdList = accessSettingService
                .findRefIdsByUserIdAndType(NumberUtils.toLong(uid),
                        SysUtils.ACCESS_SETTING_TYPE_PROJECT);

        List<Project> projectList = projectService.findByIds(projectIdList);

        List<Long> projectIds = new ArrayList<Long>();
        List<Long> envIds = new ArrayList<Long>();
        List<Long> verIds = new ArrayList<Long>();
        // 父节点
        TreeDto rootNode = new TreeDto("0", "-1", TreeDto.ROOT_URL, "工程列表");
        treeList.add(rootNode);
        for (Project project : projectList) {
            // 加入一个工程节点
            TreeDto projectNode = new TreeDto(TreeDto.TYPE_PROJECT_ID_PREFIX
                    + project.getId(), "0", TreeDto.TYPE_PROJECT_URL
                    + project.getId() + "&authCheck="
                    + SysUtils.encryptAuthCheck(userId, project.getId()),
                    project.getName());
            treeList.add(projectNode);
            projectIds.add(project.getId());
        }
        List<Environment> environmentList = environmentService
                .findByProjectIds(projectIds);
        for (Environment env : environmentList) {
            // 加入一个环境节点
            TreeDto envNode = new TreeDto(TreeDto.TYPE_ENV_ID_PREFIX
                    + env.getId(), TreeDto.TYPE_PROJECT_ID_PREFIX
                    + env.getProjectId(), TreeDto.TYPE_ENV_URL + env.getId()
                    + "&authCheck="
                    + SysUtils.encryptAuthCheck(userId, env.getProjectId()),
                    env.getName());
            treeList.add(envNode);
            envIds.add(env.getId());
        }
        List<Version> versionList = versionService
                .findVersionListByEnvIds(envIds);
        for (Version ver : versionList) {
            // 加入一个版本节点
            TreeDto verNode = new TreeDto(TreeDto.TYPE_VER_ID_PREFIX
                    + ver.getId(), TreeDto.TYPE_ENV_ID_PREFIX
                    + ver.getEnvironmentId(), TreeDto.TYPE_VER_URL
                    + ver.getId() + "&authCheck="
                    + SysUtils.encryptAuthCheck(userId, ver.getProjectId()),
                    ver.getName());
            treeList.add(verNode);
            verIds.add(ver.getId());
        }
        List<ConfigGroup> configGroupList = configGroupService
                .findByVersionIds(verIds);

        for (ConfigGroup group : configGroupList) {
            // TODO 待改成批量方法
            Version ver = versionService.findById(group.getVersionId());

            // 加入一个分组节点
            TreeDto groupNode = new TreeDto(TreeDto.TYPE_GROUP_ID_PREFIX
                    + group.getId(), TreeDto.TYPE_VER_ID_PREFIX
                    + group.getVersionId(), TreeDto.TYPE_GROUP_URL
                    + group.getId() + "&authCheck="
                    + SysUtils.encryptAuthCheck(userId, ver.getProjectId()),
                    group.getName());
            treeList.add(groupNode);
        }
        dataResult.setTreeList(treeList);

        return dataResult;
    }

    // step4 wrap result return to font page

    /**
     * 结果返回包装方法.
     * 
     * @param dr
     *            返回结果对象
     * @return result after user process.
     */
    protected Object doWrapResult(DataResult dr) {
        return dr;
    }

}
