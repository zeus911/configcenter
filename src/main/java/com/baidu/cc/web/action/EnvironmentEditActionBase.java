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
import com.baidu.cc.configuration.bo.Environment;
import com.baidu.cc.configuration.bo.Project;
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
 * 功能：导向环境编辑页.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/env")
@Results({ @Result(name = "success", location = "/cc/env/editEnv.vm")

})
public abstract class EnvironmentEditActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = EnvironmentEditAction.class)
    protected EnvironmentEditActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public EnvironmentEditActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new EnvironmentEditActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(EnvironmentEditActionBase.ReqParam reqParam) {
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
        if (v instanceof EnvironmentEditActionBase.DataResult) {
            setData((EnvironmentEditActionBase.DataResult) v);
        }
    }

    /** return data object. */
    protected EnvironmentEditActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public EnvironmentEditActionBase.DataResult getData() {
        if (data == null) {
            data = new EnvironmentEditActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(EnvironmentEditActionBase.DataResult data) {
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
    @Action("editEnv")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
    @Validations(regexFields = {
        @RegexFieldValidator(fieldName = "reqParam.projectId", 
            message = "字段\'projectId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$"),
        @RegexFieldValidator(fieldName = "reqParam.environmentId", 
            message = "字段\'environmentId\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$") })
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
        EnvironmentEditAction action = new EnvironmentEditAction();
        action.setReqParam((EnvironmentEditActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：. */
        private Long projectId;

        /** 说明：environment表主键. */
        private Long environmentId;

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
         * set environment表主键.
         * 
         * @param environmentId
         *            Long
         */
        public void setEnvironmentId(Long environmentId) {
            this.environmentId = environmentId;
        }

        /**
         * get environment表主键.
         * 
         * @return Long
         */
        public Long getEnvironmentId() {
            return environmentId;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectId", projectId);
            map.put("environmentId", environmentId);
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

    }

}
