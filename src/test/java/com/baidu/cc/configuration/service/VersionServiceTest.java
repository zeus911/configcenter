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

import com.baidu.cc.BasePrepareTestData;
import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.bo.Version;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Junit for VersionService.
 * 
 * @author xiemalin
 */
public class VersionServiceTest extends BasePrepareTestData {

    /** The Constant FILE_PATH. */
    private static final String FILE_PATH = "/com/baidu/cc/configuration/service/ConfigExportFile";

    /** {@link VersionService} instanc. */
    @Resource(name = "versionService")
    private VersionService versionService;

    /**
     * test find version by version name.
     */
    @Test
    public void testFindVersionAndConfigItems() {
        Version version = versionService
                .findVersionAndConfigItems("_not exist");

        Assert.assertNull(version);

        String name = UUID.randomUUID().toString();
        Version v = new Version();
        v.setName(name);
        v.setUpdateTime(new Date());

        versionService.saveEntity(v);

        version = versionService.findVersionAndConfigItems(name);
        Assert.assertNotNull(version);
        Assert.assertEquals(name, version.getName());

        versionService.delete(v);

    }

    /**
     * Test export to file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testExportToFile() throws IOException {

        URL toExportFile = getClass().getResource(FILE_PATH);
        if (toExportFile != null) {
            File file = new File(toExportFile.getFile());
            System.out.println(file.getAbsolutePath());
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, false);
            versionService.exportToFile(fos, versionId);

            // now test to load from file
            // create new version
            String versionName = UUID.randomUUID().toString();
            long newVersionId = prepareVersionData(versionName, envId,
                    projectId);
            versionService.importFromFile(file, newVersionId);

            List<ConfigGroup> groups = configGroupService
                    .findByVersionId(newVersionId);
            Assert.assertEquals(1, groups.size());
            ConfigGroup group = groups.get(0);
            Assert.assertEquals(groupName, group.getName());

            List<ConfigItem> items = configItemService.findByGroupId(
                    group.getId(), true);

            Assert.assertEquals(2, items.size());

            Set<String> itemNames = new HashSet<String>();
            itemNames.add(itemName1);
            itemNames.add(itemName2);

            Set<String> itemValues = new HashSet<String>();
            itemValues.add(itemValue1);
            itemValues.add(itemValue2);

            for (ConfigItem item : items) {
                Assert.assertTrue(itemNames.contains(item.getName()));
                Assert.assertTrue(itemValues.contains(item.getVal()));
            }

        }

    }

}
