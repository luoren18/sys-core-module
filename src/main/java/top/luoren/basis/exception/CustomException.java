package top.luoren.basis.exception;

import lombok.Data;

/**
 * @author luoren
 * @date 2019-04-28 11:10
 */
@Data
public class CustomException extends RuntimeException {
    private static final String ERROR = "-1";
    private String code;
    private String message;

    public CustomException() {
        this.message = "服务器异常，请联系管理员";
        this.code=ERROR;
    }

    public CustomException(String message) {
        this.message = message;
        this.code = ERROR;
    }

    public CustomException(String message, String code) {
        this.message = message;
        this.code = code;
    }
}
