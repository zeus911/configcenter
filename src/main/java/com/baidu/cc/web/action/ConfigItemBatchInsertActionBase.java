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
import com.baidu.cc.web.action.ConfigItemBatchInsertActionBase.DataResult;
import com.baidu.lego.web.spi.EJsonDataResult;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 功能：保存配置项接口，批量，用于将properties中的值批量导入一个group.
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
public abstract class ConfigItemBatchInsertActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = ConfigItemBatchInsertAction.class)
    protected ConfigItemBatchInsertActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public ConfigItemBatchInsertActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new ConfigItemBatchInsertActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(ConfigItemBatchInsertActionBase.ReqParam reqParam) {
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
        if (v instanceof ConfigItemBatchInsertActionBase.DataResult) {
            setData((ConfigItemBatchInsertActionBase.DataResult) v);
        }
        setEJsonDataResult(v);
    }

    /** return data object. */
    protected ConfigItemBatchInsertActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public ConfigItemBatchInsertActionBase.DataResult getData() {
        if (data == null) {
            data = new ConfigItemBatchInsertActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(ConfigItemBatchInsertActionBase.DataResult data) {
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
    @Action("batchInsertConfigItem")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
    @Validations(regexFields = {
        @RegexFieldValidator(fieldName = "reqParam.groupId", 
            message = "字段\'groupId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.versionId", 
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
        ConfigItemBatchInsertAction action = new ConfigItemBatchInsertAction();
        action.setReqParam((ConfigItemBatchInsertActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：. */
        private Long groupId;

        /** 说明：. */
        private Long versionId;

        /** 说明：properties中复制过来的内容. */
        private String content;

        /**
         * set .
         * 
         * @param groupId
         *            Long
         */
        public void setGroupId(Long groupId) {
            this.groupId = groupId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getGroupId() {
            return groupId;
        }

        /**
         * set .
         * 
         * @param versionId
         *            Long
         */
        public void setVersionId(Long versionId) {
            this.versionId = versionId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getVersionId() {
            return versionId;
        }

        /**
         * set properties中复制过来的内容.
         * 
         * @param content
         *            String
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * get properties中复制过来的内容.
         * 
         * @return String
         */
        public String getContent() {
            return content;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupId", groupId);
            map.put("versionId", versionId);
            map.put("content", StringUtils.trimToNull(content));
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
