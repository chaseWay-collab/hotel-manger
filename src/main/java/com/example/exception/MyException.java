package com.example.exception;

import com.example.exception.code.ResponseCodeInterface;

/**
 * @ClassName: MyException
 * @Description: 自定义异常类
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     *  异常编号
     */
    private final int messageCode;

    /**
     *  对 messageCode 异常信息进行补充说明
     */
    private final String detailMsg;

    public MyException(String message) {
        //super(message);
        this.messageCode = 500;
        this.detailMsg = message;
    }

    public MyException(int messageCode, String message) {
        //super(message);
        this.messageCode = messageCode;
        this.detailMsg = message;
    }

    /**
     *  构造器
     * @param code 异常码
     */
    public MyException(ResponseCodeInterface code){
        this(code.getCode(), code.getMsg());
    }

    public int getMessageCode() { return messageCode; }

    public String getDetailMsg() { return detailMsg; }

}
