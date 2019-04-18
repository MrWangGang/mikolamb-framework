package org.mikolamb.framework.sub.statemachine.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface MikoLambStateMachineListener {

}
