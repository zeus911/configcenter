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

import com.baidu.rigel.demo.bo.User;
import com.baidu.rigel.demo.dao.UserDao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * The Class UserServiceImpl.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    /** The black list service. */
    @Resource(name = "blackListService")
    private BlackListService blackListService;

    /** The user dao. */
    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     * add user to db if not in blacklist
     * 
     * @param user
     *            the user
     * @return true if not in blacklist
     */
    public boolean registerUser(User user) {
        boolean inBlackList = blackListService.isInBlackList(user);
        if (!inBlackList) {
            userDao.insertUser(user);
            return true;
        }
        return false;
    }

}
