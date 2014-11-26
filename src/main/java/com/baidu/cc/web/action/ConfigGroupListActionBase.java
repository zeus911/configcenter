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
import com.baidu.cc.configuration.bo.ConfigGroup;
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.bo.Project;
import com.baidu.cc.configuration.bo.Version;
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
 * 功能：分组列表页.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/configGroup")
@Results({ @Result(name = "success", location = "/cc/configGroup/listConfigGroup.vm")

})
public abstract class ConfigGroupListActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = ConfigGroupListAction.class)
    protected ConfigGroupListActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public ConfigGroupListActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new ConfigGroupListActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(ConfigGroupListActionBase.ReqParam reqParam) {
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
        if (v instanceof ConfigGroupListActionBase.DataResult) {
            setData((ConfigGroupListActionBase.DataResult) v);
        }
    }

    /** return data object. */
    protected ConfigGroupListActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public ConfigGroupListActionBase.DataResult getData() {
        if (data == null) {
            data = new ConfigGroupListActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(ConfigGroupListActionBase.DataResult data) {
        this.data = data;
    }

    /** return data object of e-json wrapped. */
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

    // step1 main entry to define action name
    /**
     * do execute service.
     * 
     * @return result name
     * @throws Exception
     *             throws all available exception
     */
    @Action("listConfigGroup")
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
        ConfigGroupListAction action = new ConfigGroupListAction();
        action.setReqParam((ConfigGroupListActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：versionId. */
        private Long versionId;

        /**
         * set versionId.
         * 
         * @param versionId
         *            Long
         */
        public void setVersionId(Long versionId) {
            this.versionId = versionId;
        }

        /**
         * get versionId.
         * 
         * @return Long
         */
        public Long getVersionId() {
            return versionId;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("versionId", versionId);
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
        private Project project;

        /** 说明：. */
        private Environment env;

        /** 说明：. */
        private Version version;

        /** 说明：. */
        private List<ConfigGroup> configGroupList;

        /**
         * set .
         * 
         * @param project
         *            Project
         */
        public void setProject(Project project) {
            this.project = project;
        }

        /**
         * get .
         * 
         * @return Project
         */
        public Project getProject() {
            return project;
        }

        /**
         * set .
         * 
         * @param env
         *            Environment
         */
        public void setEnv(Environment env) {
            this.env = env;
        }

        /**
         * get .
         * 
         * @return Environment
         */
        public Environment getEnv() {
            return env;
        }

        /**
         * set .
         * 
         * @param version
         *            Version
         */
        public void setVersion(Version version) {
            this.version = version;
        }

        /**
         * get .
         * 
         * @return Version
         */
        public Version getVersion() {
            return version;
        }

        /**
         * set .
         * 
         * @param configGroupList
         *            List<ConfigGroup>
         */
        public void setConfigGroupList(List<ConfigGroup> configGroupList) {
            this.configGroupList = configGroupList;
        }

        /**
         * get .
         * 
         * @return List<ConfigGroup>
         */
        public List<ConfigGroup> getConfigGroupList() {
            return configGroupList;
        }

    }

}
