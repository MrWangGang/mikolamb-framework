package org.mikolamb.framework.util.sample;

import org.mikolamb.framework.common.exception.basic.MikoLambGlobalException;

import java.util.Arrays;

/**
 * @description: 异常栈
 * @author: Mr.WangGang
 * @create: 2018-11-22 下午 5:45
 **/
public class StackTraceElementUtil {
    public static Boolean checkGlobalExcetionOStackTrace(StackTraceElement[] es){
        return Arrays.stream(es).anyMatch(e -> MikoLambGlobalException.class.getName().equals(e.getClassName()));
    }
}
