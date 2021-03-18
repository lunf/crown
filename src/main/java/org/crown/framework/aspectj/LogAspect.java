package org.crown.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.crown.common.annotation.Log;
import org.crown.common.cons.APICons;
import org.crown.common.cons.Constants;
import org.crown.common.utils.JsonUtils;
import org.crown.common.utils.StringUtils;
import org.crown.common.utils.security.ShiroUtils;
import org.crown.framework.manager.ThreadExecutors;
import org.crown.framework.manager.factory.TimerTasks;
import org.crown.framework.spring.ApplicationUtils;
import org.crown.framework.utils.LogUtils;
import org.crown.project.monitor.operlog.domain.OperLog;
import org.crown.project.system.user.domain.User;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Operation log record processing
 *
 * @author Crown
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    // Configure weaving point
    @Pointcut("execution(public * org.crown.project..*.*Controller.*(..))")
    public void logPointCut() {
    }

    /**
     * Execute after processing the request
     *
     * @param joinPoint Cut-off point
     * @param ret       Return value
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        handleLog(joinPoint, ret, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint Cut-off point
     * @param e         abnormal
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, null, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Object ret, final Exception e) {
        try {
            // Setting method name
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String actionMethod = className + "." + methodName + "()";
            ApplicationUtils.getRequest().setAttribute(APICons.API_ACTION_METHOD, actionMethod);
            String requestURI = (String) ApplicationUtils.getRequest().getAttribute(APICons.API_REQURL);
            LogUtils.doAfterReturning(ret);
            // Get comments
            Method method = getMethod(joinPoint);
            Log controllerLog = getAnnotationLog(method);
            if (controllerLog == null) {
                return;
            }

            // Get current user
            User currentUser = ShiroUtils.getSysUser();

            // *========Database log=========*//
            OperLog operLog = new OperLog();
            operLog.setStatus(Constants.SUCCESS);
            // Requested address
            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);
            operLog.setOperUrl(requestURI);
            if (currentUser != null) {
                operLog.setOperName(currentUser.getLoginName());
                if (StringUtils.isNotNull(currentUser.getDept())
                        && StringUtils.isNotEmpty(currentUser.getDept().getDeptName())) {
                    operLog.setDeptName(currentUser.getDept().getDeptName());
                }
            }

            if (e != null) {
                operLog.setStatus(Constants.FAIL);
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            operLog.setMethod(actionMethod);
            // Process the parameters on the setting annotations
            getControllerMethodDescription(controllerLog, operLog);
            // Save the database
            ThreadExecutors.execute(TimerTasks.recordOper(operLog));
        } catch (Exception exp) {
            // Record local exception log
            log.error("==Pre-notification exception==");
            log.error("Exception information:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * Get the description of the method in the annotation for the Controller layer annotation
     *
     * @param log     日志
     * @param operLog 操作日志
     */
    public void getControllerMethodDescription(Log log, OperLog operLog) {
        // Set action
        operLog.setBusinessType(log.businessType().ordinal());
        // Set title
        operLog.setTitle(log.title());
        // Set operator category
        operLog.setOperatorType(log.operatorType().ordinal());
        // Do you need to save the request, parameters and values
        if (log.isSaveRequestData()) {
            // Get the parameter information and transfer it to the database.
            setRequestValue(operLog);
        }
    }

    /**
     * Get the requested parameters and put them in the log
     *
     * @param operLog
     */
    private void setRequestValue(OperLog operLog) {
        Map<String, String[]> map = ApplicationUtils.getRequest().getParameterMap();
        String params = JsonUtils.toJson(map);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }

    /**
     * Whether there is a comment, if it exists, get it
     */
    private Log getAnnotationLog(Method method) {
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * Get Method
     *
     * @param joinPoint
     * @return
     */
    private Method getMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }
}
