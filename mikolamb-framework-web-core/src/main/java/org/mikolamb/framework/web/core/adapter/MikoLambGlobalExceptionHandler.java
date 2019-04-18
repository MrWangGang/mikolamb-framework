package org.mikolamb.framework.web.core.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mikolamb.framework.common.enums.MikoLambExceptionEnum;
import org.mikolamb.framework.common.exception.basic.MikoLambGlobalException;
import org.mikolamb.framework.common.templete.MikoLambResponseTemplete;
import org.mikolamb.framework.util.sample.StackTraceElementUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@Component
@RestControllerAdvice
public class MikoLambGlobalExceptionHandler implements ErrorWebExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(MikoLambGlobalExceptionHandler.class);

    @Override
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        return write(serverWebExchange.getResponse(),handleTransferException(throwable));
    }

    private MikoLambResponseTemplete handleTransferException(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        logger.debug(str);
        if(e instanceof MikoLambGlobalException){
            return result(((MikoLambGlobalException)e).getCode(),((MikoLambGlobalException)e).getMessage());
        } else if(StackTraceElementUtil.checkGlobalExcetionOStackTrace(e.getStackTrace())){
            return result(((MikoLambGlobalException)e).getCode(),((MikoLambGlobalException)e).getMessage());
        } else if(e instanceof WebExchangeBindException){
            return result(MikoLambExceptionEnum.EI00000000.getCode(), MikoLambExceptionEnum.EI00000000.getMessage());
        }else if(e instanceof NoSuchElementException){
            return result(MikoLambExceptionEnum.ES00000027.getCode(), MikoLambExceptionEnum.ES00000027.getMessage());
        }else if(e instanceof RedisSystemException){
            return result(MikoLambExceptionEnum.ES00000026.getCode(), MikoLambExceptionEnum.ES00000026.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            return result(MikoLambExceptionEnum.EI00000000.getCode(), MikoLambExceptionEnum.EI00000000.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            return result(MikoLambExceptionEnum.ES00000000.getCode(), MikoLambExceptionEnum.ES00000000.getMessage());
        } else if (e instanceof NullPointerException) {
            return result(MikoLambExceptionEnum.ES00000000.getCode(), MikoLambExceptionEnum.ES00000000.getMessage());
        } else if (e instanceof RuntimeException) {
            return result(MikoLambExceptionEnum.ES00000000.getCode(), MikoLambExceptionEnum.ES00000000.getMessage());
        }else if ( e instanceof Exception){
            return result(MikoLambExceptionEnum.ES00000000.getCode(), MikoLambExceptionEnum.ES00000000.getMessage());
        }
        return result(MikoLambExceptionEnum.ES00000000.getCode(), MikoLambExceptionEnum.ES00000000.getMessage());
    }

    private MikoLambResponseTemplete result(String code, String message){
        MikoLambResponseTemplete mikoLambResponseTemplete = new MikoLambResponseTemplete();
        mikoLambResponseTemplete.setServiceCode(code);
        mikoLambResponseTemplete.setServiceMessage(message);
        return mikoLambResponseTemplete;
    }



    private  <T> Mono<Void> write(ServerHttpResponse httpResponse, T object) {
        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return httpResponse
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = httpResponse.bufferFactory();
                    try {
                        return bufferFactory.wrap((new ObjectMapper()).writeValueAsBytes(object));
                    } catch (Exception ex) {
                        logger.warn("Error writing response", ex);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }




}
