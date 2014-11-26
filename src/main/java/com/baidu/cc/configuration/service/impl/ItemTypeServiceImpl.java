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
import com.baidu.cc.configuration.bo.ItemType;
import com.baidu.cc.configuration.dao.ItemTypeDao;
import com.baidu.cc.configuration.service.ItemTypeService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * Service implement class for model :
 * com.baidu.cc.configuration.bo.ItemTypeBase
 * 
 * @author BJF
 */
@Service("itemTypeService")
public class ItemTypeServiceImpl extends
        GenericSqlMapServiceImpl<ItemType, Long> implements ItemTypeService {
    /**
     * Dao 'itemTypeDao' reference.
     */
    @Resource(name = "itemTypeDao")
    private ItemTypeDao itemTypeDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.bjf.service.GenericSqlMapServiceImpl#getDao()
     */
    @Override
    public SqlMapDao<ItemType, Long> getDao() {
        return itemTypeDao;
    }
}