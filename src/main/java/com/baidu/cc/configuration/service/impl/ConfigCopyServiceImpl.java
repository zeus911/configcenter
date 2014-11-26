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

import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.service.ConfigCopyService;
import com.baidu.cc.configuration.service.ConfigGroupService;
import com.baidu.cc.configuration.service.ConfigItemService;
import com.baidu.cc.configuration.service.EnvironmentService;
import com.baidu.cc.configuration.service.ProjectService;
import com.baidu.cc.configuration.service.VersionService;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by zhenhui on 14-3-7.
 * 
 * @author zhenhui
 */
@Service("configCopyService")
public class ConfigCopyServiceImpl implements ConfigCopyService {

    /** Logger for this class. */
    private static final Logger LOGGER = Logger
            .getLogger(ConfigCopyServiceImpl.class);

    /** ConfigItemService. */
    @Resource
    private ConfigItemService configItemService;

    /** ConfigGroupService. */
    @Resource
    private ConfigGroupService configGroupService;

    /** VersionService. */
    @Resource
    private VersionService versionService;

    /** EnvironmentService. */
    @Resource
    private EnvironmentService environmentService;

    /** ProjectService. */
    @Resource
    private ProjectService projectService;

    /**
     * 从一个group复制所有的配置项到另一个已有的group里.
     * 
     * @param srcGroupId
     *            源GroupId
     * @param destGroupId
     *            目标GroupId
     */
    @Override
    public void copyConfigItemsFromGroup(Long srcGroupId, Long destGroupId) {

        Date now = new Date();
        List<ConfigItem> srcConfigItems = configItemService.findByGroupId(
                srcGroupId, false);
        ConfigGroup destConfigGroup = configGroupService.findById(destGroupId);

        for (ConfigItem srcConfigItem : srcConfigItems) {
            // 主键自增，置空
            srcConfigItem.setId(null);
            srcConfigItem.setGroupId(destGroupId);
            srcConfigItem.setCreateTime(now);
            srcConfigItem.setUpdateTime(null);
            srcConfigItem.setVersionId(destConfigGroup.getVersionId());
        }
        configItemService.saveConfigItems(srcConfigItems, null, null);
    }
    
    /**
     * 从一个group复制所有的配置项到另一个已有的group里.
     * 
     * @param configGroup
     *            group对象
     * @param destGroupId
     *            目标GroupId
     */
    public void copyConfigItemsFromGroup(ConfigGroup configGroup,
            Long destGroupId) {

        // save config item under group
        List<ConfigItem> configItems = configGroup.getConfigItems();
        if (CollectionUtils.isEmpty(configItems)) {
            LOGGER.warn("#copyConfigItemsFromGroup found no config items under group");
            return;
        }

        Date now = new Date();
        for (ConfigItem srcConfigItem : configItems) {
            srcConfigItem.setId(null);
            srcConfigItem.setGroupId(destGroupId);
            srcConfigItem.setCreateTime(now);
            srcConfigItem.setUpdateTime(null);
            srcConfigItem.setVersionId(configGroup.getVersionId());

            configItemService.saveEntity(srcConfigItem);
        }

    }

    /**
     * 从一个version复制所有的配置项到另一个存在的version里.
     * 
     * @param srcVersionId
     *            源versionId
     * @param destVersionId
     *            目标versionId
     */
    @Override
    public void copyConfigItemsFromVersion(Long srcVersionId, Long destVersionId) {

        // 先将源version下的所有group查出来，使用新增的versionId,复制一份
        List<ConfigGroup> configGroups = configGroupService
                .findByVersionId(srcVersionId);

        Date now = new Date();

        for (ConfigGroup configGroup : configGroups) {
            Long srcGroupId = configGroup.getId();
            configGroup.setId(null);
            configGroup.setCreateTime(now);
            configGroup.setUpdateTime(null);
            configGroup.setVersionId(destVersionId);
            Long destGroupId = configGroupService.saveEntity(configGroup);
            copyConfigItemsFromGroup(srcGroupId, destGroupId);
        }
    }

    /**
     * 从一个version复制所有的配置项到另一个存在的version里.
     * 
     * @param version
     *            需要复制的版本对象
     * @param destVersionId
     *            目录version id
     */
    public void copyConfigItemsFromVersion(Version version, Long destVersionId) {
        Assert.notNull(version, "param 'version' is null.");
        // check destination version id exist
        Version destVersion = versionService.findById(destVersionId);
        Assert.notNull(destVersion, "version id '" + destVersionId
                + "' not exist.");

        // get groups
        List<ConfigGroup> configGroups = version.getConfigGroups();
        if (CollectionUtils.isEmpty(configGroups)) {
            LOGGER.warn("#copyConfigItemsFromVersion found no groups under version");
            return;
        }

        Date now = new Date();
        for (ConfigGroup configGroup : configGroups) {
            // save config group
            configGroup.setId(null);
            configGroup.setVersionId(destVersionId);
            configGroup.setCreateTime(now);
            configGroup.setUpdateTime(null);
            configGroupService.saveEntity(configGroup);

            copyConfigItemsFromGroup(configGroup, configGroup.getId());
        }

    }

    /**
     * 从一个env复制所有的配置项到另一个已存在的env里.
     * 
     * @param srcEnvId
     *            srcEnvId
     * @param destEnvId
     *            destEnvId
     */
    @Override
    public void copyConfigItemsFromEnv(Long srcEnvId, Long destEnvId) {

        List<Version> srcVersionList = versionService
                .findVersionListByEnvId(srcEnvId);

        Date now = new Date();

        for (Version version : srcVersionList) {
            Long srcVersionId = version.getId();
            version.setId(null);
            version.setUpdateTime(now);
            // TODO 待定
            version.setCheckSumDate(now);
            version.setEnvironmentId(destEnvId);

            Long destVersionId = versionService.saveEntity(version);
            copyConfigItemsFromVersion(srcVersionId, destVersionId);
        }

    }

}
