/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.framework.web.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.crown.common.cons.PageCons;
import org.crown.common.utils.DateUtils;
import org.crown.common.utils.sql.AntiSQLFilter;
import org.crown.framework.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;

import lombok.extern.slf4j.Slf4j;

/**
 * SuperController
 *
 * @author Caratacus
 */
@Slf4j
public class SuperController<Entity> implements PageCons {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * The date format string passed from the front desk is automatically converted to Date type
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date Type conversion
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
        // JSONObject Type conversion
        binder.registerCustomEditor(JSONObject.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(JSON.parseObject(text));
            }
        });
        // JSONArray Type conversion
        binder.registerCustomEditor(JSONArray.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(JSON.parseArray(text));
            }
        });
    }

    /**
     * Successfully returned
     *
     * @param object
     * @return
     */
    public <T> ApiResponses<T> success(T object) {
        return ApiResponses.<T>success(response, object);
    }

    /**
     * Successfully returned
     *
     * @return
     */
    public ApiResponses<Void> success() {
        return success(HttpStatus.OK);
    }

    /**
     * Successfully returned
     *
     * @param status
     * @param object
     * @return
     */
    public <T> ApiResponses<T> success(HttpStatus status, T object) {
        return ApiResponses.<T>success(response, status, object);
    }

    /**
     * Successfully returned
     *
     * @param status
     * @return
     */
    public ApiResponses<Void> success(HttpStatus status) {
        return ApiResponses.<Void>success(response, status);
    }

    /**
     * Get the sorted table
     */
    protected String getAlias() {
        try {
            Class<Entity> entityClass = (Class<Entity>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
            return TableInfoHelper.getTableInfo(entityClass).getTableName();
        } catch (Exception e) {
            log.warn("Error getting sort alias:{}", e.getMessage());
            return null;
        }
    }

    /**
     * Get security parameters (SQL ORDER BY filtering)
     *
     * @param parameter
     * @return
     */
    protected String getParameterSafeValue(String parameter) {
        return AntiSQLFilter.getSafeValue(request.getParameter(parameter));
    }
}
