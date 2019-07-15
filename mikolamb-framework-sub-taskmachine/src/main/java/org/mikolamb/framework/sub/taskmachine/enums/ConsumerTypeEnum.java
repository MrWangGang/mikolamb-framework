package org.mikolamb.framework.sub.taskmachine.enums;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public enum  ConsumerTypeEnum {

    BLOCK("block"),
    UNBLOCK("unblock");
    // 成员变量
    @Getter
    @Setter
    private String code;
    // 构造方法
    private ConsumerTypeEnum(String code) {
        this.code = code;
    }
}
