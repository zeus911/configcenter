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
package com.baidu.cc.configuration.bo;

import com.baidu.cc.common.JsonUtils;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This bo is map to table : config_group Table description : .
 * 
 * @author BJF
 */
public class ConfigGroup extends ConfigGroupBase {

    /** default group name. */
    public static final String DEFAULT_GROUP = "default_group";

    /** default group name. */
    public static final String DEFAULT_GROUP_MEMO = "default group";

    /** serial Version UID. */
    private static final long serialVersionUID = -1L;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /** 该下面所有的配置项. */
    private List<ConfigItem> configItems;

    /**
     * get configItems.
     * 
     * @return the configItems
     */
    public List<ConfigItem> getConfigItems() {
        return configItems;
    }

    /**
     * set configItems.
     * 
     * @param configItems
     *            the configItems to set
     */
    public void setConfigItems(List<ConfigItem> configItems) {
        this.configItems = configItems;
    }

    /**
     * Copy value to {@link JsonObject}.
     * 
     * @return json object
     */
    public JsonObject copyToJson() {
        Map<String, Object> map = toMap();
        // ingore date
        map.remove("createTime");
        map.remove("updateTime");
        return JsonUtils.fromSimpleMap(map);
    }

    /**
     * create a default group.
     * 
     * @return {@link ConfigGroup} instance.
     */
    public static ConfigGroup newGroup() {
        ConfigGroup group = new ConfigGroup();
        group.setName(DEFAULT_GROUP);
        group.setMemo(DEFAULT_GROUP_MEMO);
        group.setDefaultType(true);
        group.setCreateTime(new Date());

        return group;
    }

    /**
     * create batch {@link ConfigItem} instances by name and value.
     * 
     * @param configItem
     *            batch name and value
     * @return {@link ConfigItem} instances
     */
    public List<ConfigItem> newItems(Map<String, String> configItem) {
        if (MapUtils.isEmpty(configItem)) {
            return Collections.emptyList();
        }

        Date createDate = new Date();

        List<ConfigItem> ret = new ArrayList<ConfigItem>(configItem.size());

        Iterator<Entry<String, String>> iter = configItem.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            ConfigItem item = new ConfigItem();
            item.setName(entry.getKey());
            item.setVal(entry.getValue());
            item.setRef(false);
            item.setCreateTime(createDate);
            item.setGroupId(getId());
            item.setShareable(false);
            item.setVersionId(getVersionId());

            ret.add(item);
        }

        return ret;
    }
}
