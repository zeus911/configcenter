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
package com.baidu.rigel.test.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 一些测试帮助类.
 * 
 * @author zhangjunjun
 */
public class TestHelper {

    /**
     * 将一个私有方法转化为一个可访问的方法.
     * 
     * @param target
     *            测试对象
     * @param methodName
     *            方法名称
     * @param parameterTypes
     *            方法的传入参数类型
     * @return 可访问的m方法
     */
    public static Method getAccessablePrivateMethod(Object target,
            String methodName, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = target.getClass().getDeclaredMethod(methodName,
                    parameterTypes);
            method.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 比较两个对象是否相等，之所以有这个方式是因为有些类没有实现equal方法，手工比较每个字段比较麻烦
     * 这里比较的过程是调用对象的所有get或者is方法看是否相等.
     * 
     * @param expected
     *            期望值
     * @param actual
     *            实际值
     */
    public static void assertAppear(Object expected, Object actual) {
        assertEquals("class not equals", expected.getClass(), actual.getClass());

        Method[] methods = expected.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 0) {
                if (methodName.startsWith("get") || methodName.startsWith("is")) {
                    try {
                        Object ex = method.invoke(expected, new Object[] {});
                        Object ac = method.invoke(actual, new Object[] {});
                        assertEquals(methodName
                                + "()'s return value is not equals", ex, ac);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 比较两个列表里面的内容是否相等，之所以有这个方式是因为有些列表里面的类没有实现equal方法，手工比较每个字段比较麻烦
     * 这里比较的过程是每次调用列表里面的对象的get和is方向进行比较.
     * 
     * @param expected
     *            期待结果
     * @param actual
     *            实际结果
     */
    public static void assertListAppear(List expected, List actual) {
        Object[] expecteds = expected.toArray();
        Object[] actuals = actual.toArray();
        assertEquals("array size is not same", expecteds.length, actuals.length);

        for (int i = 0; i < expecteds.length; i++) {
            assertAppear(expecteds[i], actuals[i]);
        }
    }
}