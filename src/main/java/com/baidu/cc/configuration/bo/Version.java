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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This bo is map to table : version Table description : null.
 * 
 * @author BJF
 */
public class Version extends VersionBase {
    /** serial Version UID. */
    private static final long serialVersionUID = -1L;

    /**
     * config items under this version.<br>
     * Note: 如果引用的值已经被替换真正的值时，需要把<code>resolved</code>设置成false.
     */
    private List<ConfigItem> configItems;

    /** 该版本下所有的分组信息. */
    private List<ConfigGroup> configGroups;

    /**
     * Gets the config groups.
     * 
     * @return the configGroups
     */
    public List<ConfigGroup> getConfigGroups() {
        return configGroups;
    }

    /**
     * Sets the config groups.
     * 
     * @param configGroups
     *            the configGroups to set
     */
    public void setConfigGroups(List<ConfigGroup> configGroups) {
        this.configGroups = configGroups;
    }

    /**
     * get config items under this version as map object.<br>
     * ref value will be ingore
     * 
     * @param ignoreRef
     *            if ingore reference value item
     * @return map instance.
     */
    public Map<String, String> getConfigItemAsMap(boolean ignoreRef) {
        if (CollectionUtils.isEmpty(configItems)) {
            return Collections.emptyMap();
        }

        Map<String, String> ret = new HashMap<String, String>();
        for (ConfigItem item : configItems) {
            if (item.isRef() && ignoreRef) {
                continue;
            }
            ret.put(item.getName(), item.getVal());
        }

        return ret;
    }

    /**
     * get config items under this version.
     * 
     * @return {@link ConfigItem} list
     */
    public List<ConfigItem> getConfigItems() {
        return configItems;
    }

    /**
     * set config items under this version.
     * 
     * @param configItems
     *            {@link ConfigItem} list
     */
    public void setConfigItems(final List<ConfigItem> configItems) {
        this.configItems = configItems;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}