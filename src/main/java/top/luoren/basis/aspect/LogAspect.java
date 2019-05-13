package top.luoren.basis.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.luoren.basis.annotation.Log;
import top.luoren.basis.entity.SysLog;
import top.luoren.basis.mapper.SysLogMapper;
import top.luoren.basis.util.HttpContextUtil;
import top.luoren.basis.util.JwtTokenUtil;
import top.luoren.basis.util.RespBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author luoren
 * @date 2019-05-05 09:17
 */

@Aspect
@Component
public class LogAspect {
    @Autowired
    SysLogMapper sysLogMapper;
    @Autowired
    JwtTokenUtil tokenUtil;

    @Pointcut("@annotation(top.luoren.basis.annotation.Log)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public RespBody around(ProceedingJoinPoint point) {
        long beninTime = System.currentTimeMillis();
        RespBody respBody = RespBody.ok();
        try {
            Object obj = point.proceed();
            if (!ObjectUtils.isEmpty(obj)) {
                respBody = (RespBody) obj;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - beninTime;

        saveLog(point, respBody, time);
        return respBody;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, RespBody respBody, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            //注解描述
            sysLog.setOperation(logAnnotation.value());
        }
        //类名
        String className = joinPoint.getTarget().getClass().getName();
        //方法名
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName);
        //请求方法的参数
        Object[] args = joinPoint.getArgs();
        //请求方法的参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuffer params = new StringBuffer();
            for (int i = 0; i < args.length; i++) {
                params.append(" ").append(paramNames[i]).append(": ").append(args[i]);
            }
            sysLog.setParams(params.toString());
        }
        //获取request
        HttpServletRequest request= HttpContextUtil.getHttpServletRequest();
        sysLog.setIp(HttpContextUtil.getIpAddr(request));
        //获取用户名
        String token=request.getHeader("token");
        String username=tokenUtil.getUsernameFromToken(token);
        sysLog.setUsername(username);
        sysLog.setMessage((String) respBody.get("msg"));
        sysLog.setCode((String) respBody.get("code"));
        sysLog.setTime((int) time);
        sysLog.setCreateTime(LocalDateTime.now());
        sysLogMapper.insert(sysLog);
    }

}
