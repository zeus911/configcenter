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
package com.baidu.rigel.demo.action;

import com.baidu.rigel.demo.bo.User;
import com.baidu.rigel.demo.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

/**
 * The Class UserAction.
 */
@Controller("userAction")
public class UserAction extends ActionSupport {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2735298865687951988L;

    /** The user service. */
    @Resource(name = "userService")
    private UserService userService;

    /**
     * Register user.
     * 
     * @return the string
     */
    public String registerUser() {
        User user = new User();
        HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("user.name");
        String descs = request.getParameter("user.descs");

        user.setName(name);
        user.setDescs(descs);
        userService.registerUser(user);
        return SUCCESS;
    }
}
