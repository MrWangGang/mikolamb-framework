package org.mikolamb.framework.sub.guid.annotation;

import org.mikolamb.framework.sub.guid.handler.LambGUIDFactorySelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(LambGUIDFactorySelector.class)
public @interface EnableLambGUIDFactory {

}
