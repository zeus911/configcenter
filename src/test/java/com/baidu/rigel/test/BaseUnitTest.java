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
package com.baidu.rigel.test;

import com.baidu.rigel.test.dbunit.AbstractDBUnitSpringContextTests;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

/**
 * 测试基类，继承至AbstractDBUnitSpringContextTests，支持用dbunit准备和清除数据，支持spring-test的事务，
 * 可以做完测试后自动回滚。 同时，拥有jdbcTemplate和simpleJdbcTemplate，用这两者查询最后的实际结果。.
 * 
 * @author zhangjunjun
 */
@ContextConfiguration(locations = { "/conf/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class BaseUnitTest extends AbstractDBUnitSpringContextTests {

    /**
     * Count the rows in the given table.
     * 
     * @param tableName
     *            table name to count rows in
     * @return the number of rows in the table
     */
    protected int countRowsInTable(String tableName) {
        return SimpleJdbcTestUtils.countRowsInTable(this.simpleJdbcTemplate,
                tableName);
    }

    /**
     * Convenience method for deleting all rows from the specified tables. Use
     * with caution outside of a transaction!
     * 
     * @param names
     *            the names of the tables from which to delete
     * @return the total number of rows deleted from all specified tables
     */
    protected int deleteFromTables(String... names) {
        return SimpleJdbcTestUtils.deleteFromTables(this.simpleJdbcTemplate,
                names);
    }

    // @Resource(name = "sessionFactory")
    // protected SessionFactory sessionFactory;

    /**
     * 将当前Hibernate中session内容flush到数据库，使spring的simpleJdbcTemplate可以访问之前对数据库的更改
     */
    // protected void flushCurrentSession() {
    // Session session = sessionFactory.getCurrentSession();
    // if (session != null) {
    // session.flush();
    // }
    // }

}
