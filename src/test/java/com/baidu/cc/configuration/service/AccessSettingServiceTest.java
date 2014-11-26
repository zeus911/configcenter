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
package com.baidu.cc.configuration.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.baidu.cc.common.SysUtils;
import com.baidu.cc.configuration.bo.AccessSetting;
import com.baidu.cc.configuration.dao.AccessSettingMapper;
import com.baidu.cc.configuration.service.impl.AccessSettingServiceImpl;

import java.util.List;

import mockit.Injectable;
import mockit.Tested;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

/**
 * Created by zhenhui on 14-3-20.
 * 
 * @author zhenhui
 */
public class AccessSettingServiceTest {

    /** 测试AccessSettingServiceImpl. */
    @Tested
    private AccessSettingServiceImpl accessSettingService;

    /** The access setting mapper. */
    @Injectable
    private AccessSettingMapper accessSettingMapper;

    /**
     * Test check auth.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCheckAuth() throws Exception {
        Long userId = 1L;
        Long projectId = 1L;

        List<AccessSetting> settingList = accessSettingService
                .findAllByUserId(userId);

        assertTrue(CollectionUtils.isEmpty(settingList));
        Long refId = projectId;

        boolean hadAuth = accessSettingService.checkAuth(userId,
                SysUtils.ACCESS_SETTING_TYPE_PROJECT, refId);
        assertFalse(hadAuth);

        assertTrue(accessSettingService.findRefIdsByUserIdAndType(userId,
                SysUtils.ACCESS_SETTING_TYPE_PROJECT).size() == 0);

        accessSettingService.delete(userId, refId,
                SysUtils.ACCESS_SETTING_TYPE_PROJECT);
    }

}
