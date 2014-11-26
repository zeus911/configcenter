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
package com.baidu.rigel.demo.action;

import static org.hamcrest.CoreMatchers.is;

import com.baidu.rigel.test.strut2.AbstractStruts2SpringContextTests;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * 这个主要演示如何测试和http相关的action，可以直接利用spring提供的mock包,
 * 这里继承自AbstractStruts2SpringContextTests，里面已经有基本的http mock类。.
 * 
 * @author zhangjunjun
 */
@ContextConfiguration(locations = { "/com/baidu/rigel/demo/conf/applicationContext-demo.xml" }, inheritLocations = false)
public class UserActionTestDemo extends AbstractStruts2SpringContextTests {

    /** The user action. */
    @Resource(name = "userAction")
    private UserAction userAction;

    /**
     * Test register user.
     */
    @Test
    public void testRegisterUser() {
        request.setParameter("user.name", "rigel");
        request.setParameter("user.descs", "crm team");

        String isSuccess = userAction.registerUser();
        Assert.assertThat("注册应该成功", isSuccess, is("success"));
    }

}
