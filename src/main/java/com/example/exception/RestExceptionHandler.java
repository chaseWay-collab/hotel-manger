package com.example.exception;

import com.example.common.utils.DataResult;
import com.example.exception.code.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @ClassName: RestExceptionHandler
 * @Description: 自定义全局异常处理类
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     *  系统繁忙, 请稍后再试
     * @param e 任意异常
     * @param <T>
     * @return  common.utils.DataResult<T>
     */
    @ExceptionHandler(Exception.class)
    public <T>DataResult<T> handleException(Exception e) {
        log.error("Exception, exception:{}", e);  // Fewer arguments provided (0) than placeholders specified (1)
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }

    /**
     *  自定义全局异常处理
     * @param e 自定义异常
     * @param <T>
     * @return common.utils.DataResult<T>
     */
    @ExceptionHandler(value = MyException.class)
    public <T> DataResult<T> MyExceptionHandler(MyException e){
        log.error("MyException, exception:{}", e);
        return new DataResult<>(e.getMessageCode(), e.getDetailMsg());
    }

    /**
     *  没有权限, 返回403视图
     * @param e 权限认证异常
     * @param <T>
     * @return  org.springframework.web.servlet.ModelAndView
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public <T>DataResult<T> errorPermission(AuthorizationException e){
        log.error("MyException, exception:{}", e);
        // UNAUTHORIZED_ERROR(401008, "权限校验不通过")
        return new DataResult<>(BaseResponseCode.UNAUTHORIZED_ERROR);
    }

    /**
     *  处理 validation 框架异常
     * @param e validation的方法异常
     * @param <T>
     * @return  org.springframework.web.bind.MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    <T>DataResult<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        log.error("methodArgumentNotValidExceptionHandler  bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors());
        // org.springframework.validation.ObjectError
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }

    private <T> DataResult<T> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        int i = 0;
        for(ObjectError error : errors){
            msgs[i] = error.getDefaultMessage();
            log.info("msg={}", msgs[i]);
            i++;
        }
        // METHODARGUMENTNOTVALIDEXCEPTION(401007, "方法参数校验异常")
        return DataResult.getResult(BaseResponseCode.METHODARGUMENTNOTVALIDEXCEPTION.getCode(), msgs[0]);
    }

}
