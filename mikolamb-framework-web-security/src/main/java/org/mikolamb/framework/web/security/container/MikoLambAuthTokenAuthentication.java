package org.mikolamb.framework.web.security.container;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @description: 框架层的用户接口
 * @author: Mr.WangGang
 * @create: 2018-11-30 下午 4:28
 **/
@Data
@Builder
public class MikoLambAuthTokenAuthentication extends AbstractAuthenticationToken {
    private final String principal;
    private String credentials;
    private String principalModel;

    public MikoLambAuthTokenAuthentication(String principal, String credentials, String principalModel){
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.principalModel = principalModel;
    }
}
