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
package org.crown.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.crown.common.mybatisplus.LambdaDeleteWrapperChain;
import org.crown.common.mybatisplus.LambdaQueryWrapperChain;
import org.crown.common.mybatisplus.LambdaUpdateWrapperChain;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

/**
 * <p>
 * The basic Service inherits from Mybatis-plus
 * </p>
 *
 * @author Caratacus
 */
public interface BaseService<T> {

    Log logger = LogFactory.getLog(SqlHelper.class);

    /**
     * Batch size
     */
    int batchSize = 1024;

    /**
     * <p>
     * Insert a record (select field, strategy insert)
     * </p>
     *
     * @param entity Entity object
     */
    boolean save(T entity);

    /**
     * <p>
     * Insert (bulk)
     * </p>
     *
     * @param entityList Entity object collection
     */
    void saveBatch(Collection<T> entityList);

    /**
     * <p>
     * Batch edit and insert
     * </p>
     *
     * @param entityList Entity object collection
     */
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * <p>
     * Delete based on ID
     * </p>
     *
     * @param id Primary key ID
     */
    boolean removeById(Serializable id);

    /**
     * <p>
     * Delete all records
     * </p>
     */
    default boolean remove() {
        return remove(Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * Delete the record based on the entity condition
     * </p>
     *
     * @param queryWrapper Physical packaging {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    boolean remove(Wrapper<T> queryWrapper);

    /**
     * <p>
     * Select edit based on ID
     * </p>
     *
     * @param entity Entity object
     */
    boolean updateById(T entity);

    /**
     * <p>
     * Edit all based on ID
     * </p>
     *
     * @param entity Entity object
     */
    boolean alwaysUpdateSomeColumnById(T entity);

    /**
     * <p>
     * update the record based on the whereEntity condition
     * </p>
     *
     * @param updateWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    default boolean update(Wrapper<T> updateWrapper) {
        return update(null, updateWrapper);
    }

    /**
     * <p>
     * update the record based on the whereEntity condition
     * </p>
     *
     * @param entity        Entity object
     * @param updateWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * <p>
     * Batch update based on ID
     * </p>
     *
     * @param entityList Entity object collection
     */
    default boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, batchSize);
    }

    /**
     * <p>
     * Batch update based on ID
     * </p>
     *
     * @param entityList Entity object collection
     * @param batchSize  Update batch quantity
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * <p>
     * TableId An update record exists, whether to insert a record
     * </p>
     *
     * @param entity Entity object
     */
    boolean saveOrUpdate(T entity);

    /**
     * <p>
     * Query by ID
     * </p>
     *
     * @param id Primary key ID
     */
    T getById(Serializable id);

    /**
     * <p>
     * query a record based on Wrapper
     * </p>
     *
     * @param queryWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default T getOne(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(logger, list(queryWrapper));
    }

    /**
     * <p>
     * query a record based on Wrapper
     * </p>
     *
     * @param queryWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default <R> R getObj(Wrapper<T> queryWrapper, Function<? super Object, R> mapper) {
        return SqlHelper.getObject(logger, listObjs(queryWrapper, mapper));
    }

    /**
     * <p>
     * query the total number of records based on Wrapper conditions
     * </p>
     */
    default int count() {
        return count(Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * query whether the record exists based on Wrapper conditions
     * </p>
     *
     * @param queryWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default boolean exist(Wrapper<T> queryWrapper) {
        return count(queryWrapper) > 0;
    }

    /**
     * <p>
     * query whether the record does not exist based on Wrapper conditions
     * </p>
     *
     * @param queryWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default boolean nonExist(Wrapper<T> queryWrapper) {
        return !exist(queryWrapper);
    }

    /**
     * <p>
     * query the total number of records based on Wrapper conditions
     * </p>
     *
     * @param queryWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    int count(Wrapper<T> queryWrapper);

    /**
     * <p>
     * Query list
     * </p>
     */
    default List<T> list() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * <p>
     * Query list
     * </p>
     *
     * @param queryWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    List<T> list(Wrapper<T> queryWrapper);

    /**
     * <p>
     * query all records based on Wrapper conditions
     * </p>
     */
    default <R> List<R> listObjs(Function<? super Object, R> mapper) {
        return listObjs(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * <p>
     * query all records based on Wrapper conditions
     * </p>
     *
     * @param queryWrapper Entity object package operation class {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */

    <R> List<R> listObjs(Wrapper<T> queryWrapper, Function<? super Object, R> mapper);

    /**
     * <p>
     * query a custom object record based on Wrapper
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param mapper
     * @return
     */
    default <R> R entity(Wrapper<T> wrapper, Function<? super T, R> mapper) {
        return SqlHelper.getObject(logger, entitys(wrapper, mapper));
    }

    /**
     * <p>
     * Query the list of custom objects
     * </p>
     *
     * @param mapper
     * @return
     */
    default <R> List<R> entitys(Function<? super T, R> mapper) {
        return entitys(Wrappers.emptyWrapper(), mapper);
    }

    /**
     * <p>
     * Query the list of custom objects
     * </p>
     *
     * @param wrapper {@link Wrapper}
     * @param mapper
     * @return
     */
    <R> List<R> entitys(Wrapper<T> wrapper, Function<? super T, R> mapper);

    /**
     * Query the list, use an attribute of the object in the list as a key value, and convert it into a map
     * <p>
     *
     * @param column The attributes of the objects in the list, as the key value
     * @return 转换后的map
     */
    default <K> Map<K, T> list2Map(SFunction<T, K> column) {
        return list2Map(Wrappers.<T>emptyWrapper(), column);
    }

    /**
     * Query the list, use an attribute of the object in the list as a key value, and convert it into a map
     * <p>
     *
     * @param wrapper condition
     * @param column  The attributes of the objects in the list, as the key value
     * @return 转换后的map
     */
    <K> Map<K, T> list2Map(Wrapper<T> wrapper, SFunction<T, K> column);

    default LambdaQueryWrapperChain<T> query() {
        return new LambdaQueryWrapperChain<>(this);
    }

    default LambdaUpdateWrapperChain<T> update() {
        return new LambdaUpdateWrapperChain<>(this);
    }

    default LambdaDeleteWrapperChain<T> delete() {
        return new LambdaDeleteWrapperChain<>(this);
    }

}


