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

import java.lang.reflect.Method;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * 注册到Spring-test的listener，这样就能在junit执行周期中插入dbunit的准备和清除数据操作.
 * 
 * @author zhangjunjun
 */
public class DBUnitExecutionListener extends AbstractTestExecutionListener {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.test.context.support.AbstractTestExecutionListener
     * #beforeTestMethod(org.springframework.test.context.TestContext)
     */
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        Object testInstance = testContext.getTestInstance();
        Method testMethod = testContext.getTestMethod();
        if (testInstance instanceof AbstractDBUnitSpringContextTests) {
            DBUnitFile annotation = testMethod.getAnnotation(DBUnitFile.class);
            if (annotation != null) {
                String value = annotation.value();
                ((AbstractDBUnitSpringContextTests) testInstance)
                        .setDbunitFile(value);
                ((AbstractDBUnitSpringContextTests) testInstance)
                        .beforeForDBUnit();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.test.context.support.AbstractTestExecutionListener
     * #afterTestMethod(org.springframework.test.context.TestContext)
     */
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        Object testInstance = testContext.getTestInstance();
        Method testMethod = testContext.getTestMethod();
        if (testInstance instanceof AbstractDBUnitSpringContextTests) {
            DBUnitFile annotation = testMethod.getAnnotation(DBUnitFile.class);
            if (annotation != null) {
                ((AbstractDBUnitSpringContextTests) testInstance)
                        .afterForDBUnit();
            }
        }
    }
}
