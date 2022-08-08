package org.cmyk.aifocus.aspect;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Aspect
@Component
@Slf4j
public class AutoLogAspect {
    /**
     * 换行符
     */
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private StringBuilder stringBuilder;

    public static String getLocalHostName() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException ignored) {
            return "error";
        }
        return addr.getHostName();
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 以swagger @Operation 注解为切点
     */
    @Pointcut("@annotation(io.swagger.v3.oas.annotations.Operation)")
    public void webLog() {
    }

    /**
     * 在切点之前织入
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        stringBuilder = new StringBuilder();
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 获取 @WebLog 注解的描述信息
        String methodDescription;
        try {
            methodDescription = getAspectLogDescription(joinPoint);
        } catch (Exception e) {
            return;
        }

        // 打印请求相关参数
        stringBuilder.append("\n========================================== Start ==========================================").append(LINE_SEPARATOR);
        // 打印请求 url
        stringBuilder.append("URL            : ").append(request.getRequestURL().toString()).append(LINE_SEPARATOR);
        // 打印描述信息
        stringBuilder.append("Description    : ").append(methodDescription).append(LINE_SEPARATOR);
        // 打印 Http method
        stringBuilder.append("HTTP Method    : ").append(request.getMethod()).append(LINE_SEPARATOR);
        // 打印调用 controller 的全路径以及执行方法
        stringBuilder.append("Class Method   : ").append(joinPoint.getSignature().getDeclaringTypeName()).append('.').append(joinPoint.getSignature().getName()).append(LINE_SEPARATOR);
        // 打印请求的 IP
        stringBuilder.append("Remote IP      : ").append(getClientIp(request)).append(LINE_SEPARATOR);
        // 当前处理之后
        stringBuilder.append("Process Machine: ").append(getLocalHostName()).append(LINE_SEPARATOR);

        getLocalHostName();

        // 打印请求入参
        stringBuilder.append("Request Args   : ").append(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                .toJson(joinPoint.getArgs())).append(LINE_SEPARATOR);
    }

    /**
     * 在切点之后织入
     */
    @After("webLog()")
    public void doAfter() {
        // 接口结束后换行，方便分割查看
        stringBuilder.append("=========================================== End ===========================================");
        log.info(stringBuilder.toString().replace("\\", ""));
    }

    /**
     * 环绕
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        stringBuilder.append("Response Args  : ").append(new Gson().toJson(result)).append(LINE_SEPARATOR);
        // 执行耗时
        stringBuilder.append("Time-Consuming : ").append(System.currentTimeMillis() - startTime).append(" ms").append(LINE_SEPARATOR);
        return result;
    }


    /**
     * 获取切面注解的描述
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description
                            .append(methodName)
                            .append(" ")
                            .append(method.getAnnotation(Operation.class).summary());
                    break;
                }
            }
        }
        return description.toString();
    }
}