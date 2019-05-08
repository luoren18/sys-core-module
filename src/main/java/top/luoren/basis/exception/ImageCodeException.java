package top.luoren.basis.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * @author luoren
 * @date 2019-05-06 15:56
 */
public class ImageCodeException extends AccessDeniedException {
    public ImageCodeException(String msg) {
        super(msg);
    }

}
