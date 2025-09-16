package org.example.manage.common;

public enum ErrorCode {
    // 业务异常（1xxx）
    USER_NOT_FOUND(1001, "用户不存在"),
    PARAM_INVALID(1002, "参数不合法"),

    // 系统异常（5xxx）
    DATABASE_ERROR(5001, "数据库连接失败"),
    SERVICE_TIMEOUT(5002, "服务超时");

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter 方法
}