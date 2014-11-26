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
package com.baidu.cc.web.action.fortest;

import com.baidu.cc.interfaces.ConfigServerService;
import com.baidu.cc.web.action.fortest.GetConfigItemsActionBase.DataResult;
import com.baidu.cc.web.action.fortest.GetConfigItemsActionBase.ReqParam;

import java.util.Map;

import javax.annotation.Resource;

/**
 * 功能：压力测试专用-根据版本号获取所有配置项内容.
 * 
 * @author BJF
 */
public class GetConfigItemsAction extends
        GetConfigItemsActionBase<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** ConfigServerService. */
    @Resource
    private ConfigServerService configServerService;

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
        Map<String, String> configItems = configServerService.getConfigItems(
                reqParam.getUsername(), reqParam.getPassword(),
                reqParam.getVersionId());
        if (configItems != null) {
            getData().setResult(configItems.toString());
        }
        return getData();
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
