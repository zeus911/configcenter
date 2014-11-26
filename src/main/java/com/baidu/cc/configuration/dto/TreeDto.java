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
package com.baidu.cc.configuration.dto;

import java.io.Serializable;

/**
 * The Class TreeDto.
 * 
 * @author huangxiaofeng
 * @ClassName: 类TreeDto.java
 * @Description: 前台显示树形菜单的数据结构
 * @date 2014-3-21 上午11:45:17
 */
public class TreeDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5936868026624729163L;

    /** 根节点url 列出所有工程. */
    public static final String ROOT_URL = "/project/listProject.action";

    /** 工程节点url前缀 列出所有环境. */
    public static final String TYPE_PROJECT_URL = "/env/listEnv.action?reqParam.projectId=";

    /** 环境节点url前缀 列出所有版本. */
    public static final String TYPE_ENV_URL = "/version/listVersion.action?reqParam.environmentId=";

    /** 版本节点url前缀 列出所有分组. */
    public static final String TYPE_VER_URL = "/configGroup/listConfigGroup.action?reqParam.versionId=";

    /** 分组节点url前缀 列出所有. */
    public static final String TYPE_GROUP_URL = "/configItem/listConfigItem.action?reqParam.groupId=";

    /** 工程节点id前缀. */
    public static final String TYPE_PROJECT_ID_PREFIX = "p";

    /** 环境节点id前缀. */
    public static final String TYPE_ENV_ID_PREFIX = "e";

    /** 版本节点id前缀. */
    public static final String TYPE_VER_ID_PREFIX = "v";

    /** 分组节点id前缀. */
    public static final String TYPE_GROUP_ID_PREFIX = "g";

    /** 节点id. */
    private String id;

    /** 父节点id. */
    private String pid;

    /** 节点类型. */
    private String type;

    /** 节点超链接. */
    private String url;

    /** 节点名称. */
    private String name;

    /** 节点说明. */
    private String title;

    /** 节点图标. */
    private String icon;

    /**
     * Instantiates a new tree dto.
     */
    public TreeDto() {

    }

    /**
     * Instantiates a new tree dto.
     * 
     * @param id
     *            the id
     * @param pid
     *            the pid
     * @param url
     *            the url
     * @param name
     *            the name
     */
    public TreeDto(String id, String pid, String url, String name) {
        this.id = id;
        this.pid = pid;
        this.url = url;
        this.name = name;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the pid.
     * 
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * Sets the pid.
     * 
     * @param pid
     *            the new pid
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the url.
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     * 
     * @param url
     *            the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title
     *            the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the icon.
     * 
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the icon.
     * 
     * @param icon
     *            the new icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

}
