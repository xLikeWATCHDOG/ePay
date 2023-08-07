package cn.watchdog.epay.common;

import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {
    SUCCESS(0, "操作成功"),
    PARAMS_ERROR(40000, "请求参数错误"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    ;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
