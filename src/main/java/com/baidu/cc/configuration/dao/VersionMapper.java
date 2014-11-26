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
import com.baidu.cc.configuration.bo.Version;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Mapper interface class for model com.baidu.cc.configuration.bo.VersionBase
 * 
 * @author BJF
 */
@SqlMapper
public interface VersionMapper extends DaoMapper<Version, Long> {

    /**
     * find by version name.
     * 
     * @param versionName
     *            version name
     * @return {@link Version} object or null if not exist
     */
    Version findByName(String versionName);

    /**
     * 根据环境ID查询所有相关的版本列表.
     * 
     * @param environmentId
     *            environmentId
     * @return List<Version>
     */
    List<Version> findVersionListByEnvId(Long environmentId);

    /**
     * 根据环境ID列表删除所有相关的version.
     * 
     * @param envIds
     *            envIds
     */
    void deleteVersionCascadeByEnvIds(@Param("envIds") List<Long> envIds);

    /**
     * 根据环境ID查询所有相关的versionId列表.
     * 
     * @param envIds
     *            环境ID列表
     * @return versionId列表
     */
    List<Long> findVersionIdsByEnvIds(@Param("envIds") List<Long> envIds);

    /**
     * 根据envId查询id最大的版本号.
     * 
     * @param envId
     *            环境ID
     * @return 版本对象 或者为null即不存在
     */
    Version findLastestByEnvId(Long envId);

    /**
     * 根据envId列表查找版本列表.
     * 
     * @param envIds
     *            the env ids
     * @return the list
     */
    List<Version> findVersionListByEnvIds(@Param("envIds") List<Long> envIds);
}