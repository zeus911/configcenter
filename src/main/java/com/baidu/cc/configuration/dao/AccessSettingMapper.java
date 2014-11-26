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
package com.baidu.cc.configuration.dao;

import com.baidu.bjf.dao.DaoMapper;
import com.baidu.bjf.orm.mybatis.SqlMapper;
import com.baidu.cc.configuration.bo.AccessSetting;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Mapper interface class for model
 * com.baidu.cc.configuration.bo.AccessSettingBase
 * 
 * @author BJF
 */
@SqlMapper
public interface AccessSettingMapper extends DaoMapper<AccessSetting, Long> {

    /**
     * 根据userId查询其所有的权限.
     * 
     * @param userId
     *            userId
     * @return List<AccessSetting>
     */
    List<AccessSetting> findAllByUserId(@Param("userId") Long userId);

    /**
     * 检验权限用.
     * 
     * @param userId
     *            userId
     * @param refType
     *            refType
     * @param refId
     *            refId
     * @return List<AccessSetting>
     */
    List<AccessSetting> findByUserIdAndTypeAndRefId(
            @Param("userId") Long userId, @Param("refType") int refType,
            @Param("refId") Long refId);

    /**
     * 查询用户拥有的某类型的所有ID列表.
     * 
     * @param userId
     *            userId
     * @param refType
     *            refType
     * @return List<Long>
     */
    List<Long> findRefIdsByUserIdAndType(@Param("userId") Long userId,
            @Param("refType") int refType);

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
    void deleteAuth(@Param("userId") Long userId, @Param("refId") Long refId,
            @Param("refType") int refType);

    /**
     * 查询某项所有的权限列表.
     * 
     * @param refId
     *            主键，依refType定
     * @param refType
     *            哪种权限
     * @return List<AccessSetting>
     */
    List<AccessSetting> findByRefIdAndType(@Param("refId") Long refId,
            @Param("refType") int refType);
}