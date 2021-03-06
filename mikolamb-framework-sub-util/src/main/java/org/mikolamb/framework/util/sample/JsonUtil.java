package org.mikolamb.framework.util.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mikolamb.framework.common.exception.MikoLambEventException;

import java.io.IOException;
import java.util.Optional;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.*;

/**
 * @description: JSON解析工具
 * @author: Mr.WangGang
 * @create: 2018-11-23 下午 12:51
 **/
public class JsonUtil {
    public static String objToString(Object data){
        try {
            return (new ObjectMapper()).writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new MikoLambEventException(ES00000020);
        }
    }

    public static <T>Optional<T> stringToObj(String data,Class<T> clazz){
        try {
            return Optional.ofNullable((new ObjectMapper()).readValue(data,clazz));
        } catch (JsonProcessingException e) {
            throw new MikoLambEventException(ES00000019);
        } catch (IOException e) {
            throw new MikoLambEventException(ES00000003);
        }
    }
}
