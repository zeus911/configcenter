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
import com.baidu.cc.configuration.bo.AccessSetting;
import com.baidu.cc.configuration.bo.User;
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
 * 功能：查看用户授权信息页面.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/user")
@Results({ @Result(name = "success", location = "/cc/user/listaccess.vm")

})
public abstract class UserAccessListActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = UserAccessListAction.class)
    protected UserAccessListActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public UserAccessListActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new UserAccessListActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(UserAccessListActionBase.ReqParam reqParam) {
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
        if (v instanceof UserAccessListActionBase.DataResult) {
            setData((UserAccessListActionBase.DataResult) v);
        }
    }

    /** return data object. */
    protected UserAccessListActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public UserAccessListActionBase.DataResult getData() {
        if (data == null) {
            data = new UserAccessListActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(UserAccessListActionBase.DataResult data) {
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
    @Action("listAccess")
    public String doExecute() throws Exception {
        return execute();
    }

    /**
     * do interal validaotr. do not override it
     */
    @Validations(regexFields = { @RegexFieldValidator(fieldName = "reqParam.id", 
        message = "字段\'id\'必须为整数类型", expression = "^(\\+|-)?[0-9]\\d*$") })
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
        UserAccessListAction action = new UserAccessListAction();
        action.setReqParam((UserAccessListActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：用户id. */
        private Long id;

        /**
         * set 用户id.
         * 
         * @param id
         *            Long
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * get 用户id.
         * 
         * @return Long
         */
        public Long getId() {
            return id;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            return map;
        }
    }

    /**
     * Data return class.
     * 
     * @author BJF
     */
    public static class DataResult {

        /** 说明：用户信息. */
        private User user;

        /** 说明：用户已授权信息列表. */
        private List<AccessSetting> accessSettings;

        /**
         * set 用户信息.
         * 
         * @param user
         *            User
         */
        public void setUser(User user) {
            this.user = user;
        }

        /**
         * get 用户信息.
         * 
         * @return User
         */
        public User getUser() {
            return user;
        }

        /**
         * set 用户已授权信息列表.
         * 
         * @param accessSettings
         *            List<AccessSetting>
         */
        public void setAccessSettings(List<AccessSetting> accessSettings) {
            this.accessSettings = accessSettings;
        }

        /**
         * get 用户已授权信息列表.
         * 
         * @return List<AccessSetting>
         */
        public List<AccessSetting> getAccessSettings() {
            return accessSettings;
        }

    }

}
