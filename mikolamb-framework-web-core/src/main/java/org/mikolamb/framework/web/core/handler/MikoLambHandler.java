package org.mikolamb.framework.web.core.handler;

import org.mikolamb.framework.common.templete.MikoLambResponseTemplete;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public abstract class MikoLambHandler {

    protected Mono<ServerResponse> reactive(Object data){
        MikoLambResponseTemplete mikoLambResponseTemplete = new MikoLambResponseTemplete(data);
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(mikoLambResponseTemplete),MikoLambResponseTemplete.class);
    }

    protected Mono<ServerResponse> reactive(){
        MikoLambResponseTemplete mikoLambResponseTemplete = new MikoLambResponseTemplete();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(mikoLambResponseTemplete),MikoLambResponseTemplete.class);
    }

    protected Mono<MikoLambResponseTemplete> returning(Object data){
        return Mono.just(new MikoLambResponseTemplete(data));
    }

    protected Mono<MikoLambResponseTemplete> returning(){
        return Mono.just(new MikoLambResponseTemplete());
    }
}
