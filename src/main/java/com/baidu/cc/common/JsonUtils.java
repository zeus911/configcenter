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
package com.baidu.cc.common;

import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;

/**
 * Json utility class by using {@link Gson}.
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public class JsonUtils {

    /**
     * convert map to json object.
     * 
     * @param map
     *            key is string value will convert to string
     * @return {@link JsonObject}
     */
    public static JsonObject fromSimpleMap(Map<String, Object> map) {
        JsonObject jo = new JsonObject();
        if (MapUtils.isEmpty(map)) {
            return jo;
        }

        Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Object> entry = iter.next();
            jo.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return jo;
    }
}
