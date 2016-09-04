package com.yangmao.model.exception;

/**
 * crud简单操作异常
 * Created by liyongfeng on 16/8/26.
 */
public class AdminServiceException extends RuntimeException {

    /**
     * 错误编码
     */
    private int errCode;

    /**
     * 错误消息
     */
    private String errMessage;

    public AdminServiceException(int errCode, String errMessage){
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }
}
