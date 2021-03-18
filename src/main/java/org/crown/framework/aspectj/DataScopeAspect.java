package org.crown.framework.aspectj;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.crown.common.annotation.DataScope;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.web.domain.BaseEntity;
import org.crown.project.system.role.domain.Role;
import org.crown.project.system.user.domain.User;
import org.springframework.stereotype.Component;

/**
 * Data filtering processing
 *
 * @author Crown
 */
@Aspect
@Component
public class DataScopeAspect {

    /**
     * All data permissions
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * Custom data permissions
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * Departmental data permissions
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * Department and below data permissions
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * Personal data access only
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * Data permission filtering keywords
     */
    public static final String DATA_SCOPE = "dataScope";

    // Configure weaving point
    @Pointcut("@annotation(org.crown.common.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // Get comments
        DataScope controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null) {
            return;
        }
        // Get current user
        User currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // If you are a super administrator, do not filter data
            if (!currentUser.isAdmin()) {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(),
                        controllerDataScope.userAlias());
            }
        }
    }

    /**
     * Data range filtering
     *
     * @param joinPoint Cut-off point
     * @param user      User
     * @param deptAlias Department alias
     * @param userAlias User alias
     */
    public static void dataScopeFilter(JoinPoint joinPoint, User user, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();

        for (Role role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias,
                        role.getRoleId()));
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.dept_id = {} ", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                sqlString.append(StringUtils.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        deptAlias, user.getDeptId(), user.getDeptId()));
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (StringUtils.isNotBlank(userAlias)) {
                    sqlString.append(StringUtils.format(" OR {}.user_id = {} ", userAlias, user.getUserId()));
                } else {
                    sqlString.append(StringUtils.format(" OR {}.dept_id IS NULL ", deptAlias));
                }
            }
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[0];
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
        }
    }

    /**
     * Whether there is a comment, if it exists, get it
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }
}
