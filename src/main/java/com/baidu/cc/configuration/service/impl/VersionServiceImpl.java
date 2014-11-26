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
package com.baidu.cc.configuration.service.impl;

import com.baidu.bjf.dao.SqlMapDao;
import com.baidu.bjf.service.GenericSqlMapServiceImpl;
import com.baidu.bjf.util.CalendarSerializer;
import com.baidu.bjf.util.DateSerializer;
import com.baidu.bjf.util.JsonTypeAdapters;
import com.baidu.cc.common.SysUtils;
import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.configuration.bo.Version;
import com.baidu.cc.configuration.dao.VersionDao;
import com.baidu.cc.configuration.dao.VersionMapper;
import com.baidu.cc.configuration.service.ConfigCopyService;
import com.baidu.cc.configuration.service.ConfigGroupService;
import com.baidu.cc.configuration.service.ConfigItemService;
import com.baidu.cc.configuration.service.VersionService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implement class for model : com.baidu.cc.configuration.bo.VersionBase
 * 
 * @author BJF
 */
@Service("versionService")
public class VersionServiceImpl extends GenericSqlMapServiceImpl<Version, Long>
        implements VersionService {

    /** The Constant CONFIG_ITEMS_ELE. */
    private static final String CONFIG_ITEMS_ELE = "configItems";

    /** The gson. */
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.sql.Date.class, new DateSerializer())
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Calendar.class, new CalendarSerializer())
            .registerTypeAdapter(GregorianCalendar.class,
                    new CalendarSerializer())
            .registerTypeAdapter(Long.class, JsonTypeAdapters.LONG_DESERIALIZER)
            .registerTypeAdapter(Character.class,
                    JsonTypeAdapters.CHARACTER_TYPE_ADAPTER)
            .registerTypeAdapter(Integer.class,
                    JsonTypeAdapters.INTEGER_TYPE_ADAPTER)
            .registerTypeAdapter(Boolean.class,
                    JsonTypeAdapters.BOOLEAN_TYPE_ADAPTER)
            .registerTypeAdapter(boolean.class,
                    JsonTypeAdapters.BOOLEAN_TYPE_ADAPTER)
            .registerTypeAdapter(Number.class,
                    JsonTypeAdapters.NUMBER_TYPE_ADAPTER)
            .registerTypeAdapter(Float.class,
                    JsonTypeAdapters.FLOAT_TYPE_ADAPTER)
            .registerTypeAdapter(Double.class,
                    JsonTypeAdapters.DOUBLE_TYPE_ADAPTER)
            .registerTypeAdapter(Byte.class, JsonTypeAdapters.BYTE_TYPE_ADAPTER)
            .registerTypeAdapter(Short.class,
                    JsonTypeAdapters.SHORT_TYPE_ADAPTER).create();

    /** Logger for this class. */
    private static final Logger LOGGER = Logger
            .getLogger(VersionServiceImpl.class);

    /**
     * Dao 'versionDao' reference.
     */
    @Resource(name = "versionDao")
    private VersionDao versionDao;

    /**
     * Service 'configItemService' reference.
     */
    @Resource(name = "configItemService")
    private ConfigItemService configItemService;

    /** ConfigGroupService. */
    @Resource
    private ConfigGroupService configGroupService;

    /** ConfigCopyService. */
    @Resource
    private ConfigCopyService configCopyService;

    /**
     * Mapper 'versionMapper' reference.
     */
    @Autowired
    private VersionMapper versionMapper;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<Version, Long> getDao() {
        return versionDao;
    }

    /**
     * query {@link Version} by version id and fetch all related
     * {@link ConfigItem}.
     * 
     * @param versionName
     *            version name
     * @return {@link Version}
     */
    public Version findVersionAndConfigItems(String versionName) {

        Version version = versionMapper.findByName(versionName);
        if (version != null) {
            List<ConfigItem> configItems;
            configItems = configItemService.findByVersionId(version.getId(),
                    true);
            version.setConfigItems(configItems);
        }

        return version;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.cc.configuration.service.VersionService#findVersionListByEnvId
     * (java.lang.Long)
     */
    @Override
    public List<Version> findVersionListByEnvId(Long environmentId) {
        return versionDao.findVersionListByEnvId(environmentId);
    }

    /**
     * 根据envId级联删除version,含其所有子项.
     * 
     * @param ids
     *            ids
     */
    @Override
    public void deleteVersionCascadeByIds(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Long> configGroupIds = configGroupService
                    .findConfigGroupIdsByVersionIds(ids);
            configGroupService.deleteConfigGroupCascadeByIds(configGroupIds);
            versionDao.deleteBatch(ids.toArray(new Long[] {}));
        }
    }

    /**
     * 根据环境ID查询所有相关的versionId列表.
     * 
     * @param envIds
     *            环境ID列表
     * @return versionId列表
     */
    @Override
    public List<Long> findVersionIdsByEnvIds(List<Long> envIds) {
        if (CollectionUtils.isEmpty(envIds)) {
            return null;
        }
        return versionDao.findVersionIdsByEnvIds(envIds);
    }

    /**
     * 根据envId查询id最大的版本号.
     * 
     * @param envId
     *            环境ID
     * @return 版本对象 或者为null即不存在
     */
    @Override
    public Version findLastestByEnvId(Long envId) {
        return versionMapper.findLastestByEnvId(envId);
    }

    /**
     * 从文件中导入配置组与配置项到指定版本。.
     * 
     * @param file
     *            导入文件
     * @param versionId
     *            the version id
     * @throws IOException
     *             文件操作异常
     */
    @Override
    public void importFromFile(File file, Long versionId) throws IOException {
        byte[] byteArray = FileUtils.readFileToByteArray(file);

        Hex encoder = new Hex();
        try {
            byteArray = encoder.decode(byteArray);
        } catch (DecoderException e) {
            throw new IOException(e.getMessage());
        }
        String json = new String(byteArray, SysUtils.UTF_8);

        // parse from gson
        JsonParser jsonParser = new JsonParser();
        JsonElement je = jsonParser.parse(json);

        if (!je.isJsonArray()) {
            throw new RuntimeException(
                    "illegal json string. must be json array.");
        }

        JsonArray jsonArray = je.getAsJsonArray();

        int size = jsonArray.size();
        Version version = new Version();
        List<ConfigGroup> groups = new ArrayList<ConfigGroup>();
        ConfigGroup group;
        List<ConfigItem> items;
        ConfigItem item;

        for (int i = 0; i < size; i++) {
            JsonObject jo = jsonArray.get(i).getAsJsonObject();
            group = gson.fromJson(jo, ConfigGroup.class);

            // get sub configuration item
            JsonArray subItemsJson = jo.get(CONFIG_ITEMS_ELE).getAsJsonArray();

            int subSize = subItemsJson.size();
            items = new ArrayList<ConfigItem>();
            for (int j = 0; j < subSize; j++) {
                item = gson.fromJson(subItemsJson.get(j), ConfigItem.class);
                items.add(item);
            }

            group.setConfigItems(items);
            groups.add(group);
        }

        version.setConfigGroups(groups);
        configCopyService.copyConfigItemsFromVersion(version, versionId);
    }

    /**
     * 将指定版本号的配置内容导出到输出流.
     * 
     * @param os
     *            输出流
     * @param versionId
     *            版本号id
     * @throws IOException
     *             流操作异常
     */
    @Override
    public void exportToFile(OutputStream os, Long versionId)
            throws IOException {

        Version version = findById(versionId);
        if (version == null) {
            LOGGER.warn("export to file failed due to version id not exist. id="
                    + versionId);
            return;
        }

        JsonArray jsonArray = new JsonArray();

        // get all group
        List<ConfigGroup> groups = configGroupService
                .findByVersionId(versionId);
        if (CollectionUtils.isEmpty(groups)) {
            LOGGER.warn("No config group under version id:" + versionId);
            return;
        }

        for (ConfigGroup configGroup : groups) {
            JsonObject jo = configGroup.copyToJson();
            jsonArray.add(jo);
            Long groupId = configGroup.getId();
            // add sub
            List<ConfigItem> items = configItemService.findByGroupId(groupId,
                    true);
            if (CollectionUtils.isEmpty(items)) {
                LOGGER.warn("No config items under version id:" + versionId
                        + " and group id:" + groupId);
            }

            JsonArray subItemsJson = new JsonArray();
            for (ConfigItem configItem : items) {
                JsonObject itemJson = configItem.copyToJson();
                subItemsJson.add(itemJson);
            }
            jo.add(CONFIG_ITEMS_ELE, subItemsJson);
        }

        writeJson(jsonArray, os);

    }

    /**
     * Write json string to {@link OutputStream}.
     * 
     * @param jsonArray
     *            {@link JsonArray}
     * @param os
     *            {@link OutputStream}
     * @throws IOException
     *             流操作异常
     */
    private void writeJson(JsonArray jsonArray, OutputStream os)
            throws IOException {
        String json = gson.toJson(jsonArray);
        // to byte array
        byte[] byteArray = json.getBytes(SysUtils.UTF_8);

        Hex encoder = new Hex();
        byteArray = encoder.encode(byteArray);
        IOUtils.write(byteArray, os);
    }

    /**
     * 根据envId列表查找版本列表.
     * 
     * @param envIds
     *            envIds
     * @return List<Version>
     */
    @Override
    public List<Version> findVersionListByEnvIds(List<Long> envIds) {
        if (CollectionUtils.isEmpty(envIds)) {
            return new ArrayList<Version>();
        }
        return versionDao.findVersionListByEnvIds(envIds);
    }

    /**
     * 修改变更通知字段.
     * 
     * @param versionId
     *            版本ID
     */
    @Override
    public void pushChange(Long versionId) {
        Version version = versionDao.findById(versionId);
        version.setCheckSumDate(new Date());
        version.setCheckSum(SysUtils.uuid());

        versionDao.saveOrUpdate(version);

    }

    /**
     * 根据版本名称查询版本ID.
     * 
     * @param name
     *            版本名称
     * @return ID
     */
    @Override
    public Long findIdByName(String name) {
        Version version = versionMapper.findByName(name);
        return version == null ? null : version.getId();
    }
}