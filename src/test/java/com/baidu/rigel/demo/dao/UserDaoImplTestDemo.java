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

import static org.hamcrest.CoreMatchers.is;

import com.baidu.rigel.demo.bo.User;
import com.baidu.rigel.test.BaseUnitTest;
import com.baidu.rigel.test.dbunit.DBUnitFile;
import com.baidu.rigel.test.util.TestHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.test.context.ContextConfiguration;

/**
 * 这里第一个测试演示了不用dbunit准备数据，第二个演示了用dbunit准备数据。注意 如果数据库引擎为myisam，第一个例子的回滚不会生效。
 * 这里@ContextConfiguration配置了新的位置因为父类默认是去/
 * conf/applicationContext.xml找配置文件，如果inheritLocations设置为true表示不继承父类的配置，
 * 这里为了演示demo的例子，所以配置成了false。
 * 
 * @author zhangjunjun
 * 
 */
@ContextConfiguration(locations = { "/com/baidu/rigel/demo/conf/applicationContext-demo.xml" }, inheritLocations = false)
public class UserDaoImplTestDemo extends BaseUnitTest {

    /** The user dao. */
    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     * Test insert user.
     */
    @Test
    public void testInsertUser() {
        User user = new User();
        user.setName("rigel");
        user.setDescs("crm team");
        long id = userDao.insertUser(user);

        User actual = getUserById(id);

        // 加入User类没有equal可以这样比较
        TestHelper.assertAppear(user, actual);
    }

    /**
     * Test update user.
     */
    @Test
    @DBUnitFile("test/com/baidu/rigel/demo/dao/user.xml")
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("rigelrd");
        user.setDescs("rigel developer");
        userDao.updateUser(user);

        User actual = getUserById(user.getId());
        Assert.assertThat("name is not equals", actual.getName(),
                is(user.getName()));
        Assert.assertThat("desc is not equals", actual.getDescs(),
                is(user.getDescs()));
    }

    /**
     * Gets the user by id.
     * 
     * @param id
     *            the id
     * @return the user by id
     */
    private User getUserById(long id) {
        String sql = "select * from user where id = ?";
        User user = simpleJdbcTemplate.queryForObject(sql, getMapper(), id);
        return user;
    }

    /**
     * Gets the mapper.
     * 
     * @return the mapper
     */
    private ParameterizedRowMapper<User> getMapper() {
        ParameterizedRowMapper<User> mapper = new ParameterizedRowMapper<User>() {
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setDescs(rs.getString("descs"));
                return user;
            }
        };
        return mapper;
    }

}
