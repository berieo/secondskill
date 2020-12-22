package com.spring.secondskill.exception;

import com.spring.secondskill.result.CodeMsg;
import com.spring.secondskill.result.Result;
import com.sun.org.apache.bcel.internal.classfile.Code;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /*
        @ControllerAdvice 处理所有异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest httpServletRequest, Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException){
            GlobalException ge = (GlobalException) e;
            return Result.error(ge.getCm());
        }else if(e instanceof BindException){
            /*
                exception有多个
            */
            BindException ex = (BindException) e;
            List<ObjectError> erorrs = ex.getAllErrors();
            ObjectError error = erorrs.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
