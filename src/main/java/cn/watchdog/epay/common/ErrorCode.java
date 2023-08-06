package cn.watchdog.epay.common;

/**
 * 错误码
 */
public enum ErrorCode {

    SUCCESS(0, "操作成功"),
    PARAMS_ERROR(40000, "请求参数错误");

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

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
