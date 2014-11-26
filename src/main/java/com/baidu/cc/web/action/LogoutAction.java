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
import com.baidu.bjf.web.session.ExtMapSessionFactory;
import com.baidu.cc.common.SysUtils;
import com.baidu.cc.web.action.LogoutActionBase.DataResult;
import com.baidu.cc.web.action.LogoutActionBase.ReqParam;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * 功能：登出接口.
 * 
 * @author BJF
 */
public class LogoutAction extends LogoutActionBase<ReqParam, DataResult>
        implements ServletResponseAware, ServletRequestAware {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** HttpServletResponse. */
    private HttpServletResponse response;

    /** HttpServletRequest. */
    private HttpServletRequest request;

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
        String uid = ThreadLocalInfo.getThreadUuid();
        ThreadLocalInfo.clear();
        ExtMapSessionFactory.removeMySession(uid);
        SysUtils.deleteLoginCookie(request, response);
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.struts2.interceptor.ServletResponseAware#setServletResponse
     * (javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
     * javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

}
