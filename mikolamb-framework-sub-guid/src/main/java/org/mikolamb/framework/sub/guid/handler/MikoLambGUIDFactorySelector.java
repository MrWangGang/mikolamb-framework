package org.mikolamb.framework.sub.guid.handler;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @description: GUID生成
 * @author: Mr.WangGang
 * @create: 2018-12-03 下午 3:44
 **/
@Data
public class MikoLambGUIDFactorySelector {
    @Value("${mikolamb.datacenterId}")
    private Long datacenterId;
    @Value("${mikolamb.machineId}")
    private Long machineId;

    @Bean
    public MikoLambGUIDFactory set(){
        return new MikoLambGUIDFactory(datacenterId,machineId);
    }
}
