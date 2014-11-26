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
import com.baidu.bjf.util.ThreadLocalInfo;
import com.baidu.bjf.web.JndiSupportFilter;
import com.baidu.cc.common.SysUtils;
import com.baidu.cc.configuration.bo.User;
import com.baidu.uc.util.CookieHelper;

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
public class LoginFilter extends JndiSupportFilter {

    /** 不用filter过滤的url. */
    private String excludePath;

    /** SortedSet<String>. */
    private SortedSet<String> excludePathv;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.bjf.web.JndiSupportFilter#doInit(javax.servlet.FilterConfig)
     */
    @Override
    protected void doInit(FilterConfig filterConfig) throws ServletException {
        excludePathv = new TreeSet<String>();
        if (excludePath != null) {
            String[] paths = excludePath.split(";");
            for (String p : paths) {
                if (p != null && p.length() > 0)
                    excludePathv.add(p);
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

        // 属于exclude path 不要验证，直接跳过
        if (UrlUtils.urlMatch(excludePathv, path)) {
            filterChain.doFilter(request, response);
            return;
        }

        CookieHelper cookieHelper = new CookieHelper(request, response);

        Long uid = NumberUtils.toLong(cookieHelper.getCookieVal("rcc_uid"));
        String name = cookieHelper.getCookieVal("rcc_name");
        String token = cookieHelper.getCookieVal("rcc_token");
        if (uid > 0L && StringUtils.isNotEmpty(name)
                && SysUtils.genCookieToken(uid, name).equals(token)) {
            ThreadLocalInfo.setThreadUuid(uid.toString());
            User user = new User();
            user.setId(uid);
            user.setName(name);
            SysUtils.addLoginCookie(request, response, user);
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(StringUtils.defaultIfEmpty(
                    request.getContextPath(), "/"));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.web.JndiSupportFilter#destroy()
     */
    @Override
    public void destroy() {

    }

    /**
     * Sets the exclude path.
     * 
     * @param excludePath
     *            the excludePath to set
     */
    public void setExcludePath(String excludePath) {
        this.excludePath = excludePath;
    }

}
