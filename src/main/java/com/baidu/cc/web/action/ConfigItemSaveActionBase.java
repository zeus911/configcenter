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

package com.baidu.cc.web.action;

import com.baidu.bjf.web.struts2.annotation.FromJSON;
import com.baidu.bjf.web.struts2.annotation.ToJSON;
import com.baidu.cc.configuration.bo.ConfigItem;
import com.baidu.cc.web.action.ConfigItemSaveActionBase.DataResult;
import com.baidu.lego.web.spi.EJsonDataResult;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 功能：保存配置项接口，通用，含CRUD.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/configItem")
@Results({ @Result(name = "success", type = "json")

})
public abstract class ConfigItemSaveActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = ConfigItemSaveAction.class)
    protected ConfigItemSaveActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public ConfigItemSaveActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new ConfigItemSaveActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(ConfigItemSaveActionBase.ReqParam reqParam) {
        this.reqParam = reqParam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baidu.lego.web.struts2.DirectVisitAction#getAsReqParam()
     */
    @Override
    public Object getAsReqParam() {
        return getReqParam();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.lego.web.struts2.DirectVisitAction#setAsDataResult(java.lang
     * .Object)
     */
    @Override
    public void setAsDataResult(Object v) {
        if (v instanceof ConfigItemSaveActionBase.DataResult) {
            setData((ConfigItemSaveActionBase.DataResult) v);
        }
        setEJsonDataResult(v);
    }

    /** return data object. */
    protected ConfigItemSaveActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public ConfigItemSaveActionBase.DataResult getData() {
        if (data == null) {
            data = new ConfigItemSaveActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(ConfigItemSaveActionBase.DataResult data) {
        this.data = data;
    }

    /** return data object of e-json wrapped. */
    @ToJSON
    protected EJsonDataResult result;

    /**
     * get return data object wrapped by ejson.
     * 
     * @return EJsonDataResult
     */
    public EJsonDataResult getResult() {
        if (result == null) {
            result = new EJsonDataResult();
        }
        return result;
    }

    /**
     * set ejson data object.
     * 
     * @param result
     *            EJsonDataResult
     */
    public void setEJsonDataResult(EJsonDataResult result) {
        this.result = result;
    }

    /**
     * set ejson data object.
     * 
     * @param data
     *            DataResult
     */
    public void setEJsonDataResult(Object data) {
        getResult().setData(data);
    }

    /**
     * set ejson data object.
     * 
     * @param data
     *            DataResult
     * @param status
     *            状态码
     * @param statusInfo
     *            状态信息
     */
    public void setEJsonDataResult(DataResult data, int status,
            String statusInfo) {
        getResult().setData(data);
        getResult().setStatus(status);
        getResult().setStatusInfo(statusInfo);
    }

    // step1 main entry to define action name
    /**
     * do execute service.
     * 
     * @return result name
     * @throws Exception
     *             throws all available exception
     */
    @Action("saveConfigItem")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
    @Validations(regexFields = {
        @RegexFieldValidator(fieldName = "reqParam.updateConfigItemList.versionId", 
            message = "字段\'versionId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.insertConfigItemList.versionId", 
            message = "字段\'versionId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$") })
    public final void internalValidator() {
        throw new RuntimeException("invalid access.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.baidu.lego.web.struts2.DirectVisitAction#getValidationTarget(java
     * .lang.Object)
     */
    @Override
    protected Object getValidationTarget(Object target) {
        ConfigItemSaveAction action = new ConfigItemSaveAction();
        action.setReqParam((ConfigItemSaveActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：要修改的配置项列表. */
        private List<ConfigItem> updateConfigItemList;

        /** 说明：要新增的配置项列表. */
        private List<ConfigItem> insertConfigItemList;

        /** 说明：. */
        private List<Long> delConfigItemIds;

        /**
         * set 要修改的配置项列表.
         * 
         * @param updateConfigItemList
         *            List<ConfigItem>
         */
        public void setUpdateConfigItemList(
                List<ConfigItem> updateConfigItemList) {
            this.updateConfigItemList = updateConfigItemList;
        }

        /**
         * get 要修改的配置项列表.
         * 
         * @return List<ConfigItem>
         */
        public List<ConfigItem> getUpdateConfigItemList() {
            return updateConfigItemList;
        }

        /**
         * set 要新增的配置项列表.
         * 
         * @param insertConfigItemList
         *            List<ConfigItem>
         */
        public void setInsertConfigItemList(
                List<ConfigItem> insertConfigItemList) {
            this.insertConfigItemList = insertConfigItemList;
        }

        /**
         * get 要新增的配置项列表.
         * 
         * @return List<ConfigItem>
         */
        public List<ConfigItem> getInsertConfigItemList() {
            return insertConfigItemList;
        }

        /**
         * set .
         * 
         * @param delConfigItemIds
         *            List<Long>
         */
        public void setDelConfigItemIds(List<Long> delConfigItemIds) {
            this.delConfigItemIds = delConfigItemIds;
        }

        /**
         * get .
         * 
         * @return List<Long>
         */
        public List<Long> getDelConfigItemIds() {
            return delConfigItemIds;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("updateConfigItemList", updateConfigItemList);
            map.put("insertConfigItemList", insertConfigItemList);
            map.put("delConfigItemIds", delConfigItemIds);
            return map;
        }
    }

    /**
     * Data return class.
     * 
     * @author BJF
     */
    public static class DataResult {

    }

}
