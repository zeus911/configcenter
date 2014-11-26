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
package com.baidu.cc.configuration.service;

import com.baidu.bjf.service.GenericService;
import com.baidu.cc.configuration.bo.AccessSetting;

import java.util.List;

/**
 * Service interface class for model
 * com.baidu.cc.configuration.bo.AccessSettingBase
 * 
 * @author BJF
 */
public interface AccessSettingService extends
        GenericService<AccessSetting, Long> {

    /** checkAuth type of project. */
    public static final int Auth_TYPE_PROJECT = 1;

    /** checkAuth type of environment. */
    public static final int Auth_TYPE_ENV = 2;

    /**
     * 根据userId查询其所有的权限.
     * 
     * @param userId
     *            userId
     * @return List<AccessSetting>
     */
    List<AccessSetting> findAllByUserId(Long userId);

    /**
     * 查询用户拥有的某类型的所有ID列表.
     * 
     * @param userId
     *            userId
     * @param refType
     *            refType
     * @return List<Long>
     */
    List<Long> findRefIdsByUserIdAndType(Long userId, int refType);

    /**
     * 检验是否有某配置的权限.
     * 
     * @param userId
     *            用户ID
     * @param refType
     *            类型 1、工程，2、环境
     * @param refId
     *            引用的ID，与refType共同决定权限
     * @return true/false
     */
    boolean checkAuth(Long userId, int refType, Long refId);

    /**
     * 删除权限.
     * 
     * @param userId
     *            userId
     * @param refId
     *            主键，依refType定
     * @param refType
     *            哪种权限
     */
    void delete(Long userId, Long refId, int refType);

    /**
     * 查询某项所有的权限列表.
     * 
     * @param refId
     *            主键，依refType定
     * @param refType
     *            哪种权限
     * @return List<AccessSetting>
     */
    List<AccessSetting> findByRefIdAndType(Long refId, int refType);
}