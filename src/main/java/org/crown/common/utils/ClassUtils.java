package org.crown.common.utils;

import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.crown.framework.exception.Crown2Exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ClassUtils
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ClassUtils {

    /**
     * Initialize the instance
     *
     * @param clazz
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Create a new instance exception，Class：" + (clazz == null ? "null" : clazz.getName()), e);
        }
    }

    /**
     * Get Class by name
     *
     * @param clazz
     * @return
     */
    public static Class<?> forName(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            throw new Crown2Exception(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "无法根据名称获取Class，String class [" + clazz + "]", e);
        }
    }

    /**
     * Determine whether it is a proxy object
     *
     * @param clazz
     * @return
     */
    public static boolean isProxy(Class<?> clazz) {
        if (clazz != null) {
            for (Class<?> cls : clazz.getInterfaces()) {
                String interfaceName = cls.getName();
                //cglib
                if ("net.sf.cglib.proxy.Factory".equals(interfaceName)
                        || "org.springframework.cglib.proxy.Factory".equals(interfaceName)
                        //javassist
                        || "javassist.util.proxy.ProxyObject".equals(interfaceName)
                        || "org.apache.ibatis.javassist.util.proxy.ProxyObject".equals(interfaceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the class of the current object
     *
     * @param clazz
     * @return
     */
    public static Class<?> getUserClass(Class<?> clazz) {
        return isProxy(clazz) ? clazz.getSuperclass() : clazz;
    }

    /**
     * Get the class of the current object
     *
     * @param object
     * @returna
     */
    public static Class<?> getUserClass(Object object) {
        if (Objects.isNull(object)) {
            throw new Crown2Exception(HttpServletResponse.SC_BAD_REQUEST, "实例不能为空");
        }
        return getUserClass(object.getClass());
    }

}
