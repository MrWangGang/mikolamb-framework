package org.mikolamb.framework.web.security.container;

import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.common.exception.basic.MikoLambGlobalException;
import org.mikolamb.framework.util.sample.JsonUtil;
import org.mikolamb.framework.web.security.function.PrincipalModelFunction;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.*;

/**
 * @description: 获取spring secutiy中的principal
 * @author: Mr.WangGang
 * @create: 2018-11-30 下午 3:28
 **/
public class MikoLambPrincipalFactoryContainer {
    public static String getPrincipal(){
        MikoLambAuthTokenAuthentication authentication = getAuthentication();
        String principal = authentication.getPrincipal();
        if(StringUtils.isBlank(principal))throw new MikoLambEventException(EA00000004);
        return principal;
    }

    public static String getPrincipalModel(){
        MikoLambAuthTokenAuthentication authentication = getAuthentication();
        String principalModel = authentication.getPrincipalModel();
        if(principalModel == null)throw new MikoLambEventException(EA00000004);
        if(StringUtils.isBlank(principalModel.toString()))throw new MikoLambEventException(EA00000004);
        return principalModel;
    }

    public static String getCredentials(){
        MikoLambAuthTokenAuthentication authentication = getAuthentication();
        String credentials = authentication.getCredentials();
        if(StringUtils.isBlank(credentials))throw new MikoLambEventException(EA00000003);
        return credentials;
    }

    public static <T>T getPrincipal(Enum<? extends PrincipalModelFunction> param) {
        String principalModel = getPrincipalModel();
        if(param == null)throw new MikoLambEventException(EA00000009);
        PrincipalModelFunction principalModelFunction = principalModelFunction(param);
        if(StringUtils.isBlank(principalModelFunction.principalModel()))throw new MikoLambEventException(EA00000009);
        if(principalModelFunction.principal() == null)throw new MikoLambEventException(EA00000009);
        if(!principalModel.equals(principalModelFunction.principalModel()))throw new MikoLambEventException(EA00000010);
        try{
            return (T)(JsonUtil.stringToObj(getPrincipal(),principalModelFunction.principal()).orElseThrow(()->new MikoLambEventException(EA00000004)));
        }catch (MikoLambEventException e){
            if(e == null){
                throw new MikoLambEventException(EA00000004);
            }
            throw new MikoLambGlobalException(e.getCode(),e.getMessage());
        }catch (ClassCastException e){
            throw new MikoLambEventException(EA00000004);
        } catch (Throwable throwable) {
            throw new MikoLambEventException(EA00000004);
        }

    }

    public static <T>T getPrincipal(Class<? extends Enum<? extends PrincipalModelFunction>> param)  {
        String principalModel = getPrincipalModel();
        if(param == null)throw new MikoLambEventException(EA00000009);
        Enum<? extends PrincipalModelFunction>[] enums = param.getEnumConstants();
        if(enums == null)throw new MikoLambEventException(EA00000009);
        if(enums.length == 0)throw new MikoLambEventException(EA00000009);
        PrincipalModelFunction etc = (PrincipalModelFunction) Arrays.stream(enums).filter(e->{
            PrincipalModelFunction principalModelFunction = principalModelFunction(e);
            if(principalModel.equals(principalModelFunction.principalModel()))return true;
            return false;
        }).findFirst().orElseThrow(()->new MikoLambEventException(EA00000009));
        if(etc.principal()==null)throw new MikoLambEventException(EA00000009);
        try{
            return (T)(JsonUtil.stringToObj(getPrincipal(),etc.principal()).orElseThrow(()->new MikoLambEventException(EA00000004)));
        }catch (MikoLambEventException e){
            if(e == null){
                throw new MikoLambEventException(EA00000004);
            }
            throw new MikoLambGlobalException(e.getCode(),e.getMessage());
        }catch (ClassCastException e){
            throw new MikoLambEventException(EA00000004);
        } catch (Throwable throwable) {
            throw new MikoLambEventException(EA00000004);
        }

    }

    private static MikoLambAuthTokenAuthentication getAuthentication(){
        if(SecurityContextHolder.getContext() == null)throw new MikoLambEventException(EA00000008);
        if(SecurityContextHolder.getContext().getAuthentication() == null)throw new MikoLambEventException(EA00000008);
        try {
            MikoLambAuthTokenAuthentication authentication = (MikoLambAuthTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
            return authentication;
        }catch (Exception e){
            throw new MikoLambEventException(EA00000003);
        }
    }

    private static PrincipalModelFunction principalModelFunction(Enum<? extends PrincipalModelFunction> param){
        try {
            PrincipalModelFunction principalModelFunction = (PrincipalModelFunction)param;
            if(principalModelFunction == null)throw new MikoLambEventException(EA00000009);
            return principalModelFunction;
        }catch (Exception ex){
            throw new MikoLambEventException(EA00000009);
        }

    }
}
