package org.mikolamb.framework.web.security.contract;

/**
 * @description: 契约
 * @author: Mr.WangGang
 * @create: 2018-11-16 下午 3:56
 **/
public class Contract {
    public static final String MIKOLAMB_AUTH_TOKEN_SALT  = "mikolamb.salt";
    public static final String MIKOLAMB_TOKEN_KEY = "mikolamb.auth.token.";
    public static final Long MIKOLAMB_TOKEN_TIME = new Long(1800000);
    public static final String MIKOLAMB_AUTH_TOKEN_REGX = "^"+MIKOLAMB_TOKEN_KEY+"[a-zA-Z\\d]{1,}\\.[a-zA-Z\\d]{1,}$";
}
