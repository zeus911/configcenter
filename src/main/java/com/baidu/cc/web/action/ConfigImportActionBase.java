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
import com.baidu.cc.web.action.ConfigImportActionBase.DataResult;
import com.baidu.lego.web.spi.EJsonDataResult;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 功能：复制功能接口.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/import")
@Results({ @Result(name = "success", type = "json")

})
public abstract class ConfigImportActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = ConfigImportAction.class)
    protected ConfigImportActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public ConfigImportActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new ConfigImportActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(ConfigImportActionBase.ReqParam reqParam) {
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
        if (v instanceof ConfigImportActionBase.DataResult) {
            setData((ConfigImportActionBase.DataResult) v);
        }
        setEJsonDataResult(v);
    }

    /** return data object. */
    protected ConfigImportActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public ConfigImportActionBase.DataResult getData() {
        if (data == null) {
            data = new ConfigImportActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(ConfigImportActionBase.DataResult data) {
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
    @Action("importConfig")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
    @Validations(regexFields = {
        @RegexFieldValidator(fieldName = "reqParam.srcProjectId", 
            message = "字段\'projectId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.destProjectId", 
            message = "字段\'destProjectId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.srcEnvId", 
            message = "字段\'srcEnvId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.destEnvId", 
            message = "字段\'destEnvId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.srcVersionId", 
            message = "字段\'srcVersionId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.destVersionId", 
            message = "字段\'destVersionId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.srcConfigGroupId", 
            message = "字段\'srcConfigGroupId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.destConfigGroupId", 
            message = "字段\'destConfigGroupId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$") })
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
        ConfigImportAction action = new ConfigImportAction();
        action.setReqParam((ConfigImportActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：. */
        private Long srcProjectId;

        /** 说明：. */
        private Long destProjectId;

        /** 说明：. */
        private Long srcEnvId;

        /** 说明：. */
        private Long destEnvId;

        /** 说明：. */
        private Long srcVersionId;

        /** 说明：. */
        private Long destVersionId;

        /** 说明：. */
        private Long srcConfigGroupId;

        /** 说明：. */
        private Long destConfigGroupId;

        /**
         * set .
         * 
         * @param srcProjectId
         *            Long
         */
        public void setSrcProjectId(Long srcProjectId) {
            this.srcProjectId = srcProjectId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getSrcProjectId() {
            return srcProjectId;
        }

        /**
         * set .
         * 
         * @param destProjectId
         *            Long
         */
        public void setDestProjectId(Long destProjectId) {
            this.destProjectId = destProjectId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getDestProjectId() {
            return destProjectId;
        }

        /**
         * set .
         * 
         * @param srcEnvId
         *            Long
         */
        public void setSrcEnvId(Long srcEnvId) {
            this.srcEnvId = srcEnvId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getSrcEnvId() {
            return srcEnvId;
        }

        /**
         * set .
         * 
         * @param destEnvId
         *            Long
         */
        public void setDestEnvId(Long destEnvId) {
            this.destEnvId = destEnvId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getDestEnvId() {
            return destEnvId;
        }

        /**
         * set .
         * 
         * @param srcVersionId
         *            Long
         */
        public void setSrcVersionId(Long srcVersionId) {
            this.srcVersionId = srcVersionId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getSrcVersionId() {
            return srcVersionId;
        }

        /**
         * set .
         * 
         * @param destVersionId
         *            Long
         */
        public void setDestVersionId(Long destVersionId) {
            this.destVersionId = destVersionId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getDestVersionId() {
            return destVersionId;
        }

        /**
         * set .
         * 
         * @param srcConfigGroupId
         *            Long
         */
        public void setSrcConfigGroupId(Long srcConfigGroupId) {
            this.srcConfigGroupId = srcConfigGroupId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getSrcConfigGroupId() {
            return srcConfigGroupId;
        }

        /**
         * set .
         * 
         * @param destConfigGroupId
         *            Long
         */
        public void setDestConfigGroupId(Long destConfigGroupId) {
            this.destConfigGroupId = destConfigGroupId;
        }

        /**
         * get .
         * 
         * @return Long
         */
        public Long getDestConfigGroupId() {
            return destConfigGroupId;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("srcProjectId", srcProjectId);
            map.put("destProjectId", destProjectId);
            map.put("srcEnvId", srcEnvId);
            map.put("destEnvId", destEnvId);
            map.put("srcVersionId", srcVersionId);
            map.put("destVersionId", destVersionId);
            map.put("srcConfigGroupId", srcConfigGroupId);
            map.put("destConfigGroupId", destConfigGroupId);
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
