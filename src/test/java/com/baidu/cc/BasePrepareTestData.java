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
package com.baidu.cc;

import com.baidu.cc.configuration.bo.AccessSetting;
import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.bo.Project;
import com.baidu.cc.configuration.bo.User;
import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.service.AccessSettingService;
import com.baidu.cc.configuration.service.ConfigGroupService;
import com.baidu.cc.configuration.service.ConfigItemService;
import com.baidu.cc.configuration.service.EnvironmentService;
import com.baidu.cc.configuration.service.ProjectService;
import com.baidu.cc.configuration.service.UserService;
import com.baidu.cc.configuration.service.VersionService;
import com.baidu.cc.interfaces.ExtConfigServerService;
import com.baidu.rigel.platform.util.Security;
import com.baidu.rigel.test.BaseUnitTest;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Before;

/**
 * Abstract class for prepare test data.
 * 
 * @author xiemalin
 */
public abstract class BasePrepareTestData extends BaseUnitTest {

    /**
     * {@link ExtConfigServerService} instance.
     */
    protected UserService userService;

    /** The project service. */
    protected ProjectService projectService;

    /** The environment service. */
    protected EnvironmentService environmentService;

    /** The version service. */
    protected VersionService versionService;

    /** The config group service. */
    protected ConfigGroupService configGroupService;

    /** The config item service. */
    protected ConfigItemService configItemService;

    /** The access setting service. */
    protected AccessSettingService accessSettingService;

    /** The username. */
    protected String username = UUID.randomUUID().toString();

    /** The password. */
    protected String password = UUID.randomUUID().toString();

    /** The api password. */
    protected String apiPassword = UUID.randomUUID().toString();

    /** The encryptor. */
    StandardPBEStringEncryptor encryptor;

    /** The encrypted api password. */
    protected String encryptedApiPassword;

    /** The error api password. */
    protected String errorApiPassword;

    /** The project name. */
    protected String projectName = UUID.randomUUID().toString();

    /** The env name. */
    protected String envName = UUID.randomUUID().toString();

    /** The version name. */
    protected String versionName = UUID.randomUUID().toString();

    /** The version id. */
    protected long versionId;

    /** The env id. */
    protected long envId;

    /** The tag. */
    protected String tag = UUID.randomUUID().toString();

    /** The item name1. */
    protected String itemName1 = UUID.randomUUID().toString();

    /** The item value1. */
    protected String itemValue1 = UUID.randomUUID().toString();

    /** The item name2. */
    protected String itemName2 = UUID.randomUUID().toString();

    /** The item value2. */
    protected String itemValue2 = UUID.randomUUID().toString();

    /** The project id. */
    protected long projectId;

    /** The group name. */
    protected String groupName = UUID.randomUUID().toString();

    /** The user. */
    protected User user;

    /**
     * Instantiates a new base prepare test data.
     */
    public BasePrepareTestData() {
        encryptor = new StandardPBEStringEncryptor();
        ;
        encryptor.setPassword(ExtConfigServerService.class.getSimpleName());
        encryptedApiPassword = encryptor.encrypt(apiPassword);
        errorApiPassword = encryptor.encrypt("error password");
    }

    /**
     * Prepare data.
     */
    @Before
    public void prepareData() {
        prepareUserData();
        projectId = prepareProjectData();

        prepareUserAccessSetting();

        envId = prepareEnviromentData(projectId);

        versionId = prepareVersionData(versionName, envId, projectId);

        prepareGroupAndItem(versionId);
    }

    /**
     * Prepare user access setting.
     */
    private void prepareUserAccessSetting() {
        AccessSetting as = new AccessSetting();
        as.setUserId(user.getId());
        as.setRefId(projectId);
        byte type = Byte.valueOf(AccessSettingService.Auth_TYPE_PROJECT + "");
        as.setType(type);
        as.setCreateTime(new Date());

        accessSettingService.saveEntity(as);
    }

    /**
     * Prepare user data.
     */
    private void prepareUserData() {
        // add user
        user = new User();
        user.setName(username);
        user.setPassword(Security.MD5Encode(password));
        user.setApiPassword(Security.MD5Encode(apiPassword));
        user.setCreateTime(new Date());
        user.setStatus(User.STAUTS_OK);
        userService.saveEntity(user);
    }

    /**
     * Prepare project data.
     * 
     * @return the long
     */
    private long prepareProjectData() {
        Project project = new Project();
        project.setName(projectName);
        project.setCreateTime(new Date());

        projectService.saveEntity(project);

        return project.getId();
    }

    /**
     * Prepare enviroment data.
     * 
     * @param projectId
     *            the project id
     * @return the long
     */
    private long prepareEnviromentData(long projectId) {
        Environment env = new Environment();
        env.setName(envName);
        env.setCreateTime(new Date());
        env.setProjectId(projectId);

        environmentService.saveEntity(env);

        return env.getId();
    }

    /**
     * Prepare version data.
     * 
     * @param versionName
     *            the version name
     * @param envId
     *            the env id
     * @param projectId
     *            the project id
     * @return the long
     */
    protected long prepareVersionData(String versionName, long envId,
            long projectId) {
        Version v = new Version();

        v.setName(versionName);
        v.setProjectId(projectId);
        v.setEnvironmentId(envId);
        v.setCheckSum(tag);
        v.setCheckSumDate(new Date());

        versionService.saveEntity(v);
        return v.getId();

    }

    /**
     * Prepare group and item.
     * 
     * @param versionId
     *            the version id
     */
    private void prepareGroupAndItem(long versionId) {

        ConfigGroup group = new ConfigGroup();
        // add one group
        group.setName(groupName);
        group.setVersionId(versionId);
        group.setCreateTime(new Date());
        group.setDefaultType(true);

        configGroupService.saveEntity(group);

        Long groupId = group.getId();

        // add configuration item
        createConfigItem(itemName1, itemValue1, groupId, versionId);
        createConfigItem(itemName2, itemValue2, groupId, versionId);

    }

    /**
     * Creates the config item.
     * 
     * @param name
     *            the name
     * @param value
     *            the value
     * @param groupId
     *            the group id
     * @param versionId
     *            the version id
     */
    private void createConfigItem(String name, String value, Long groupId,
            long versionId) {
        ConfigItem item = new ConfigItem();
        item.setName(name);
        item.setGroupId(groupId);
        item.setCreateTime(new Date());
        item.setVal(value);
        item.setShareable(false);
        item.setRef(false);
        item.setVersionId(versionId);

        configItemService.saveEntity(item);
    }

    /**
     * Sets the user service.
     * 
     * @param userService
     *            the userService to set
     */
    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets the project service.
     * 
     * @param projectService
     *            the projectService to set
     */
    @Resource
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Sets the environment service.
     * 
     * @param environmentService
     *            the environmentService to set
     */
    @Resource
    public void setEnvironmentService(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    /**
     * Sets the version service.
     * 
     * @param versionService
     *            the versionService to set
     */
    @Resource
    public void setVersionService(VersionService versionService) {
        this.versionService = versionService;
    }

    /**
     * Sets the config group service.
     * 
     * @param configGroupService
     *            the configGroupService to set
     */
    @Resource
    public void setConfigGroupService(ConfigGroupService configGroupService) {
        this.configGroupService = configGroupService;
    }

    /**
     * Sets the config item service.
     * 
     * @param configItemService
     *            the configItemService to set
     */
    @Resource
    public void setConfigItemService(ConfigItemService configItemService) {
        this.configItemService = configItemService;
    }

    /**
     * Sets the access setting service.
     * 
     * @param accessSettingService
     *            the accessSettingService to set
     */
    @Resource
    public void setAccessSettingService(
            AccessSettingService accessSettingService) {
        this.accessSettingService = accessSettingService;
    }
}
