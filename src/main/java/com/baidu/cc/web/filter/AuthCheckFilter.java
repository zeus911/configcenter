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
package com.baidu.cc.web.filter;

import com.baidu.bjf.adapter.util.UrlUtils;
import com.baidu.bjf.beans.ServiceLocator;
import com.baidu.bjf.web.JndiSupportFilter;
import com.baidu.cc.common.AuthException;
import com.baidu.cc.common.SysUtils;
import com.baidu.cc.configuration.service.AccessSettingService;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by zhenhui on 14-3-20.
 * 
 * @author zhenhui
 */
public class AuthCheckFilter extends JndiSupportFilter {

    /** 不用filter过滤的url. */
    private String includePath;

    /** AccessSettingService. */
    private AccessSettingService accessSettingService;

    /** SortedSet<String>. */
    private SortedSet<String> includePathv;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.bjf.web.JndiSupportFilter#doInit(javax.servlet.FilterConfig)
     */
    @Override
    protected void doInit(FilterConfig filterConfig) throws ServletException {
        includePathv = new TreeSet<String>();
        if (includePath != null) {
            String[] paths = includePath.split(";\n?");
            for (String p : paths) {
                if (p != null && p.length() > 0)
                    includePathv.add(p.trim());
            }
        }

        accessSettingService = (AccessSettingService) ServiceLocator
                .getInstance().getBean("accessSettingService");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        if (request.getParameter("method") != null) {
            path = path + "?method=" + request.getParameter("method");
        }

        // 不属于includePath 不要验证，直接跳过
        if (UrlUtils.urlMatch(includePathv, path)) {
            if (StringUtils.contains(
                    "/project/editProject.action;/project/saveProject.action;",
                    path)) {
                // 如果是导向工程编辑页和保存工程请求，要依据有没有projectId来判定是新增还是编辑，至少保证任何人都有新增的权限
                String tmpProjectId = request
                        .getParameter("reqParam.project.id");
                if (StringUtils.isEmpty(tmpProjectId)) {
                    tmpProjectId = request.getParameter("reqParam.projectId");
                }
                Long pid = NumberUtils.toLong(tmpProjectId, 0);
                if (pid < 1L) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            String authCheck = request.getParameter("authCheck");

            Long projectId = SysUtils.getProjectIdFromAuthcheck(authCheck);
            Long userId = SysUtils.getUserIdFromAuthcheck(authCheck);
            Long cookieUserId = SysUtils.getUserIdFromCookie(request);

            // 防止用户从别处贴个别人的authCheck过来或者随便输入点东西
            if (projectId == 0L
                    || userId == 0L
                    || !cookieUserId.equals(userId)
                    || !accessSettingService.checkAuth(userId,
                            SysUtils.ACCESS_SETTING_TYPE_PROJECT, projectId)) {
                throw new AuthException("无此权限");
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Sets the include path.
     * 
     * @param includePath
     *            includePath
     */
    public void setIncludePath(String includePath) {
        this.includePath = includePath;
    }

}
