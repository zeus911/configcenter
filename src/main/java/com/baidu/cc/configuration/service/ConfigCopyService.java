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
import com.baidu.cc.configuration.bo.Version;

/**
 * 配置项复制功能 暂不支持从工程到工程的复制，工程之间的配置差异比较大，复制意义不大.
 * 
 * @author zhenhui
 */
public interface ConfigCopyService {

    /**
     * 从一个group复制所有的配置项到另一个已有的group里.
     * 
     * @param srcGroupId
     *            源GroupId
     * @param destGroupId
     *            目标GroupId
     */
    public void copyConfigItemsFromGroup(Long srcGroupId, Long destGroupId);

    /**
     * 从一个version复制所有的配置项到另一个存在的version里.
     * 
     * @param srcVersionId
     *            源versionId
     * @param destVersionId
     *            目标versionId
     */
    public void copyConfigItemsFromVersion(Long srcVersionId, Long destVersionId);

    /**
     * 从一个env复制所有的配置项到另一个已存在的env里.
     * 
     * @param srcEnvId
     *            srcEnvId
     * @param destEnvId
     *            destEnvId
     */
    public void copyConfigItemsFromEnv(Long srcEnvId, Long destEnvId);

    /**
     * 从一个version复制所有的配置项到另一个存在的version里.
     * 
     * @param version
     *            需要复制的版本对象
     * @param destVersionId
     *            目录version id
     */
    void copyConfigItemsFromVersion(Version version, Long destVersionId);

    /**
     * 从一个group复制所有的配置项到另一个已有的group里.
     * 
     * @param configGroup
     *            group对象
     * @param destGroupId
     *            目标GroupId
     */
    void copyConfigItemsFromGroup(ConfigGroup configGroup, Long destGroupId);
}
