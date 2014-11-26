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
package com.baidu.cc.configuration.service.impl;

import com.baidu.bjf.dao.SqlMapDao;
import com.baidu.bjf.service.GenericSqlMapServiceImpl;
import com.baidu.cc.configuration.bo.AccessSetting;
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.dao.AccessSettingDao;
import com.baidu.cc.configuration.dao.AccessSettingMapper;
import com.baidu.cc.configuration.dao.EnvironmentDao;
import com.baidu.cc.configuration.service.AccessSettingService;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * Service implement class for model :
 * com.baidu.cc.configuration.bo.AccessSettingBase
 * 
 * @author BJF
 */
@Service("accessSettingService")
public class AccessSettingServiceImpl extends
        GenericSqlMapServiceImpl<AccessSetting, Long> implements
        AccessSettingService {
    /**
     * Dao 'accessSettingDao' reference.
     */
    @Resource(name = "accessSettingDao")
    private AccessSettingDao accessSettingDao;

    /** AccessSettingMapper. */
    @Resource
    private AccessSettingMapper accessSettingMapper;

    /** EnvironmentDao. */
    @Resource
    private EnvironmentDao environmentDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<AccessSetting, Long> getDao() {
        return accessSettingDao;
    }

    /**
     * 根据userId查询其所有的权限.
     * 
     * @param userId
     *            userId
     * @return List<AccessSetting>
     */
    @Override
    public List<AccessSetting> findAllByUserId(Long userId) {
        return accessSettingMapper.findAllByUserId(userId);
    }

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
    @Override
    public boolean checkAuth(Long userId, int refType, Long refId) {
        // TODO 后期看情况加缓存
        List<AccessSetting> result = accessSettingMapper
                .findByUserIdAndTypeAndRefId(userId, refType, refId);

        // XXX modify by malin
        boolean notEmpty = CollectionUtils.isNotEmpty(result);
        if (!notEmpty && refType == Auth_TYPE_ENV) {
            // to check if has project authorization
            Environment e = environmentDao.findById(refId);
            if (e != null) {
                result = accessSettingMapper.findByUserIdAndTypeAndRefId(
                        userId, Auth_TYPE_PROJECT, e.getProjectId());
                return CollectionUtils.isNotEmpty(result);
            }
        }

        return notEmpty;
    }

    /**
     * 查询用户拥有的某类型的所有ID列表.
     * 
     * @param userId
     *            userId
     * @param refType
     *            refType
     * @return List<Long>
     */
    @Override
    public List<Long> findRefIdsByUserIdAndType(Long userId, int refType) {
        return accessSettingMapper.findRefIdsByUserIdAndType(userId, refType);
    }

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
    @Override
    public void delete(Long userId, Long refId, int refType) {
        accessSettingMapper.deleteAuth(userId, refId, refType);
    }

    /**
     * 查询某项所有的权限列表.
     * 
     * @param refId
     *            主键，依refType定
     * @param refType
     *            哪种权限
     * @return List<AccessSetting>
     */
    @Override
    public List<AccessSetting> findByRefIdAndType(Long refId, int refType) {
        return accessSettingMapper.findByRefIdAndType(refId, refType);
    }
}