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

import java.util.Map;

/**
 * This bo is map to table : config_item Table description : null.
 * 
 * @author BJF
 */
public class ConfigItem extends ConfigItemBase {
    /** serial Version UID. */
    private static final long serialVersionUID = -1L;

    /**
     * if ref item is resolved value. only works if item is ref item.
     */
    private boolean resolved;

    /**
     * get ref item is resolved or not.
     * 
     * @return true if ref item is resolved.
     */
    public boolean isResolved() {
        return resolved && isRef();
    }

    /**
     * set ref time is resolved.
     * 
     * @param resolved
     *            true if ref item is resolved.
     */
    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    /**
     * Check is config item value is referred from other config item value.
     * 
     * @return true if value is referred
     */
    public boolean isRef() {
        Boolean ref = getRef();
        if (ref == null) {
            return false;
        }
        return ref;
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
}