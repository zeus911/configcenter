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
package com.baidu.rigel.demo.dao;

import com.baidu.rigel.demo.bo.User;

import org.springframework.stereotype.Repository;

/**
 * The Class UserDaoImpl.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao {

    /**
     * insert user to db and return primary key
     *      
     * @param user
     *            the user
     * @return primary key
     */
    public long insertUser(User user) {
        /**
         * insert user sql
         */
        final String sql = "insert into user (name,descs) values(? ,? )";
        long id = (Long) insertAndGetKey(sql, user.getName(), user.getDescs());
        return id;
    }

    /**
     * implementation of updateUser
     * 
     * @param user
     *            the user
     */
    public void updateUser(User user) {
        /**
         * upate user sql
         */
        final String sql = "update user set name = ? , descs = ? where id = ?";
        simpleJdbcTemplate.update(sql, user.getName(), user.getDescs(),
                user.getId());
    }

}
