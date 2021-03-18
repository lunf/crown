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
package org.crown.framework.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * BaseMapper Based on MP cut
 *
 * @author Caratacus
 * @see com.baomidou.mybatisplus.core.mapper.BaseMapper
 */
public interface BaseMapper<T> {

    /**
     * <p>
     * Insert a record
     * </p>
     *
     * @param entity Entity object
     */
    int insert(T entity);

    /**
     * <p>
     * Delete based on ID
     * </p>
     *
     * @param id Primary key ID
     */
    int deleteById(Serializable id);

    /**
     * <p>
     * Delete the record according to the entity condition
     * </p>
     *
     * @param queryWrapper The entity object encapsulates the operation class (may be null)
     */
    int delete(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * <p>
     * Modify according to ID
     * </p>
     *
     * @param entity Entity object
     */
    int updateById(@Param(Constants.ENTITY) T entity);

    /**
     * <p>
     * Modify according to ID
     * </p>
     *
     * @param entity Entity object
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    /**
     * <p>
     * Update the record according to the whereEntity condition
     * </p>
     *
     * @param entity        Entity object (set condition value, cannot be null)
     * @param updateWrapper The entity object encapsulates the operation class (it can be null, and the entity inside is used to generate the where statement)
     */
    int update(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);

    /**
     * <p>
     * Query by ID
     * </p>
     *
     * @param id Primary key ID
     */
    T selectById(Serializable id);

    /**
     * <p>
     * Query the total number of records according to Wrapper conditions
     * </p>
     *
     * @param queryWrapper Entity object
     */
    Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * <p>
     * Query all records according to entity condition
     * </p>
     *
     * @param queryWrapper The entity object encapsulates the operation class (may be null)
     */
    List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     * <p>
     * Query all records according to Wrapper condition
     * Note: Only return the value of the first field
     * </p>
     *
     * @param queryWrapper The entity object encapsulates the operation class (may be null)
     */
    List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

}
