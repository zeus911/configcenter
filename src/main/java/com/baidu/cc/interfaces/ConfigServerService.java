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
package com.baidu.cc.interfaces;

import java.util.Map;

/**
 * Service interface of configuration server APIs.
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public interface ConfigServerService {

    /**
     * tag key under configuration center version.
     */
    String TAG_KEY = "__CONFIG_TAG_KEY__";

    /**
     * 根据版本号获取所有配置项内容.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param version
     *            版本号
     * @return Map<String,String> 返回所有的值类型都是String.包括tag版本标识
     */
    Map<String, String> getConfigItems(String user, String password,
            Long version);

    /**
     * 根据版本名称获得配置id, 如果版本不存在，则返回null.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param versionName
     *            版本名称
     * @return 版本id
     */
    Long getVersionId(String user, String password, String versionName);

    /**
     * 根据运行环境id获取最新一份版本的配置项内容.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param envId
     *            运行环境id
     * @return Map<String,String> 返回所有的值类型都是String。 包括tag版本标识
     */
    Map<String, String> getLastestConfigItems(String user, String password,
            Long envId);

    /**
     * 根据运行环境id获取最新一份版本的版本号.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param envId
     *            the env id
     * @return 版本号
     */
    Long getLastestConfigVersion(String user, String password, Long envId);

    /**
     * 根据工程名称与运行环境名称获取最新一份版本的版本号.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param projectName
     *            the project name
     * @param envName
     *            the env name
     * @return 版本号
     */
    Long getLastestConfigVersion(String user, String password,
            String projectName, String envName);

    /**
     * 根据版本号、key获得某项配置内容.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param version
     *            版本号
     * @param key
     *            key
     * @return 配置项内容
     */
    String getConfigItemValue(String user, String password, Long version,
            String key);

    /**
     * 校验版本的tag号是否修改.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param version
     *            版本号
     * @param tag
     *            tag value
     * @return false表示版本tag不一致。
     */
    boolean checkVersionTag(String user, String password, Long version,
            String tag);

}
