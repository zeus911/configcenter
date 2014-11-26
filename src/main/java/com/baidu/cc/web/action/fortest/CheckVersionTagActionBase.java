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

package com.baidu.cc.web.action.fortest;

import com.baidu.bjf.web.struts2.annotation.FromJSON;
import com.baidu.bjf.web.struts2.annotation.ToJSON;
import com.baidu.cc.web.action.fortest.CheckVersionTagActionBase.DataResult;
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
 * 功能：压力测试专用 - 校验版本的tag号是否修改.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/fortest")
@Results({ @Result(name = "success", type = "json")

})
public abstract class CheckVersionTagActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = CheckVersionTagAction.class)
    protected CheckVersionTagActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public CheckVersionTagActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new CheckVersionTagActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(CheckVersionTagActionBase.ReqParam reqParam) {
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
        if (v instanceof CheckVersionTagActionBase.DataResult) {
            setData((CheckVersionTagActionBase.DataResult) v);
        }
        setEJsonDataResult(v);
    }

    /** return data object. */
    protected CheckVersionTagActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public CheckVersionTagActionBase.DataResult getData() {
        if (data == null) {
            data = new CheckVersionTagActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(CheckVersionTagActionBase.DataResult data) {
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
    @Action("checkVersionTag")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
    @Validations(regexFields = { @RegexFieldValidator(fieldName = "reqParam.versionId", 
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
        CheckVersionTagAction action = new CheckVersionTagAction();
        action.setReqParam((CheckVersionTagActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：. */
        private String username;

        /** 说明：. */
        private String password;

        /** 说明：. */
        private Long versionId;

        /** 说明：. */
        private String tag;

        /**
         * set .
         * 
         * @param username
         *            String
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * get .
         * 
         * @return String
         */
        public String getUsername() {
            return username;
        }

        /**
         * set .
         * 
         * @param password
         *            String
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * get .
         * 
         * @return String
         */
        public String getPassword() {
            return password;
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
         * set .
         * 
         * @param tag
         *            String
         */
        public void setTag(String tag) {
            this.tag = tag;
        }

        /**
         * get .
         * 
         * @return String
         */
        public String getTag() {
            return tag;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", StringUtils.trimToNull(username));
            map.put("password", StringUtils.trimToNull(password));
            map.put("versionId", versionId);
            map.put("tag", StringUtils.trimToNull(tag));
            return map;
        }
    }

    /**
     * Data return class.
     * 
     * @author BJF
     */
    public static class DataResult {

        /** 说明：. */
        private Boolean result;

        /**
         * set .
         * 
         * @param result
         *            Boolean
         */
        public void setResult(Boolean result) {
            this.result = result;
        }

        /**
         * get .
         * 
         * @return Boolean
         */
        public Boolean getResult() {
            return result;
        }

    }

}
