package org.example.manage.exception;

// 系统异常基类（可选）
public class SystemException extends RuntimeException {
    private final int code;

    public SystemException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
