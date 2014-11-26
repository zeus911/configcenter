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
package com.baidu.rigel.test.dbunit;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * DBunit和Spring-test结合的基类，如果要用dbunit准备和清楚数据库数据，直接在方法上面加上@DBUnitFile注解.
 * 
 * @author zhangjunjun
 */
@TestExecutionListeners({ DBUnitExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Transactional
public abstract class AbstractDBUnitSpringContextTests extends
        AbstractJUnit4SpringContextTests {

    /** The logger. */
    private Log logger = LogFactory
            .getLog(AbstractDBUnitSpringContextTests.class);

    /** The data source. */
    protected DataSource dataSource;

    /** The data source database tester. */
    protected DataSourceDatabaseTester dataSourceDatabaseTester;

    /** The dbunit file. */
    protected String dbunitFile;

    /** The set up operation. */
    protected DatabaseOperation setUpOperation = DatabaseOperation.CLEAN_INSERT;

    /** The tear down operation. */
    protected DatabaseOperation tearDownOperation = DatabaseOperation.DELETE;

    /** The simple jdbc template. */
    protected SimpleJdbcTemplate simpleJdbcTemplate;

    /**
     * Gets the data set.
     * 
     * @return the data set
     */
    protected IDataSet getDataSet() {
        IDataSet dataSet = null;
        try {
            // dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream(
            // dbunitFile));
            dataSet = new FlatXmlDataSetBuilder()
                    .build(AbstractDBUnitSpringContextTests.class
                            .getResourceAsStream(dbunitFile));
        } catch (DataSetException e) {
            logger.error(e.getStackTrace());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            e.printStackTrace();
        }
        return dataSet;
    };

    /**
     * Before for db unit.
     * 
     * @throws Exception
     *             the exception
     */
    public void beforeForDBUnit() throws Exception {
        if (!isDBUnitFileExists()) {
            return; // 如果没有数据则什么也不做，兼容不用dbunit的情况
        }

        dataSourceDatabaseTester = new DataSourceDatabaseTester(getDataSource());
        dataSourceDatabaseTester.setDataSet(getDataSet());

        dataSourceDatabaseTester.setSetUpOperation(setUpOperation);
        dataSourceDatabaseTester.onSetup();
    }

    /**
     * After for db unit.
     * 
     * @throws Exception
     *             the exception
     */
    public void afterForDBUnit() throws Exception {
        if (!isDBUnitFileExists()) {
            return; // 如果没有数据则什么也不做，兼容不用dbunit的情况
        }

        dataSourceDatabaseTester.setTearDownOperation(tearDownOperation);
        dataSourceDatabaseTester.onTearDown();
    }

    /**
     * Checks if is DB unit file exists.
     * 
     * @return true, if is DB unit file exists
     */
    private boolean isDBUnitFileExists() {
        if (dbunitFile != null && !"".equals(dbunitFile.trim())) {
            return true;
        }
        return false;
    }

    /**
     * Gets the data source.
     * 
     * @return the data source
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Sets the data source.
     * 
     * @param dataSource
     *            the new data source
     */
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    /**
     * Gets the dbunit file.
     * 
     * @return the dbunit file
     */
    public String getDbunitFile() {
        return dbunitFile;
    }

    /**
     * Sets the dbunit file.
     * 
     * @param dbunitFile
     *            the new dbunit file
     */
    public void setDbunitFile(String dbunitFile) {
        this.dbunitFile = dbunitFile;
    }

}
