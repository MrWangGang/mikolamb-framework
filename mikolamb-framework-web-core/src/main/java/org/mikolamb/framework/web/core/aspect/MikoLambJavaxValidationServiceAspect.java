package org.mikolamb.framework.web.core.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.util.sample.ValidatorUtil;
import org.mikolamb.framework.web.core.annotation.MikoLambValid;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000099;

/**
 * @description: service层开启使用javax注解来校验参数
 * @author: Mr.WangGang
 * @create: 2018-11-23 下午 2:49
 **/
@Aspect
@Component
public class MikoLambJavaxValidationServiceAspect {
    @Pointcut("@annotation(org.mikolamb.framework.web.core.annotation.MikoLambValid)")
    public void serviceValid() {

    }
    @Before("serviceValid()")
    public void Interceptor(JoinPoint joinpoint) {
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        MikoLambValid mikoLambValid = method.getDeclaredAnnotation(MikoLambValid.class);
        if(mikoLambValid == null){
            return;
        }
        Annotation[][] argAnnotations = method.getParameterAnnotations();
        Object[] args = joinpoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : argAnnotations[i]) {
                if (Valid.class.isInstance(annotation)) {
                    if(args[i] == null){
                        throw new MikoLambEventException(ES00000099);
                    }
                    ValidatorUtil.validate(args[i]);
                }else if(NotNull.class.isInstance(annotation)){
                    if(args[i] == null){
                        throw new MikoLambEventException(ES00000099);
                    }
                }else if(NotBlank.class.isInstance(annotation)){
                    if(args[i] == null){
                        throw new MikoLambEventException(ES00000099);
                    }
                    if(StringUtils.isBlank((String)args[i])){
                        throw new MikoLambEventException(ES00000099);
                    }
                }
            }
        }
    }

}
