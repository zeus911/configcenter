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
import com.baidu.cc.configuration.bo.Project;
import com.baidu.lego.web.spi.EJsonDataResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 功能：工程列表页.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/project")
@Results({ @Result(name = "success", location = "/cc/project/listProject.vm")

})
public abstract class ProjectListActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = ProjectListAction.class)
    protected ProjectListActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public ProjectListActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new ProjectListActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(ProjectListActionBase.ReqParam reqParam) {
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
        if (v instanceof ProjectListActionBase.DataResult) {
            setData((ProjectListActionBase.DataResult) v);
        }
    }

    /** return data object. */
    protected ProjectListActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public ProjectListActionBase.DataResult getData() {
        if (data == null) {
            data = new ProjectListActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(ProjectListActionBase.DataResult data) {
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
    @Action("listProject")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
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
        ProjectListAction action = new ProjectListAction();
        action.setReqParam((ProjectListActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：工程名 模糊查询. */
        private String projectName;

        /**
         * set 工程名 模糊查询.
         * 
         * @param projectName
         *            String
         */
        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        /**
         * get 工程名 模糊查询.
         * 
         * @return String
         */
        public String getProjectName() {
            return projectName;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("projectName", StringUtils.trimToNull(projectName));
            return map;
        }
    }

    /**
     * Data return class.
     * 
     * @author BJF
     */
    public static class DataResult {

        /** 说明：工程列表. */
        private List<Project> projectList;

        /**
         * set 工程列表.
         * 
         * @param projectList
         *            List<Project>
         */
        public void setProjectList(List<Project> projectList) {
            this.projectList = projectList;
        }

        /**
         * get 工程列表.
         * 
         * @return List<Project>
         */
        public List<Project> getProjectList() {
            return projectList;
        }

    }

}
