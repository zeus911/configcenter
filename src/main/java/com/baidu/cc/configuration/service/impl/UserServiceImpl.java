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
package com.baidu.cc.configuration.service.impl;

import com.baidu.bjf.dao.SqlMapDao;
import com.baidu.bjf.service.GenericSqlMapServiceImpl;
import com.baidu.cc.configuration.bo.User;
import com.baidu.cc.configuration.dao.UserDao;
import com.baidu.cc.configuration.dao.UserMapper;
import com.baidu.cc.configuration.service.UserService;
import com.baidu.rigel.platform.util.Security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implement class for model : com.baidu.cc.configuration.bo.UserBase
 * 
 * @author BJF
 */
@Service("userService")
public class UserServiceImpl extends GenericSqlMapServiceImpl<User, Long>
        implements UserService {
    /**
     * Dao 'userDao' reference.
     */
    @Resource(name = "userDao")
    private UserDao userDao;

    /** DaoMapper 'userMapper' reference. */
    @Autowired
    private UserMapper userMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<User, Long> getDao() {
        return userDao;
    }

    /**
     * To find user list by name filter.
     * 
     * @param name
     *            to filter
     * @return {@link User} list
     */
    public List<User> findByNameFilter(String name) {
        // add regex pattern
        String searchName = "%" + name + "%";
        return userMapper.findByNameFilter(searchName);

    }

    /**
     * To authenticate by user and password.
     * 
     * @param name
     *            user name
     * @param password
     *            password
     * @return <code>true</code> if authenticate success.
     */
    @Override
    public boolean authenticateApiLogin(String name, String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("password", password);
        List<User> users = userMapper.findByNameAndApiPassword(params);
        return !users.isEmpty();
    }

    /**
     * 根据name和password查询用户信息，登录用.
     * 
     * @param name
     *            name
     * @param password
     *            password
     * @return User
     */
    @Override
    public User findByNameAndPassword(String name, String password) {
        return userMapper.findByNameAndPassword(name,
                Security.MD5Encode(password));
    }

    /**
     * Get {@link User} by name.
     * 
     * @param name
     *            user name
     * @return {@link User}
     */
    @Override
    public User getByName(String name) {
        return userMapper.getByName(name);
    }
}