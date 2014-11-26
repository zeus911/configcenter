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
package com.baidu.cc.web.rpc;

import com.baidu.cc.BasePrepareTestData;
import com.baidu.cc.interfaces.ExtConfigServerService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test class for {@link ConfigServerServiceImpl}.
 * 
 * @author xiemalin
 * @since 1.0.0.0
 */
public class ConfigServerServiceImplTest extends BasePrepareTestData {

    /**
     * {@link ExtConfigServerService} instance.
     */
    @Resource
    private ExtConfigServerService extConfigServerService;

    /**
     * this test should throw {@link IllegalAccessError} exception due to error
     * use and password.
     */
    @Test
    public void testGetConfigItemsAuthenticateFailed() {
        String errorUser = UUID.randomUUID().toString();
        String errorPwd = errorApiPassword;
        try {

            extConfigServerService.getConfigItems(errorUser, errorPwd, 1L);
            Assert.fail("error user and password should throw excpetion");
        } catch (IllegalAccessError e) {
            Assert.assertTrue(true);
        }

        // error password
        try {

            extConfigServerService.getConfigItems(username, errorPwd, 1L);
            Assert.fail("error user and password should throw excpetion");
        } catch (IllegalAccessError e) {
            Assert.assertTrue(true);
        }

    }

    /**
     * test getConfigItems method.
     */
    @Test
    public void testGetConfigItemsAuthenticateOk() {
        Map<String, String> configItems;
        configItems = extConfigServerService.getConfigItems(username,
                encryptedApiPassword, versionId);

        Assert.assertEquals(3, configItems.size());
        Assert.assertEquals(tag,
                configItems.get(ExtConfigServerService.TAG_KEY));
        Assert.assertTrue(configItems.containsKey(itemName1));
        Assert.assertTrue(configItems.containsKey(itemName2));

        Assert.assertEquals(itemValue1, configItems.get(itemName1));
        Assert.assertEquals(itemValue2, configItems.get(itemName2));

        // check version not found
        try {
            configItems = extConfigServerService.getConfigItems(username,
                    encryptedApiPassword, -1L);
            Assert.fail();
        } catch (IllegalAccessError e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * test method 'getLastestConfigItems'.
     */
    @Test
    public void testGetLastestConfigItems() {
        Map<String, String> configItems;
        configItems = extConfigServerService.getLastestConfigItems(username,
                encryptedApiPassword, envId);

        Assert.assertEquals(3, configItems.size());
        Assert.assertEquals(tag,
                configItems.get(ExtConfigServerService.TAG_KEY));
        Assert.assertTrue(configItems.containsKey(itemName1));
        Assert.assertTrue(configItems.containsKey(itemName2));

        Assert.assertEquals(itemValue1, configItems.get(itemName1));
        Assert.assertEquals(itemValue2, configItems.get(itemName2));

    }

    /**
     * test method 'getLastestConfigVersion'.
     */
    @Test
    public void testGetLastestConfigVersionByEnvId() {
        long testVersionId;
        testVersionId = extConfigServerService.getLastestConfigVersion(
                username, encryptedApiPassword, envId);

        Assert.assertEquals(versionId, testVersionId);

        // check version not found
        try {
            testVersionId = extConfigServerService.getLastestConfigVersion(
                    username, encryptedApiPassword, -1L);
            Assert.fail();
        } catch (IllegalAccessError e) {
            Assert.assertTrue(true);
        }
    }

    /**
     * test method 'getLastestConfigVersion'.
     */
    @Test
    public void testGetLastestConfigVersionByProjectNameAndEnvName() {
        long testVersionId;
        testVersionId = extConfigServerService.getLastestConfigVersion(
                username, encryptedApiPassword, projectName, envName);
        Assert.assertEquals(versionId, testVersionId);
    }

    /**
     * test method 'getConfigItemValue'.
     */
    @Test
    public void testGetConfigItemValue() {
        String value;
        value = extConfigServerService.getConfigItemValue(username,
                encryptedApiPassword, versionId, itemName1);

        Assert.assertEquals(itemValue1, value);

        // get a not exist item
        value = extConfigServerService.getConfigItemValue(username,
                encryptedApiPassword, versionId, itemName1 + "notexist");

        Assert.assertNull(value);

        // check version not found
        try {
            value = extConfigServerService.getConfigItemValue(username,
                    encryptedApiPassword, -1L, itemName1);
            Assert.fail();
        } catch (IllegalAccessError e) {
            Assert.assertTrue(true);
        }

    }

    /**
     * test method 'checkVersionTag'.
     */
    @Test
    public void testCheckVersionTag() {

        boolean lastest;
        lastest = extConfigServerService.checkVersionTag(username,
                encryptedApiPassword, versionId, tag);

        Assert.assertEquals(true, lastest);

        lastest = extConfigServerService.checkVersionTag(username,
                encryptedApiPassword, versionId, UUID.randomUUID().toString());

        Assert.assertEquals(false, lastest);

        // check version tag blank string
        lastest = extConfigServerService.checkVersionTag(username,
                encryptedApiPassword, versionId, "");
        Assert.assertEquals(false, lastest);

        // check version tag but version not found
        lastest = extConfigServerService.checkVersionTag(username,
                encryptedApiPassword, -1L, "");
        Assert.assertEquals(false, lastest);
    }

    /**
     * test get version id by name method.
     */
    @Test
    public void testGetVersionId() {
        long id;
        id = extConfigServerService.getVersionId(username,
                encryptedApiPassword, versionName);

        Assert.assertEquals(id, versionId);
    }

    /**
     * test get version id by name method.
     */
    @Test
    public void testGetVersionIdNotFound() {
        Long id;
        id = extConfigServerService.getVersionId(username,
                encryptedApiPassword, versionName + "notfound");

        Assert.assertNull(id);
    }

    /**
     * Testimport config items.
     */
    @Test
    public void testimportConfigItems() {
        Map<String, String> newImportItems = new HashMap<String, String>();
        newImportItems.put("hello", "world");

        long newversionId = prepareVersionData("a_new_created_version", envId,
                projectId);
        extConfigServerService.importConfigItems(username,
                encryptedApiPassword, newversionId, newImportItems);

        Map<String, String> configItems;
        configItems = extConfigServerService.getConfigItems(username,
                encryptedApiPassword, newversionId);

        Assert.assertEquals(2, configItems.size());

        // import blank
        extConfigServerService.importConfigItems(username,
                encryptedApiPassword, newversionId,
                new HashMap<String, String>());

    }

}
