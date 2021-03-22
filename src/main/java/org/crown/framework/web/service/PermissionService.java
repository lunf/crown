package org.crown.framework.web.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * js calls thymeleaf to realize button permission visibility
 *
 * @author Crown
 */
@Service("permission")
public class PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    public String hasPermi(String permission) {
        return isPermittedOperator(permission) ? "" : "hidden";
    }

    public String hasRole(String role) {
        return hasRoleOperator(role) ? "" : "hidden";
    }

    /**
     * Determine whether the user has a certain permission
     *
     * @param permission Permission string
     * @return result
     */
    private boolean isPermittedOperator(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * Determine whether the user has a certain role
     *
     * @param role Role string
     * @return result
     */
    private boolean hasRoleOperator(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    /**
     * Return user attribute value
     *
     * @param property Attribute name
     * @return User attribute value
     */
    public Object getPrincipalProperty(String property) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Object principal = subject.getPrincipal();
            try {
                BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
                for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                    if (pd.getName().equals(property)) {
                        return pd.getReadMethod().invoke(principal, (Object[]) null);
                    }
                }
            } catch (Exception e) {
                log.error("Error reading property [{}] from principal of type [{}]", property,
                        principal.getClass().getName());
            }
        }
        return null;
    }
}
