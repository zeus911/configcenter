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
import com.baidu.bjf.web.JndiSupportFilter;
import com.baidu.cc.common.SysUtils;

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

/**
 * Created by zhenhui on 14-3-20. 用户的管理页面，仅限admin账号使用
 * 
 * @author zhenhui
 */
public class UserMgrFilter extends JndiSupportFilter {

    /** 不用filter过滤的url. */
    private String includePath;

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
            long userId = SysUtils.getUserIdFromCookie(request);
            // admin账号的ID固定是1
            if (userId != 1L) {
                throw new ServletException("无权限访问此页面");
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
