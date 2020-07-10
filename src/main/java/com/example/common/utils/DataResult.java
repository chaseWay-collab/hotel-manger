package com.example.common.utils;

import com.example.exception.code.BaseResponseCode;
import com.example.exception.code.ResponseCodeInterface;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: DataResult
 * @Description: 响应状态码和详细信息
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class DataResult <T> {

    /**
     *  请求响应code, 0为成功, 其他为失败
     */
    @ApiModelProperty(value = "请求响应code, 0为成功, 其他为失败", name = "code")
    private int code;

    /**
     *  响应异常码详细信息
     */
    @ApiModelProperty(value = "响应异常码详细信息", name = "msg")
    private String msg;

    /**
     *  响应内容, code = 0 时返回数据
     */
    @ApiModelProperty(value = "需要返回的数据", name = "data")
    private T data;

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public DataResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResult() {
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
        this.data = null;
    }

    public DataResult(T data) {
        this.data = data;
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    /**
     *  操作成功, data 为 null
     * @param <T>
     * @return
     */
    public static <T>DataResult<T> success() { return new <T>DataResult<T>(); }

    /**
     *  操作成功,且 data 不为 null
     * @param data
     * @param <T>
     * @return
     */
    public static <T>DataResult<T> success(T data) { return new <T>DataResult<T>(data); }

    /**
     *  操作失败 data 不为 null
     * @param msg
     * @param <T>
     * @return
     */
    public static <T>DataResult<T> fail(String msg){
        return new <T>DataResult(BaseResponseCode.OPERATION_ERRO.getCode());
    }

    /**
     *  自定义返回操作, data 可控
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T>DataResult<T> getResult(int code, String msg, T data) { return new <T>DataResult<T>(code,msg,data); }

    /**
     *  自定义返回操作, data 为 null
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T>DataResult<T> getResult(int code, String msg) { return new <T>DataResult(code,msg); }

    /**
     *  自定义返回操作, 入参一般是异常 code 枚举, data 为空
     * @param responseCode
     * @param <T>
     * @return
     */
    public static <T>DataResult<T> getResult(BaseResponseCode responseCode) { return new <T>DataResult<T>(responseCode); }

    /**
     *  自定义返回, 入参一般是异常 code 枚举, data 自定义
     * @param responseCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T>DataResult<T> getResult(BaseResponseCode responseCode, T data) { return new <T>DataResult<T>(responseCode,data); }

}
