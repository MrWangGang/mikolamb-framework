package org.mikolamb.framework.web.security.handler;

import org.mikolamb.framework.common.exception.EventException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mikolamb.framework.common.enums.ExceptionEnum.EA00000000;

@Component
public class LambServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        throw new EventException(EA00000000);
    }
}
