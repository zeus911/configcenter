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
import com.baidu.cc.web.action.ConfigGroupDeleteActionBase.DataResult;
import com.baidu.lego.web.spi.EJsonDataResult;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 功能：删除群组.
 * 
 * @author BJF
 * @param <ReqParam>
 *            request param
 * @param <DataResult>
 *            data return result
 */
@Namespace("/configGroup")
@Results({ @Result(name = "success", type = "json")

})
public abstract class ConfigGroupDeleteActionBase<ReqParam, DataResult>
        extends
        com.baidu.lego.web.struts2.DirectServiceVisitAction<ReqParam, DataResult> {

    /** serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** request param. */
    @FromJSON(validationVisitor = ConfigGroupDeleteAction.class)
    protected ConfigGroupDeleteActionBase.ReqParam reqParam;

    /**
     * get request param.
     * 
     * @return ReqParam
     */
    public ConfigGroupDeleteActionBase.ReqParam getReqParam() {
        if (reqParam == null) {
            reqParam = new ConfigGroupDeleteActionBase.ReqParam();
        }
        return reqParam;
    }

    /**
     * set request param.
     * 
     * @param reqParam
     *            ReqParam
     */
    public void setReqParam(ConfigGroupDeleteActionBase.ReqParam reqParam) {
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
        if (v instanceof ConfigGroupDeleteActionBase.DataResult) {
            setData((ConfigGroupDeleteActionBase.DataResult) v);
        }
        setEJsonDataResult(v);
    }

    /** return data object. */
    protected ConfigGroupDeleteActionBase.DataResult data;

    /**
     * get return data object.
     * 
     * @return DataResult
     */
    public ConfigGroupDeleteActionBase.DataResult getData() {
        if (data == null) {
            data = new ConfigGroupDeleteActionBase.DataResult();
        }
        return data;
    }

    /**
     * set return data object.
     * 
     * @param data
     *            DataResult
     */
    protected void setData(ConfigGroupDeleteActionBase.DataResult data) {
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
    @Action("delGroup")
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
        ConfigGroupDeleteAction action = new ConfigGroupDeleteAction();
        action.setReqParam((ConfigGroupDeleteActionBase.ReqParam) target);
        return action;
    }

    /**
     * Request param class.
     * 
     * @author BJF
     */
    protected static class ReqParam {

        /** 说明：要删除的groupIds列表，逗号分隔. */
        private String groupIds;

        /**
         * set 要删除的groupIds列表，逗号分隔.
         * 
         * @param groupIds
         *            String
         */
        public void setGroupIds(String groupIds) {
            this.groupIds = groupIds;
        }

        /**
         * get 要删除的groupIds列表，逗号分隔.
         * 
         * @return String
         */
        public String getGroupIds() {
            return groupIds;
        }

        /**
         * get Map instance.
         * 
         * @return Map
         */
        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("groupIds", StringUtils.trimToNull(groupIds));
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
