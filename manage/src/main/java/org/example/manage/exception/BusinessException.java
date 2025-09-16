package org.example.manage.exception;

public class BusinessException extends RuntimeException {
    private final int code; // 错误码

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                '}';
    }
}

