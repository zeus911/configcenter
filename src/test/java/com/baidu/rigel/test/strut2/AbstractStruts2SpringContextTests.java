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
package com.baidu.rigel.test.strut2;

import com.opensymphony.xwork2.ActionContext;

import java.util.HashMap;

import org.apache.struts2.ServletActionContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 此类主要是方便struts2的测试，默认加入了与http有关的mock对象.
 * 
 * @author zhangjunjun
 */
@TestExecutionListeners({ Struts2TestExecutionListener.class })
public class AbstractStruts2SpringContextTests extends
        AbstractTransactionalJUnit4SpringContextTests {

    /** The response. */
    protected MockHttpServletResponse response;

    /** The request. */
    protected MockHttpServletRequest request;

    /** The page context. */
    protected MockPageContext pageContext;

    /** The servlet context. */
    protected MockServletContext servletContext;

    /**
     * Execute before test method.
     */
    public void executeBeforeTestMethod() {
        response = new MockHttpServletResponse();
        request = new MockHttpServletRequest();
        pageContext = new MockPageContext();
        servletContext = new MockServletContext();

        ActionContext context = new ActionContext(new HashMap<String, Object>());
        ServletActionContext.setContext(context);

        ServletActionContext.setRequest(request);
        ServletActionContext.setResponse(response);
    }

}
