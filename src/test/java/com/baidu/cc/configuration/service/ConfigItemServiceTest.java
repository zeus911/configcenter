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

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.dao.ConfigItemDao;
import com.baidu.cc.configuration.dao.ConfigItemMapper;
import com.baidu.cc.configuration.service.impl.ConfigItemServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

/**
 * Created by zhenhui on 14-3-14.
 * 
 * @author zhenhui
 */
public class ConfigItemServiceTest {

    /** 要测试的类. */
    @Tested
    private ConfigItemServiceImpl configItemService;

    /** 自动mock并注入. */
    @Injectable
    private ConfigItemDao configItemDao;

    /** 自动mock并注入. */
    @Injectable
    private ConfigItemMapper configItemMapper;

    /**
     * 测试批量保存方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test(expected = RuntimeException.class)
    public void testBatchInsertConfigItems() throws Exception {
        String content = "ipg_mq.url=failover:(tcp://10.48.22.71:61616,tcp://10.48.22.71:61616)?randomize="
                + "false&timeout=10\n"
                + "\n"
                + "ipg_mq.audit_black_list_queue_name=audit_black_list_queue\n"
                + "\n"
                + "ipg_mq.audit_season_cust_list_queue_name=audit_season_cust_list_queue\n"
                + "\n"
                + "ipg_mq.audit_none_coreword_queue_name=audit_none_coreword_queue\n"
                + "\n"
                + "ipg_mq.audit_url_white_list_queue_name=audit_url_white_list_queue\n"
                + "\n"
                + "ipg_mq.audit_shifen_url_white_list_queue_name=audit_shifen_url_white_list_queue\n"
                + "\n"
                + "ipg_mq.audit_opportunity_queue_name=audit_opportunity_queue\n"
                + "\n"
                + "ipg_mq.audit_cust_url_queue_name=audit_cust_url_queue\n"
                + "\n"
                + "ipg_mq.audit_cust_phone_queue_name=audit_cust_phone_queue\n"
                + "\n"
                + "ipg_mq.common_queue_name=ipg_sale_queue\n"
                + "\n"
                + "ipg_mq.common_topic_name=ipg_sale_topic\n"
                + "\n"
                + "ipg_mq.custid_topic_name=custid_topic_name\n"
                + "\n"
                + "ipg_mq.common_callout_queue_name=ipg_common_callout_queue\n"
                + "\n"
                + "ipg_mq.common_support_queue_name=ipg_common_support_queue\n"
                + "\n"
                + "ipg_mq.common_hint_queue_name=ipg_common_hint_queue\n"
                + "\n"
                + "ipg_mq.index_cust_queue_name=index_cust_queue\n"
                + "\n"
                + "ipg_mq.index_contact_queue_name=index_contact_queue\n"
                + "#新增客户资料\n"
                + "ipg_mq.add_cust_topic_name=add_cust_topic\n"
                + "#更新客户资料\n"
                + "ipg_mq.update_cust_topic_name=update_cust_topic\n"
                + "#新增联系人\n"
                + "ipg_mq.add_contact_topic_name=add_contact_topic\n"
                + "#更新联系人\n"
                + "ipg_mq.update_contact_topic_name=update_contact_topic\n"
                + "#新增客户联系人关系\n"
                + "ipg_mq.add_cust_to_contact_topic_name=add_cust_to_contact_topic\n"
                + "#删除客户联系人关系\n"
                + "ipg_mq.delete_cust_to_contact_topic_name=delete_cust_to_contact_topic\n"
                + "test=.*classLoader.*, (.*.|^)class..*, ^dojo..*, ^struts..*, ^session..*, ^"
                + "request..*, ^application..*, ^servlet(Request|Response)..*, ^parameters..*, ^action:"
                + ".*, ^method:.*, __cas__st__, __cas__id__, .*[/()#@'].";
        new NonStrictExpectations(configItemService) {
            {
                configItemService.saveOrUpdateAll((List<ConfigItem>) any);
            }
        };

        Long groupId = 1L;
        Long versionId = 1L;
        configItemService.batchInsertConfigItems(groupId, versionId, content);

        new NonStrictExpectations() {
            {
                configItemDao.deleteByGroupId(anyLong);
                result = new RuntimeException("测试异常分支");
            }
        };

        configItemService.batchInsertConfigItems(groupId, versionId, content);

    }

    /**
     * 测试几个find方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testFind() throws Exception {

        assertTrue(CollectionUtils.isEmpty(configItemService.findByVersionId(
                1L, false)));
        assertNull(configItemService.findByVersionIdAndName(1L, "test", false));

        new NonStrictExpectations() {
            {
                List<ConfigItem> items = new ArrayList<ConfigItem>();
                ConfigItem configItem = new ConfigItem();
                configItem.setRef(true);
                configItem.setId(100L);
                items.add(configItem);
                configItemMapper.findByVersionId(anyLong);
                returns(items);

                configItemDao.findById(anyLong);
                result = configItem;

                configItemMapper
                        .findByVersionIdAndName((Map<String, Object>) any);
                result = configItem;
            }
        };
        assertTrue(configItemService.findByVersionId(1L, true).get(0).getId() == 100L);

        assertTrue(configItemService.findByVersionIdAndName(1L, "test", false)
                .getId() == 100L);
        assertTrue(configItemService.findByVersionIdAndName(1L, "test", true)
                .getId() == 100L);

        assertNull(configItemService.findConfigItemIdsByConfigGroupIds(null));
        assertTrue(CollectionUtils.isEmpty(configItemService
                .findConfigItemIdsByConfigGroupIds(Arrays.asList(1L, 2L))));

    }

    /**
     * 测试保存方法.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSaveConfigItems() throws Exception {
        List<ConfigItem> items = new ArrayList<ConfigItem>();
        ConfigItem configItem = new ConfigItem();
        configItem.setRef(true);
        configItem.setId(100L);
        items.add(configItem);

        configItemService.saveConfigItems(items, items, Arrays.asList(1L, 2L));
    }

}
