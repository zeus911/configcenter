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
package com.baidu.cc.web.rpc;

import com.baidu.bjf.remoting.mcpack.annotation.McpackRpcService;
import com.baidu.cc.common.SysUtils;
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
import com.baidu.cc.interfaces.ConfigServerService;
import com.baidu.cc.interfaces.ExtConfigServerService;
import com.baidu.rigel.logclient.sdk.UserLogger;
import com.baidu.rigel.platform.util.Security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

/**
 * A portal {@link ConfigServerService} implementor.
 * 
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
@Service
@McpackRpcService(serviceInterface = ExtConfigServerService.class)
public class ConfigServerServiceImpl implements ExtConfigServerService {

    /** Logger for this class. */
    private static final Logger LOGGER = Logger
            .getLogger(ConfigServerServiceImpl.class);

    /**
     * UserLogger srcId in logclient
     */
    private static final long USER_LOGGER_SRC_ID = Long.parseLong(SysUtils
            .getConstants("userLogSrcId"));

    /**
     * UserLogger key for project id in extensions
     */
    private static final String KEY_PROJECT_ID = "projectId";

    /**
     * UserLogger key for project name in extensions
     */
    private static final String KEY_PROJECT_NAME = "projectName";

    /** passowd encrypte action class. */
    StandardPBEStringEncryptor encryptor;

    /**
     * {@link UserService} instance.
     */
    @Resource(name = "userService")
    private UserService userService;

    /**
     * {@link EnvironmentService} instance.
     */
    @Resource(name = "environmentService")
    private EnvironmentService environmentService;

    /**
     * {@link ProjectService} instance.
     */
    @Resource(name = "projectService")
    private ProjectService projectService;

    /**
     * {@link ConfigItemService} instance.
     */
    @Resource(name = "configItemService")
    private ConfigItemService configItemService;

    /**
     * {@link VersionService} instance.
     */
    @Resource(name = "versionService")
    private VersionService versionService;

    /**
     * {@link AccessSettingService} instance.
     */
    @Resource(name = "accessSettingService")
    private AccessSettingService accessSettingService;

    /**
     * {@link ConfigGroupService} instance.
     */
    @Resource(name = "configGroupService")
    private ConfigGroupService configGroupService;

    /**
     * default constructor. create {@link StandardPBEStringEncryptor} instance.
     */
    public ConfigServerServiceImpl() {
        encryptor = new StandardPBEStringEncryptor();
        ;
        encryptor.setPassword(ExtConfigServerService.class.getSimpleName());
    }

    /**
     * To authenticate by user and password. if authentication failed throw<br>
     * {@link IllegalAccessError} exception.
     * 
     * @param user
     *            user name
     * @param password
     *            password
     * @return {@link User} object
     * @throws IllegalAccessError
     *             if authentication failed.
     */
    protected User authenticate(String user, String password)
            throws IllegalAccessError {

        User u = userService.getByName(user);
        if (u == null) {
            throw new IllegalAccessError("User '" + user + " not exist.");
        }

        String passwordToCheck = u.getApiPassword();
        if (passwordToCheck == null) {
            if (password == null) {
                return u;
            } else {
                throw new IllegalAccessError("User '" + user
                        + "' authenticate failed.");
            }
        }

        String plain = encryptor.decrypt(password);
        String md5 = Security.MD5Encode(plain);
        boolean result = u.getApiPassword().equals(md5);

        if (!result) {
            throw new IllegalAccessError("User '" + user
                    + "' authenticate failed.");
        }

        return u;
    }

    /**
     * To authorize by user and env id.
     * 
     * @param u
     *            {@link User} object
     * @param envId
     *            environment id
     */
    protected void authorizeEnv(User u, long envId) {

        boolean authorization = accessSettingService.checkAuth(u.getId(),
                AccessSettingService.Auth_TYPE_ENV, envId);
        if (!authorization) {
            String msg = "No access allowed for user '" + u.getName()
                    + "' to envid=" + envId;
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(msg);
            }
            throw new IllegalAccessError(msg);
        }
    }

    /**
     * To authorize by user and project id.
     * 
     * @param u
     *            {@link User} object
     * @param projectId
     *            project id
     */
    protected void authorizeProject(User u, long projectId) {

        boolean authorization = accessSettingService.checkAuth(u.getId(),
                AccessSettingService.Auth_TYPE_PROJECT, projectId);
        if (!authorization) {
            String msg = "No access allowed for user '" + u.getName()
                    + "' to projectId=" + projectId;
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(msg);
            }
            throw new IllegalAccessError(msg);
        }
    }

    /**
     * Use UserLogger to log project id, projectName and interfaceName
     * 
     * @param interfaceName
     * @param projectId
     * @param projectName
     */
    private void logExtendedInfo(String interfaceName, Long projectId,
            String projectName) {
        String actualProjectName = "";
        if (projectName == null) {
            Project project = projectService.findById(projectId);
            if (project != null) {
                actualProjectName = project.getName();
            }
        } else {
            actualProjectName = projectName;
        }

        Map<String, Object> extensions = new HashMap<String, Object>();
        extensions.put(KEY_PROJECT_ID, projectId);
        extensions.put(KEY_PROJECT_NAME, actualProjectName);
        UserLogger.log(USER_LOGGER_SRC_ID, interfaceName, extensions);
    }

    /**
     * 根据版本号获取所有配置项内容 如果用户名与密码验证失败，按抛出 IllegalAccessError异常.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param version
     *            版本号
     * @return Map<String,String> 返回所有的值类型都是String.包括tag版本标识
     */
    @Override
    public Map<String, String> getConfigItems(String user, String password,
            Long version) {
        User u = authenticate(user, password);

        Version v = versionService.findById(version);
        if (v == null) {
            throw new IllegalAccessError("No version id '" + version
                    + "'  found.");
        }

        Long projectId = v.getProjectId();
        authorizeProject(u, projectId);

        Map<String, String> items = getConfigItems(version);
        // add tag
        items.put(TAG_KEY, v.getCheckSum());

        logExtendedInfo("configcenter_getConfigItems", projectId, null);

        return items;
    }

    /**
     * 根据版本号获取所有配置项内容.
     * 
     * @param version
     *            版本号
     * @return 返回所有的值类型都是String.包括tag版本标识
     */
    private Map<String, String> getConfigItems(Long version) {

        List<ConfigItem> items = configItemService.findByVersionId(version,
                true);

        Map<String, String> ret = new HashMap<String, String>();
        if (CollectionUtils.isEmpty(items)) {
            return ret;
        }

        for (ConfigItem configItem : items) {
            ret.put(configItem.getName(), configItem.getVal());
        }

        return ret;
    }

    /**
     * 根据运行环境id获取最新一份版本的配置项内容 如果用户名与密码验证失败，按抛出 IllegalAccessError异常.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param envId
     *            运行环境id
     * @return Map<String,String> 返回所有的值类型都是String。 包括tag版本标识
     */
    @Override
    public Map<String, String> getLastestConfigItems(String user,
            String password, Long envId) {
        User u = authenticate(user, password);
        // should check authorization here by project
        authorizeEnv(u, envId);

        // 根据envId获取最新的version id(version id最大的内容)
        Version version = getLastestConfigVersion(envId);
        if (version == null) {
            throw new IllegalAccessError("No version under environemt id: "
                    + envId);
        }

        Map<String, String> items = getConfigItems(version.getId());
        // add tag
        items.put(TAG_KEY, version.getCheckSum());

        Long projectId = version.getProjectId();
        logExtendedInfo("configcenter_getLastestConfigItems", projectId, null);

        return items;
    }

    /**
     * 根据envId获取最新的version id(version id最大的内容).
     * 
     * @param envId
     *            运行环境id
     * @return 最大的版本号或者为null表示不存在版本号
     */
    private Version getLastestConfigVersion(Long envId) {

        // 根据envId获取最新的version id(version id最大的内容)
        Version version = versionService.findLastestByEnvId(envId);
        return version;
    }

    /**
     * 根据运行环境id获取最新一份版本的版本号 如果用户名与密码验证失败，按抛出 IllegalAccessError异常.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param envId
     *            运行环境id
     * @return 版本号
     */
    @Override
    public Long getLastestConfigVersion(String user, String password, Long envId) {
        User u = authenticate(user, password);

        // should check authorization here by project
        authorizeEnv(u, envId);

        Version version = getLastestConfigVersion(envId);
        if (version == null) {
            throw new IllegalAccessError("No version under environemt id: "
                    + envId);
        }

        Long projectId = version.getProjectId();
        logExtendedInfo("configcenter_getLastestConfigVersion", projectId, null);

        return version.getId();
    }

    /**
     * 根据版本号、key获得某项配置内容 如果用户名与密码验证失败，按抛出 IllegalAccessError异常.
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
    @Override
    public String getConfigItemValue(String user, String password,
            Long version, String key) {
        User u = authenticate(user, password);

        Version v = versionService.findById(version);
        if (v == null) {
            throw new IllegalAccessError("No version id '" + version
                    + "' found. ");
        }
        authorizeProject(u, v.getProjectId());

        ConfigItem item = configItemService.findByVersionIdAndName(version,
                key, true);

        Long projectId = v.getProjectId();
        logExtendedInfo("configcenter_getConfigItemValue", projectId, null);

        if (item == null) {
            return null;
        }

        return item.getVal();
    }

    /**
     * 校验版本的tag号是否修改 如果用户名与密码验证失败，按抛出 IllegalAccessError异常.
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
    @Override
    public boolean checkVersionTag(String user, String password, Long version,
            String tag) {
        User u = authenticate(user, password);

        if (StringUtils.isBlank(tag)) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("To check tag value is blank will always return false.");
            }
            return false;
        }

        Version v = versionService.findById(version);
        if (v == null) {
            throw new IllegalAccessError("No version id: '" + version
                    + "' found.");
        }
        Long projectId = v.getProjectId();
        authorizeProject(u, projectId);

        logExtendedInfo("configcenter_checkVersionTag", projectId, null);

        return tag.equals(v.getCheckSum());
    }

    /**
     * import configuration items to the configuration center server.
     * 
     * @param user
     *            user name
     * @param password
     *            password
     * @param version
     *            the target version id to import
     * @param configItems
     *            imported configuration items
     */
    @Override
    public void importConfigItems(String user, String password, Long version,
            Map<String, String> configItems) {

        if (MapUtils.isEmpty(configItems)) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("import a empty map will be ingored");
            }
            return;
        }

        authenticate(user, password);

        // check version exist
        Version v = versionService.findById(version);
        if (v == null) {
            throw new IllegalAccessError(
                    "import config failed. No version id '" + version
                            + "' found.");
        }

        List<ConfigGroup> groups = configGroupService.findByVersionId(version);
        if (CollectionUtils.isNotEmpty(groups)) {
            throw new IllegalAccessError("import config failed. version id '"
                    + version + "' is not empty.");
        }

        ConfigGroup group = ConfigGroup.newGroup();
        group.setVersionId(v.getId());
        configGroupService.saveEntity(group);

        // get configuration group id
        List<ConfigItem> items = group.newItems(configItems);
        for (ConfigItem configItem : items) {
            configItemService.saveEntity(configItem);
        }

        Long projectId = v.getProjectId();
        logExtendedInfo("configcenter_importConfigItems", projectId, null);
    }

    /**
     * 根据工程名称与运行环境名称获取最新一份版本的版本号.
     * 
     * @param user
     *            用户名
     * @param password
     *            密码
     * @param projectName
     *            工程名称
     * @param envName
     *            运行环境名称
     * @return 版本号
     */
    @Override
    public Long getLastestConfigVersion(String user, String password,
            String projectName, String envName) {
        User u = authenticate(user, password);

        // check project name
        Project project = projectService.findByName(projectName);
        if (project == null) {
            throw new IllegalAccessError("No project name found'" + projectName
                    + "'");
        }

        Long projectId = project.getId();
        authorizeProject(u, projectId);

        Environment environment;
        environment = environmentService.findByNameAndProjectId(envName,
                project.getId());

        if (environment == null) {
            throw new IllegalAccessError("No environment name found'" + envName
                    + "' under project name '" + projectName + "'");
        }
        long envId = environment.getId();
        Version version = getLastestConfigVersion(envId);
        if (version == null) {
            throw new IllegalAccessError("No version under environemt id:"
                    + envId);
        }

        logExtendedInfo("configcenter_getLastestConfigVersion", projectId,
                projectName);

        return version.getId();
    }

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
    @Override
    public Long getVersionId(String user, String password, String versionName) {
        authenticate(user, password);

        Version version = versionService.findVersionAndConfigItems(versionName);
        if (version == null) {
            return null;
        }

        Long projectId = version.getProjectId();
        logExtendedInfo("configcenter_getVersionId", projectId, null);

        return version.getId();
    }
}
