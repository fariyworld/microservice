package com.mace.microservice.common.base;

import com.mace.microservice.common.annotation.EnableControllerLog;
import com.mace.microservice.common.entity.User;
import com.mace.microservice.common.util.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.*;

/**
 * description: web请求 aop统一日志
 * <br />
 * Created by mace on 20:47 2018/5/28.
 */
@Order(0)//index的值越小，所对应的优先级就越高
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    //解决 AOP 切面中的同步问题
    ThreadLocal<Long> START_TIME = new ThreadLocal<Long>();
    ThreadLocal<Long> END_TIME = new ThreadLocal<Long>();
    ThreadLocal<String> requestId = new ThreadLocal<String>();

    // 带有注解的方法
    @Pointcut("@annotation(com.mace.microservice.common.annotation.EnableControllerLog)")
    public void pt() {
    }


    //前置通知
    @Before(value = "pt()")
    public void doBefore(JoinPoint joinPoint){

        requestId.set(StringHelper.getUUIDString());
        START_TIME.set(System.currentTimeMillis());
//        requestId = StringHelper.getUUIDString();
//        START_TIME = System.currentTimeMillis();

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();
        //读取session中的用户
        HttpSession session = request.getSession();
        String username = "";
        SecurityContextImpl spring_security_context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if(spring_security_context != null){
            username = spring_security_context.getAuthentication().getName();
//            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
//        User user = (User) session.getAttribute(Constant.CURRENT_USER);
//        if(user != null)    username = user.getUsername();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = "";
//        if(authentication != null){
//            username = authentication.getName();//anonymousUser 或者 真实用户名
//        }
//        Enumeration<String> attributeNames = session.getAttributeNames();
//        while(attributeNames.hasMoreElements()){
//            log.info(attributeNames.nextElement());
//        }

        username = username.equals("") ? "<>" : username;

        log.info("=========================请求 start======================================");
        log.info("requestId : " + requestId.get());
        log.info("请求人 : " + username);
        log.info("IP : " + getClientIp(request));
        log.info("URL : "+ getRequestUrl(request));
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName());
        log.info("METHOD_DESCRIPTION : " + getControllerMethodDescription(joinPoint));

        /*==========数据库日志=========*/

        Map<String, String[]> parameterMap = request.getParameterMap();
        if ( parameterMap.size() > 0 ){
            log.info("request参数===========================================start");
            Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
            for(Map.Entry<String, String[]> entry:entrySet){
                String key = entry.getKey();
                if(key.equals("password")){
                    log.info("name: { "+key+" }, value: {  }");
                }else{
                    log.info("name: { "+key+" }, value: { "+request.getParameter(key)+" }");
                }
            }
            log.info("request参数===========================================end");
        }

        if(!Arrays.toString(joinPoint.getArgs()).equals("[{}]") && !Arrays.toString(joinPoint.getArgs()).equals("[]")){
            log.info("URL_PATH参数: {}",Arrays.toString(joinPoint.getArgs()));
        }
    }

    //后置通知
    @After("pt()")
    public void doAfter(JoinPoint joinPoint){
    }

    //异常通知
    @AfterThrowing(throwing = "ex", pointcut = "pt()")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex){

        END_TIME.set(System.currentTimeMillis());

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        request.setAttribute("requestId", requestId.get());
        request.setAttribute("START_TIME", START_TIME.get());
        request.setAttribute("END_TIME", END_TIME.get());


        log.error("异常信息: {}",ex.getMessage());
        log.error("异常堆栈: {}",ex.getStackTrace()[0]);
        log.info("=========================请求 异常 end======================================");
    }

    //返回通知
    @AfterReturning(returning = "result",pointcut = "pt()")
    public void doAfterReturning(Object result){
        // 处理完请求，返回内容
        END_TIME.set(System.currentTimeMillis());

        if(result instanceof RestPackResponse){

            RestPackResponse response = (RestPackResponse) result;

            response.setRequestId(requestId.get());
            response.setServerTime(new Date(START_TIME.get()));
            response.setSpendTime((END_TIME.get() - START_TIME.get()));
        }

        log.info("RESPONSE_TYPE : " + result.getClass());
        log.info("RESPONSE : " + result);
        log.info("=========================请求 end======================================");
        System.out.println("\t");
    }

    //环绕通知
    //@Around(value = "pt()")
    public Object doAround(ProceedingJoinPoint pjd){
        Object result =null;

        try {
            // 环绕前

            // 前置通知

            // 执行被代理的方法
            result = pjd.proceed();

            // 环绕后

        } catch (Throwable throwable) {
            // 异常通知 (如果有返回通知，则在返回通知之后执行)
            System.out.println(throwable.getMessage());
        }

        //后置通知

        // 返回通知

        return result;
    }

    public static String getClientIp(HttpServletRequest request){

        String remoteAddr = StringUtils.EMPTY;
        if(request!=null){
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if(remoteAddr==null || "".equals(remoteAddr)){
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }


    public static String getRequestUrl(HttpServletRequest request){

        String url = StringUtils.EMPTY;

        try {
            url =  URLDecoder.decode(request.getRequestURL().toString(), Constant.DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    private  String getControllerMethodDescription(JoinPoint joinPoint){

        String description = StringUtils.EMPTY;

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        // 类上的注解
        if( targetClass.isAnnotationPresent(EnableControllerLog.class) ){
            EnableControllerLog annotation = (EnableControllerLog) targetClass.getAnnotation(EnableControllerLog.class);
            description = annotation.description() + "-";
        }

        // 方法上的注解
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                if(method.isAnnotationPresent(EnableControllerLog.class)){
                    int parameterCount = method.getParameterCount();// 参数个数 2
//                    Class[] clazzs = method.getParameterTypes();  // clazzs.length
                    if (parameterCount == arguments.length) {
                        description += method.getAnnotation(EnableControllerLog.class).description();
                        break;
                    }
                }
                break;
            }
        }
        return description;
    }
}
