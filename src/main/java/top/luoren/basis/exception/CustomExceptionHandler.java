package top.luoren.basis.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.luoren.basis.util.RespBody;

/**
 * @author luoren
 * @date 2019-04-28 11:47
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public RespBody handler(CustomException e) {
        return RespBody.error(e.getCode(), e.getMessage());
    }



}
