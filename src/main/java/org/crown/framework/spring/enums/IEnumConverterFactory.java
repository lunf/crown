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
package org.crown.framework.spring.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import org.crown.common.utils.TypeUtils;
import org.crown.framework.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * <p>
 * Enumeration factory conversion class
 * </p>
 * https://blog.csdn.net/u014527058/article/details/62883573
 * https://www.cnblogs.com/xingele0917/p/3921492.html
 *
 * @author Caratacus
 */
public class IEnumConverterFactory implements ConverterFactory<String, IEnum> {

    /**
     * WeakHashMap is a hash table. The content it stores is also a key-value map, and both the key and the value can be null.
     * The keys of WeakHashMap are "weak keys". In WeakHashMap, when a key is no longer used normally, it will be automatically removed from WeakHashMap.
     * More precisely, for a given key, the existence of its mapping does not prevent the garbage collector from discarding the key, which makes the key terminable, terminated, and then recycled.
     * When a key is terminated, its corresponding key-value pair is effectively removed from the map.
     */
    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = converterMap.get(targetType);
        if (Objects.isNull(result)) {
            result = new IntegerStrToEnum<>(targetType);
            converterMap.put(targetType, result);
        }
        return result;
    }

    static class IntegerStrToEnum<T extends IEnum> implements Converter<String, T> {

        private final Map<String, T> enumMap = new HashMap<>();

        public IntegerStrToEnum(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();
            for (T e : enums) {
                enumMap.put(TypeUtils.castToString(e.getValue()), e);
            }
        }

        @Override
        public T convert(String source) {
            T result = enumMap.get(source);
            if (result == null) {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }
}
