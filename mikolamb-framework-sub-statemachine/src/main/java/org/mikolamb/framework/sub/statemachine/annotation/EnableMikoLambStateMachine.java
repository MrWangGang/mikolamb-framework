package org.mikolamb.framework.sub.statemachine.annotation;


import org.mikolamb.framework.sub.statemachine.selector.MikoLambStateMachineSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MikoLambStateMachineSelector.class)
public @interface EnableMikoLambStateMachine {

}
