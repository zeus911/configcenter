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

import static org.junit.Assert.assertTrue;

import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.dao.EnvironmentDao;
import com.baidu.cc.configuration.service.impl.EnvironmentServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.junit.Test;

/**
 * Created by zhenhui on 14-2-27.
 * 
 * @author zhenhui
 */
public class EnvironmentServiceTest {

    /** 要测试的类. */
    @Tested
    private EnvironmentServiceImpl environmentService;

    /** mock并自动注入到@Tested声明的类. */
    @Injectable
    private EnvironmentDao environmentDao;

    /** mock并自动注入到@Tested声明的类. */
    @Injectable
    private VersionService versionService;

    /**
     * testDeleteEnvCascadeByProjectId.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDeleteEnvCascadeByProjectId() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(2L);
        environmentService.deleteEnvCascadeByIds(ids);
    }

    /**
     * 测试几个find方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testFind() throws Exception {
        new NonStrictExpectations() {
            {
                Environment environment = new Environment();
                environment.setId(100L);
                List<Environment> envList = new ArrayList<Environment>();
                envList.add(environment);
                environmentDao.findByProjectId(anyLong);
                returns(envList);
                environmentDao.findByProjectIds((List<Long>) any);
                returns(envList);
            }
        };

        assertTrue(environmentService.findByProjectId(1L).get(0).getId() == 100L);
        assertTrue(environmentService.findByProjectIds(null).size() == 0);
        assertTrue(environmentService.findByProjectIds(Arrays.asList(1L, 2L))
                .get(0).getId() == 100L);
    }
}
