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
package com.baidu.cc.interfaces;

import java.io.Serializable;

/**
 * Configuration item changed value.
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public class ChangedConfigItem implements Serializable {

    /** serial Version UID. */
    private static final long serialVersionUID = 8760573498030245046L;

    /** configuration item key name. */
    private String key;

    /** configuration item old value. */
    private String oldValue;

    /** configuration item new value. */
    private String newValue;

    /**
     * get the key name.
     * 
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * get the old value.
     * 
     * @return the oldValue
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * get the new value.
     * 
     * @return the newValue
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the old value.
     * 
     * @param oldValue
     *            the oldValue to set
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * Sets the new value.
     * 
     * @param newValue
     *            the newValue to set
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

}
