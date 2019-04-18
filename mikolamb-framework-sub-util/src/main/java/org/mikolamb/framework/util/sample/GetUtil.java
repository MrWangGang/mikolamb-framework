package org.mikolamb.framework.util.sample;

import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.MikoLambEventException;

import java.util.Map;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.*;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class GetUtil {
    public static <T>T get(Map map, String value){
        if(StringUtils.isBlank(value)){
            throw new MikoLambEventException(ES00000002);
        }

        if(map == null){
            throw new MikoLambEventException(ES00000001);
        }

        if(map.isEmpty()){
            throw new MikoLambEventException(ES00000001);
        }

        Object obj = map.get(value);
        if(obj == null){
            throw new MikoLambEventException(ES00000003);
        }
        T t = (T)obj ;
        if(t == null){
            throw new MikoLambEventException(ES00000003);
        }

        return t;
    }
}
