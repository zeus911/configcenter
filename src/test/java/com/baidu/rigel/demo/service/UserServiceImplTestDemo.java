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
package com.baidu.rigel.demo.service;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import com.baidu.rigel.demo.bo.User;
import com.baidu.rigel.test.BaseUnitTest;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * 这个例子主要如何mock,注册服务需要使用到黑名单服务，如果这个服务返回的值不确定或者想自己构造数据，只需要写一个配置文件，里面的bean
 * id或者name和被mock相同
 * ,spring的机制即会把新的对象覆盖之前的。对于这个例子来说，mock的对象将覆盖BlackListServiceImpl中的实现。.
 * 
 * @author zhangjunjun
 */
@ContextConfiguration(locations = {
    "/com/baidu/rigel/demo/conf/applicationContext-demo.xml",
    "/com/baidu/rigel/demo/service/applicationContext-mock.xml" }, inheritLocations = false)
public class UserServiceImplTestDemo extends BaseUnitTest {

    /** The user service. */
    @Resource(name = "userService")
    private UserService userService;

    /** The black list service. */
    @Resource(name = "blackListService")
    private BlackListService blackListService;

    /**
     * Test register user.
     */
    @Test
    // @Rollback(false) 如果不想回滚，加上这句，默认情况自动回滚
    public void testRegisterUser() {
        User user = new User();
        user.setName("Liu");
        user.setDescs("Actor");

        when(blackListService.isInBlackList(user)).thenReturn(false);

        boolean isSuccess = userService.registerUser(user);

        Assert.assertThat("Here should return true", isSuccess, is(true));
    }

}
