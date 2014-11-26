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
package com.baidu.cc.configuration.dao;

import com.baidu.bjf.dao.DaoMapper;
import com.baidu.bjf.orm.mybatis.SqlMapper;
import com.baidu.cc.configuration.bo.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Mapper interface class for model com.baidu.cc.configuration.bo.UserBase
 * 
 * @author BJF
 */
@SqlMapper
public interface UserMapper extends DaoMapper<User, Long> {

    /**
     * To find {@link User} list by user name filter condition.
     * 
     * @param name
     *            user name
     * @return {@link User} list
     */
    List<User> findByNameFilter(String name);

    /**
     * To find {@link User} list by user name and password.
     * 
     * @param params
     *            user key is 'name' password key is 'password'
     * @return {@link User} list
     */
    List<User> findByNameAndApiPassword(Map<String, String> params);

    /**
     * 根据name和password查询用户信息.
     * 
     * @param name
     *            name
     * @param password
     *            password
     * @return User
     */
    User findByNameAndPassword(@Param("name") String name,
            @Param("password") String password);

    /**
     * Get {@link User} by name.
     * 
     * @param name
     *            user name
     * @return {@link User}
     */
    User getByName(String name);
}