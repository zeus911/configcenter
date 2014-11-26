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

import com.baidu.cc.configuration.bo.User;
import com.baidu.rigel.platform.util.Security;
import com.baidu.rigel.test.BaseUnitTest;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for {@link UserService}.
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public class UserServiceTest extends BaseUnitTest {

    /**
     * UserService instance.
     */
    @Resource(name = "userService")
    private UserService userService;

    /**
     * test api login method by login name and password.
     */
    @Test
    public void testApiLoginAuthenticate() {
        // insert a new user
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        User user = new User();
        user.setName(name);
        user.setApiPassword(password);
        user.setCreateTime(new Date());
        byte st = 1;
        user.setStatus(st);
        userService.saveEntity(user);

        // do authenticate
        boolean success = userService.authenticateApiLogin(name, password);
        Assert.assertTrue(success);

    }

    /**
     * Test find by name and password.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testFindByNameAndPassword() throws Exception {
        String name = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        User user = new User();
        user.setName(name);
        user.setApiPassword(Security.MD5Encode(password));
        user.setPassword(Security.MD5Encode(password));
        user.setCreateTime(new Date());
        byte st = 1;
        user.setStatus(st);
        userService.saveEntity(user);

        // do authenticate
        User u = userService.findByNameAndPassword(name, password);
        Assert.assertEquals(u.getName(), name);

        Assert.assertEquals(
                userService.findByNameFilter(name).get(0).getName(), name);

        userService.delete(u);

    }
}
