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

import static junit.framework.Assert.assertTrue;

import com.baidu.cc.configuration.dao.ConfigGroupDao;
import com.baidu.cc.configuration.dao.ConfigItemDao;
import com.baidu.cc.configuration.service.impl.ConfigGroupServiceImpl;

import java.util.Arrays;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

/**
 * Created by zhenhui on 14-5-30.
 * 
 * @author zhenhui
 */
public class ConfigGroupServiceTest {

    /** 要测试的类. */
    @Tested
    private ConfigGroupServiceImpl configGroupService;

    /**
     * Dao 'configGroupDao' reference.
     */
    @Injectable
    private ConfigGroupDao configGroupDao;

    /** ConfigItemService. */
    @Injectable
    private ConfigItemService configItemService;

    /** ConfigItemDao. */
    @Injectable
    private ConfigItemDao configItemDao;

    /**
     * 测试find方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testFind() throws Exception {
        assertTrue(CollectionUtils.isEmpty(configGroupService
                .findConfigGroupIdsByVersionIds(null)));
        assertTrue(CollectionUtils.isEmpty(configGroupService
                .findConfigGroupIdsByVersionIds(Arrays.asList(1L, 2L))));

        assertTrue(CollectionUtils.isEmpty(configGroupService
                .findByVersionIds(Arrays.asList(1L, 2L))));
        assertTrue(CollectionUtils.isEmpty(configGroupService
                .findByVersionIds(null)));
    }

    /**
     * testDeleteConfigGroupCascadeByIds.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDeleteConfigGroupCascadeByIds() throws Exception {
        new NonStrictExpectations() {
            {
                configItemService.findConfigItemIdsByConfigGroupIds(Arrays
                        .asList(1L, 2L));
                returns(Arrays.asList(1L, 2L));
            }
        };
        configGroupService.deleteConfigGroupCascadeByIds(Arrays.asList(1L, 2L));
    }

}
