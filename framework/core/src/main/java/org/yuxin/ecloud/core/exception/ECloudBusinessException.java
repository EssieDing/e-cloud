package org.yuxin.ecloud.core.exception;

import org.yuxin.ecloud.core.response.ResponseCode;
import lombok.Data;

/**
 * 自定义全局业务异常类
 */
@Data
public class ECloudBusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public ECloudBusinessException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getDesc();
    }

    public ECloudBusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ECloudBusinessException(String message) {
        this.code = ResponseCode.ERROR_PARAM.getCode();
        this.message = message;
    }

    public ECloudBusinessException() {
        this.code = ResponseCode.ERROR_PARAM.getCode();
        this.message = ResponseCode.ERROR_PARAM.getDesc();
    }

}
