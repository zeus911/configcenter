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

import com.baidu.bjf.service.GenericService;
import com.baidu.cc.configuration.bo.User;

import java.util.List;

/**
 * Service interface class for model com.baidu.cc.configuration.bo.UserBase
 * 
 * @author BJF
 */
public interface UserService extends GenericService<User, Long> {

    /**
     * To find user list by name filter.
     * 
     * @param name
     *            to filter
     * @return {@link User} list
     */
    List<User> findByNameFilter(String name);

    /**
     * Get {@link User} by name.
     * 
     * @param name
     *            user name
     * @return {@link User}
     */
    User getByName(String name);

    /**
     * To authenticate by user and api password.
     * 
     * @param name
     *            user name
     * @param password
     *            password
     * @return <code>true</code> if authenticate success.
     */
    boolean authenticateApiLogin(String name, String password);

    /**
     * 根据name和password查询用户信息，登录用.
     * 
     * @param name
     *            name
     * @param password
     *            password
     * @return User
     */
    User findByNameAndPassword(String name, String password);
}
