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

import com.baidu.cc.common.SysUtils;
import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.service.EnvironmentService;
import com.baidu.cc.configuration.service.ProjectService;
import com.baidu.cc.configuration.service.VersionService;
import com.baidu.cc.web.action.VersionSaveActionBase.DataResult;
import com.baidu.cc.web.action.VersionSaveActionBase.ReqParam;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

/**
 * 功能：保存版本编辑页.
 * 
 * @author BJF
 */
public class VersionSaveAction extends
        VersionSaveActionBase<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** VersionService. */
    @Resource
    private VersionService versionService;

    /** ProjectService. */
    @Resource
    private ProjectService projectService;

    /** EnvironmentService. */
    @Resource
    private EnvironmentService environmentService;

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
        Map<String, String> result = new HashMap<String, String>();
        Version version = reqParam.getVersion();
        if (version == null) {
            result.put("envNull", "请输入所有必填项");
        } else {
            if (StringUtils.isBlank(version.getName())
                    || version.getName().length() > 50) {
                result.put("nameLengthError", "名称必须是1到50长度的字符串");
            }
            Long versionId = versionService.findIdByName(version.getName());
            if (versionId != null && !versionId.equals(version.getId())) {
                result.put("nameUniqError", "版本名称已存在，请输入其它名称");
            }

            if (StringUtils.isNotBlank(version.getMemo())
                    && version.getMemo().length() > 255) {
                result.put("memoLengthError", "简介最多255个字符");
            }
        }

        return result;

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
        Version version = reqParam.getVersion();
        Long versionId = version.getId();
        Date now = new Date();
        version.setUpdateTime(now);
        // 未传ID说明是新增
        if (versionId == null) {
            version.setCheckSum(SysUtils.uuid());
            version.setCheckSumDate(now);
            versionService.saveEntitySelective(version);
        } else {
            versionService.updateEntitySelective(version);
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
