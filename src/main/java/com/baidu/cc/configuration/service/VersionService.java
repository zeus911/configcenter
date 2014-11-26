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
import com.baidu.cc.configuration.bo.Version;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Service interface class for model com.baidu.cc.configuration.bo.VersionBase
 * 
 * @author BJF
 */
public interface VersionService extends GenericService<Version, Long> {

    /**
     * query {@link Version} by version id and fetch all related
     * {@link ConfigItem}.
     * 
     * @param versionName
     *            version name
     * @return {@link Version}
     */
    Version findVersionAndConfigItems(String versionName);

    /**
     * 根据envId查找版本列表.
     * 
     * @param environmentId
     *            environmentId
     * @return List<Version>
     */
    List<Version> findVersionListByEnvId(Long environmentId);

    /**
     * 根据envId级联删除version,含其所有子项.
     * 
     * @param ids
     *            ids
     */
    void deleteVersionCascadeByIds(List<Long> ids);

    /**
     * 根据环境ID查询所有相关的versionId列表.
     * 
     * @param envIds
     *            环境ID列表
     * @return versionId列表
     */
    List<Long> findVersionIdsByEnvIds(List<Long> envIds);

    /**
     * 根据envId查询id最大的版本号.
     * 
     * @param envId
     *            环境ID
     * @return 版本对象 或者为null即不存在
     */
    Version findLastestByEnvId(Long envId);

    /**
     * 从文件中导入配置组与配置项到指定版本。.
     * 
     * @param file
     *            导入文件
     * @param versionId
     *            the version id
     * @throws IOException
     *             文件操作异常
     */
    void importFromFile(File file, Long versionId) throws IOException;

    /**
     * 将指定版本号的配置内容导出到输出流.
     * 
     * @param os
     *            输出流
     * @param versionId
     *            版本号id
     * @throws IOException
     *             流操作异常
     */
    void exportToFile(OutputStream os, Long versionId) throws IOException;

    /**
     * 根据envId列表查找版本列表.
     * 
     * @param envIds
     *            the env ids
     * @return the list
     */
    List<Version> findVersionListByEnvIds(List<Long> envIds);

    /**
     * 修改变更通知字段.
     * 
     * @param versionId
     *            版本ID
     */
    void pushChange(Long versionId);

    /**
     * 根据版本名称查询版本ID.
     * 
     * @param name
     *            版本名称
     * @return ID
     */
    Long findIdByName(String name);
}