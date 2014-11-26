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

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.dao.VersionDao;
import com.baidu.cc.configuration.dao.VersionMapper;
import com.baidu.cc.configuration.service.impl.VersionServiceImpl;

import java.util.Arrays;
import java.util.List;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.junit.Test;

/**
 * Created by zhenhui on 14-6-3.
 * 
 * @author zhenhui
 */
public class VersionServiceMockTest {

    /** VersionServiceImpl. */
    @Tested
    private VersionServiceImpl versionService;

    /** 自动mock并注入. */
    @Injectable
    private VersionDao versionDao;

    /** 自动mock并注入. */
    @Injectable
    private VersionMapper versionMapper;

    /** 自动mock并注入. */
    @Injectable
    private ConfigGroupService configGroupService;

    /**
     * 测试几个find方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testFind() throws Exception {

        assertTrue(versionService.findVersionListByEnvId(null).size() == 0);

        assertNull(versionService.findLastestByEnvId(null));

        assertNull(versionService.findVersionIdsByEnvIds(null));
        assertTrue(versionService.findVersionIdsByEnvIds(Arrays.asList(1L, 2L))
                .size() == 0);

        assertTrue(versionService.findVersionListByEnvIds(null).size() == 0);
        assertTrue(versionService
                .findVersionListByEnvIds(Arrays.asList(1L, 2L)).size() == 0);

        assertNull(versionService.findIdByName(null));
        final String testName = "testName";
        new NonStrictExpectations() {
            {
                Version version = new Version();
                version.setId(100L);
                versionMapper.findByName(testName);
                result = version;
            }
        };

        assertTrue(versionService.findIdByName(testName) == 100L);

    }

    /**
     * 测试级联删除方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDeleteVersionCascadeByIds() throws Exception {
        new NonStrictExpectations() {
            {
                configGroupService
                        .findConfigGroupIdsByVersionIds((List<Long>) any);
                returns(Arrays.asList(1L, 2L));

            }
        };
        versionService.deleteVersionCascadeByIds(Arrays.asList(1L, 2L));
    }

    /**
     * testPushChange.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testPushChange() throws Exception {
        new NonStrictExpectations() {
            {
                Version version = new Version();
                version.setId(100L);
                versionDao.findById(100L);
                result = version;
            }
        };

        versionService.pushChange(100L);
    }
}
