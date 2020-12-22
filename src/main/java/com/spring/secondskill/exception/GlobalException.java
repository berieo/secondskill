package com.spring.secondskill.exception;

import com.spring.secondskill.result.CodeMsg;

public class GlobalException extends RuntimeException {
    /*
        GlobalException实体类
     */

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
