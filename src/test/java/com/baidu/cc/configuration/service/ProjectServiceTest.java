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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.bo.Project;
import com.baidu.cc.configuration.dao.ProjectDao;
import com.baidu.cc.configuration.dao.ProjectMapper;
import com.baidu.cc.configuration.service.impl.ProjectServiceImpl;

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
public class ProjectServiceTest {

    /** 测试ProjectServiceImpl. */
    @Tested
    private ProjectServiceImpl projectService;

    /** mock掉EnvironmentService并自动注入. */
    @Injectable
    private EnvironmentService environmentService;

    /** mock掉ProjectDao并自动注入. */
    @Injectable
    private ProjectDao projectDao;

    /** mock掉ProjectDao并自动注入. */
    @Injectable
    private AccessSettingService accessSettingService;

    /** mock掉ProjectMapper并自动注入. */
    @Injectable
    private ProjectMapper projectMapper;

    /**
     * 测试级联删除方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void deleteProjectCascadeById() throws Exception {
        final Long projectId = 1111L;
        new NonStrictExpectations() {
            {
                Environment environment = new Environment();
                environment.setId(100L);
                environment.setProjectId(projectId);
                List<Environment> envList = new ArrayList<Environment>();
                envList.add(environment);
                environmentService.findByProjectId(projectId);
                returns(envList);
            }
        };

        projectService.deleteProjectCascadeById(1L, projectId);
    }

    /**
     * 测试几个find方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testFind() throws Exception {
        assertNull(projectService.findByName(""));
        assertTrue(projectService.findByIds(null).size() == 0);
        assertTrue(projectService.findByIds(Arrays.asList(1L, 2L)).size() == 0);
    }

    /**
     * Test insert project.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testInsertProject() throws Exception {
        Long userId = 1L;
        Project project = new Project();
        project.setId(1L);
        assertTrue(projectService.insertProject(userId, project) == 1L);
    }
}