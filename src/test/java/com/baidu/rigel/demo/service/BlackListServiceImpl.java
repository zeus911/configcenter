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
package com.baidu.rigel.demo.service;

import com.baidu.rigel.demo.bo.User;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * The Class BlackListServiceImpl.
 */
@Service("blackListService")
public class BlackListServiceImpl implements BlackListService {

    /**
     * return true if user is in blacklist
     * 
     * @param user
     *            the user
     * @return true if in blacklist
     */
    public boolean isInBlackList(User user) {
        Set<String> list = new HashSet<String>();
        list.add("Liu");
        list.add("LiMing");
        list.add("Jack Zhang");
        list.add("Guo");
        return list.contains(user.getName());
    }

}
