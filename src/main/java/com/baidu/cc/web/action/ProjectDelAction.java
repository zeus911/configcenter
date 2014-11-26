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
package com.baidu.cc.web.action;

import com.baidu.bjf.util.ThreadLocalInfo;
import com.baidu.cc.configuration.service.AccessSettingService;
import com.baidu.cc.configuration.service.ProjectService;
import com.baidu.cc.web.action.ProjectDelActionBase.DataResult;
import com.baidu.cc.web.action.ProjectDelActionBase.ReqParam;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 功能：删除工程.
 * 
 * @author BJF
 */
public class ProjectDelAction extends
        ProjectDelActionBase<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** ProjectService. */
    @Resource
    private ProjectService projectService;

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
        List<String> projectIds = reqParam.getProjectIds();
        Long userId = NumberUtils.toLong(ThreadLocalInfo.getThreadUuid());
        if (projectIds == null || projectIds.size() == 0) {
            return null;
        }

        for (String projectId : projectIds) {
            projectService.deleteProjectCascadeById(userId,
                    NumberUtils.toLong(projectId));
        }
        return null;
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
