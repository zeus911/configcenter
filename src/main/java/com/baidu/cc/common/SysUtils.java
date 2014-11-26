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
package com.baidu.cc.common;

import com.baidu.cc.configuration.bo.User;
import com.baidu.rigel.platform.util.Security;
import com.baidu.uc.util.CookieHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * 工具方法 Created by zhenhui on 14-2-27.
 * 
 * @author zhenhui
 */
public class SysUtils {
    /**
     * a resource bundle for logclient constant config.
     */
    public static final ResourceBundle CONSTANTS = ResourceBundle.getBundle("logclient");

    /** Character encode string. */
    public static final String UTF_8 = "utf-8";

    /** 登录用，用来配合uid和name生成加密字符串. */
    public static final String COOKIE_MD5_TOKEN = "configcenter_cookie_token_for_md5";

    /** 权限校验用. */
    public static final String AUTHCHECK_DES_KEY = "configcenter_authcheck_key_for_des";

    /** cookie设置时的path. */
    public static final String COOKIE_PATH = "/";

    /** cookie设置时的maxage. */
    public static final int COOKIE_MAXAGE = 1800;
    
    /** cookie设置时uid的key名称. */
    public static final String COOKIE_KEY_UID = "rcc_uid";

    /** cookie设置时name的key名称. */
    public static final String COOKIE_KEY_NAME = "rcc_name";
    
    /** cookie设置时token的key名称. */
    public static final String COOKIE_KEY_TOKEN = "rcc_token";
    
    /**
     * 对应access_setting.type 工程
     */
    public static final int ACCESS_SETTING_TYPE_PROJECT = 1;

    /**
     * 对应access_setting.type 环境
     */
    public static final int ACCESS_SETTING_TYPE_ENV = 2;

    /** 超级管理员账号ID=1，只负责管理其它账号. */
    public static final Long ADMIN_USERID = 1L;

    /** OP管理员账号ID=2，拥有所有项目的读写权限. */
    public static final Long OPADMIN_USERID = 2L;

    /**
     * 产生UUID字符串.
     * 
     * @return UUID字符串 36位
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 生成存入cookie的token加密值.
     * 
     * @param userId
     *            userId
     * @param name
     *            name
     * @return MD5(COOKIE_MD5_TOKEN + userId + name)
     */
    public static String genCookieToken(long userId, String name) {
        return Security.MD5Encode(COOKIE_MD5_TOKEN + userId + name);
    }

    /**
     * 设置登录后的cookie信息.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param user
     *            User
     */
    public static void addLoginCookie(HttpServletRequest request,
            HttpServletResponse response, User user) {
        CookieHelper cookieHelper = new CookieHelper(request, response);

        cookieHelper.setCookie(COOKIE_KEY_UID, user.getId().toString(),
                COOKIE_MAXAGE, COOKIE_PATH, null);
        cookieHelper.setCookie(COOKIE_KEY_NAME, user.getName(), COOKIE_MAXAGE,
                COOKIE_PATH, null);
        cookieHelper.setCookie(COOKIE_KEY_TOKEN,
                SysUtils.genCookieToken(user.getId(), user.getName()),
                COOKIE_MAXAGE, COOKIE_PATH, null);

    }

    /**
     * 删除登录后的cookie信息.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     */
    public static void deleteLoginCookie(HttpServletRequest request,
            HttpServletResponse response) {

        CookieHelper cookieHelper = new CookieHelper(request, response);
        cookieHelper.setCookie(COOKIE_KEY_UID, "", 0, COOKIE_PATH, null);
        cookieHelper.setCookie(COOKIE_KEY_NAME, "", 0, COOKIE_PATH, null);
        cookieHelper.setCookie(COOKIE_KEY_TOKEN, "", 0, COOKIE_PATH, null);
    }

    /**
     * 给权限校验参数加密.
     * 
     * @param userId
     *            用户ID
     * @param projectId
     *            工程ID
     * @return DES加密后的字符串
     */
    public static String encryptAuthCheck(Long userId, Long projectId) {
        return Security.DESEncrypt("userId=" + userId + ";projectId="
                + projectId, AUTHCHECK_DES_KEY);
    }

    /**
     * 给权限校验参数加密.
     * 
     * @param request
     *            HttpServletRequest
     * @param projectId
     *            工程ID
     * @return DES加密后的字符串
     */
    public static String encryptAuthCheck(HttpServletRequest request,
            Long projectId) {
        return Security.DESEncrypt("userId=" + getUserIdFromCookie(request)
                + ";projectId=" + projectId, AUTHCHECK_DES_KEY);
    }

    /**
     * 从authCheck参数中获取userId.
     * 
     * @param authCheck
     *            authCheck
     * @return userId
     */
    public static Long getUserIdFromAuthcheck(String authCheck) {
        String value = Security.DESDecrypt(authCheck, AUTHCHECK_DES_KEY);
        return NumberUtils.toLong(StringUtils.substringBetween(value,
                "userId=", ";projectId"));
    }

    /**
     * 从cookie中获取userId.
     * 
     * @param request
     *            HttpServletRequest
     * @return userId
     */
    public static Long getUserIdFromCookie(HttpServletRequest request) {
        return NumberUtils.toLong(new CookieHelper(request, null)
                .getCookieVal(COOKIE_KEY_UID));
    }

    /**
     * 从authCheck参数中获取projectId.
     * 
     * @param authCheck
     *            authCheck
     * @return userId
     */
    public static Long getProjectIdFromAuthcheck(String authCheck) {
        String value = Security.DESDecrypt(authCheck, AUTHCHECK_DES_KEY);
        return NumberUtils.toLong(StringUtils.substringAfter(value,
                "projectId="));
    }

    /**
     * 把逗号分隔的ID字串转换成List<Long>.
     * 
     * @param ids
     *            the ids
     * @return the list
     * @return　List<Long>
     */
    public static List<Long> split2Ids(String ids) {

        ArrayList<Long> result = new ArrayList<Long>();
        if (StringUtils.isEmpty(ids)) {
            return result;
        }

        String[] idsArray = StringUtils.split(ids, ",");

        for (String id : idsArray) {
            result.add(NumberUtils.toLong(id));
        }
        return result;
    }
    
    /**
     * Get constant.
     * 
     * @param key key of constant
     * @return value of constant
     */
    public static String getConstants(String key) {
        return CONSTANTS.getString(key);
    }
}
