package com.side.moneymoriapi.exception.jwt;

import com.side.moneymoriapi.common.exception.CustomException;
import com.side.moneymoriapi.common.exception.ErrorCode;

public class JwtException extends CustomException {
    public JwtException() {
    }

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JwtException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
