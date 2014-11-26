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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * dao base class. for get primary id after insert
 * 
 * @author zhangjunjun
 * 
 */
public class BaseDao {

    /** The simple jdbc template. */
    @Resource(name = "simpleJdbcTemplate")
    protected SimpleJdbcTemplate simpleJdbcTemplate;

    /**
     * Insert and get key.
     * 
     * @param sql
     *            the sql
     * @param params
     *            the params
     * @return the object
     */
    public Object insertAndGetKey(final String sql, final Object... params) {
        final KeyHolder key = new GeneratedKeyHolder();

        simpleJdbcTemplate.getJdbcOperations().update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(
                            Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(sql,
                                PreparedStatement.RETURN_GENERATED_KEYS);
                        PreparedStatementSetter pss = new ArgPreparedStatementSetter(
                                params);
                        try {
                            if (pss != null) {
                                pss.setValues(ps);
                            }
                        } finally {
                            if (pss instanceof ParameterDisposer) {
                                ((ParameterDisposer) pss).cleanupParameters();
                            }
                        }
                        return ps;
                    }
                }, key);
        return key.getKey();
    }

    /**
     * The Class ArgPreparedStatementSetter.
     */
    class ArgPreparedStatementSetter implements PreparedStatementSetter,
            ParameterDisposer {

        /** The args. */
        private final Object[] args;

        /**
         * Instantiates a new arg prepared statement setter.
         * 
         * @param args
         *            the args
         */
        public ArgPreparedStatementSetter(Object[] args) {
            this.args = args;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.springframework.jdbc.core.PreparedStatementSetter#setValues(java
         * .sql.PreparedStatement)
         */
        public void setValues(PreparedStatement ps) throws SQLException {
            if (this.args != null) {
                for (int i = 0; i < this.args.length; i++) {
                    Object arg = this.args[i];
                    if (arg instanceof SqlParameterValue) {
                        SqlParameterValue paramValue = (SqlParameterValue) arg;
                        StatementCreatorUtils.setParameterValue(ps, i + 1,
                                paramValue, paramValue.getValue());
                    } else {
                        StatementCreatorUtils.setParameterValue(ps, i + 1,
                                SqlTypeValue.TYPE_UNKNOWN, arg);
                    }
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.springframework.jdbc.core.ParameterDisposer#cleanupParameters()
         */
        public void cleanupParameters() {
            StatementCreatorUtils.cleanupParameters(this.args);
        }

    }
}