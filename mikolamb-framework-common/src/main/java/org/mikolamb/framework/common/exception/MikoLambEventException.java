package org.mikolamb.framework.common.exception;

import org.mikolamb.framework.common.enums.MikoLambExceptionEnum;
import org.mikolamb.framework.common.exception.basic.MikoLambGlobalException;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class MikoLambEventException extends MikoLambGlobalException {

    public MikoLambEventException(MikoLambExceptionEnum error) {
        super(error.getCode(),error.getMessage());
    }
}
