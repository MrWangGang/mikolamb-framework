package org.mikolamb.framework.web.security.handler;

import org.mikolamb.framework.common.exception.EventException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mikolamb.framework.common.enums.ExceptionEnum.EA00000001;

@Component
public class LambServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        throw new EventException(EA00000001);
    }
}
