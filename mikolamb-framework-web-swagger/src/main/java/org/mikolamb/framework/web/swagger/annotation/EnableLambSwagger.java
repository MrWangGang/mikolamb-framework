package org.mikolamb.framework.web.swagger.annotation;

import org.mikolamb.framework.web.swagger.config.LambSwaggerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(LambSwaggerConfig.class)
public @interface EnableLambSwagger {
}
