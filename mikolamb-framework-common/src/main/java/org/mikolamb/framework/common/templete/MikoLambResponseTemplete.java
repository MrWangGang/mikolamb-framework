package org.mikolamb.framework.common.templete;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.enums.MikoLambJsonSymbolicFinalConfig;

/**
 * Created by WangGang on 2017/7/4 0004.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class MikoLambResponseTemplete {

    private String serviceCode;

    private String serviceMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public String getServiceCode() {
        if(StringUtils.isBlank(serviceCode)){
            serviceCode = MikoLambJsonSymbolicFinalConfig.DEFAULT_SUCCESS_SERVICE_CODE;
        }
        return serviceCode;
    }

    public void setServiceCode(String service_code) {
        this.serviceCode = service_code;
    }

    public String getServiceMessage() {
        if(StringUtils.isBlank(serviceMessage)){
            serviceMessage = MikoLambJsonSymbolicFinalConfig.DEFAULT_SUCCESS_SERVICE_MESSAGE;
        }
        return serviceMessage;
    }

    public void setServiceMessage(String service_message) {
        this.serviceMessage = service_message;
    }

    public Object getData() {

        if(data == null){
            return null;
        }
        return data;
    }

    private void setData(Object data) {
        this.data = data;
    }

    public MikoLambResponseTemplete(Object data){
        this.data = data;
    }

    public MikoLambResponseTemplete(){

    }
}
