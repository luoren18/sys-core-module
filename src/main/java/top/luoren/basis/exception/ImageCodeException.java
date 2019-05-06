package top.luoren.basis.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author luoren
 * @date 2019-05-06 15:56
 */
public class ImageCodeException extends AuthenticationException {
    public ImageCodeException(String msg) {
        super(msg);
    }
}
