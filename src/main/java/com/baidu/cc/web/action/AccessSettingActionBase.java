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
import com.baidu.cc.web.action.AccessSettingActionBase.DataResult;
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
 * 功能：权限设置接口.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/access")
@Results({ @Result(name = "success", type = "json")

})
public abstract class AccessSettingActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = AccessSettingAction.class)
    protected AccessSettingActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public AccessSettingActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new AccessSettingActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(AccessSettingActionBase.ReqParam reqParam) {
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
        if (v instanceof AccessSettingActionBase.DataResult) {
            setData((AccessSettingActionBase.DataResult) v);
        }
        setEJsonDataResult(v);
    }

    /** return data object. */
    protected AccessSettingActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public AccessSettingActionBase.DataResult getData() {
        if (data == null) {
            data = new AccessSettingActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(AccessSettingActionBase.DataResult data) {
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
    @Action("accessSetting")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
    @Validations(regexFields = {
        @RegexFieldValidator(fieldName = "reqParam.userId", 
            message = "字段\'userId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.projectId", 
            message = "字段\'projectId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$") })
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
        AccessSettingAction action = new AccessSettingAction();
        action.setReqParam((AccessSettingActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：被赋权限的人ID. */
        private Long userId;

        /** 说明：. */
        private Long projectId;

        /** 说明：标识操作类型，是新增还是删除. */
        private String operationType;

        /**
         * set 被赋权限的人ID.
         * 
         * @param userId
         *            Long
         */
        public void setUserId(Long userId) {
            this.userId = userId;
        }

        /**
         * get 被赋权限的人ID.
         * 
         * @return Long
         */
        public Long getUserId() {
            return userId;
        }

        /**
         * set .
         * 
         * @param projectId
         *            Long
         */
        public void setProjectId(Long projectId) {
            this.projectId = projectId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getProjectId() {
            return projectId;
        }

        /**
         * set 标识操作类型，是新增还是删除.
         * 
         * @param operationType
         *            String
         */
        public void setOperationType(String operationType) {
            this.operationType = operationType;
        }

        /**
         * get 标识操作类型，是新增还是删除.
         * 
         * @return String
         */
        public String getOperationType() {
            return operationType;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", userId);
            map.put("projectId", projectId);
            map.put("operationType", StringUtils.trimToNull(operationType));
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
