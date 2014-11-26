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

import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.service.impl.ConfigCopyServiceImpl;

import java.util.ArrayList;
import java.util.List;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.junit.Test;

/**
 * Created by zhenhui on 14-3-6.
 * 
 * @author zhenhui
 */
public class ConfigCopyServiceTest {

    /** 要测试的类. */
    @Tested
    private ConfigCopyServiceImpl configCopyService;

    /** mock并自动注入到@Tested声明的类. */
    @Injectable
    private ConfigItemService configItemService;

    /** mock并自动注入到@Tested声明的类. */
    @Injectable
    private ConfigGroupService configGroupService;

    /** mock并自动注入到@Tested声明的类. */
    @Injectable
    private VersionService versionService;

    /** mock并自动注入到@Tested声明的类. */
    @Injectable
    private EnvironmentService environmentService;

    /** mock并自动注入到@Tested声明的类. */
    @Injectable
    private ProjectService projectService;

    /**
     * 测试configCopyService.copyConfigItemsFromGroup，从一个group复制到另一个group
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCopyConfigItemsFromGroup() throws Exception {

        final Long srcGroupId = 1L;
        final Long destGroupId = 2L;
        configCopyService.copyConfigItemsFromGroup(srcGroupId, destGroupId);
        new NonStrictExpectations() {
            {
                List<ConfigItem> configItems = new ArrayList<ConfigItem>();
                ConfigItem configItem = new ConfigItem();
                configItem.setGroupId(srcGroupId);
                configItems.add(configItem);
                configItemService.findByGroupId(srcGroupId, anyBoolean);
                result = configItems;
                configGroupService.findById(destGroupId);
                result = new ConfigGroup();
            }
        };

        configCopyService.copyConfigItemsFromGroup(srcGroupId, destGroupId);

    }

    /**
     * 测试从一个version完全复制.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCopyConfigItemsFromVersion() throws Exception {

        final Long srcVersionId = 1L;
        final Long destVersionId = 2L;
        configCopyService.copyConfigItemsFromVersion(1L, 1L);

        new NonStrictExpectations() {
            {

                ConfigGroup configGroup = new ConfigGroup();
                configGroup.setVersionId(destVersionId);
                List<ConfigGroup> configGroups = new ArrayList<ConfigGroup>();
                configGroups.add(configGroup);
                configGroupService.findByVersionId(srcVersionId);
                result = configGroups;
            }
        };

        new NonStrictExpectations(configCopyService) {
            {
                configCopyService.copyConfigItemsFromGroup(anyLong, anyLong);
            }
        };

        configCopyService.copyConfigItemsFromVersion(srcVersionId,
                destVersionId);

    }

    /**
     * 测试从env复制全部配置功能.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCopyConfigItemsFromEnv() throws Exception {
        final Long srcEnvId = 1L;
        final Long destEnvId = 2L;

        new NonStrictExpectations() {
            {
                Version version = new Version();
                version.setId(srcEnvId);
                List<Version> versions = new ArrayList<Version>();
                versions.add(version);

                versionService.findVersionListByEnvId(srcEnvId);
                result = versions;
            }
        };

        configCopyService.copyConfigItemsFromEnv(srcEnvId, destEnvId);

    }

}
