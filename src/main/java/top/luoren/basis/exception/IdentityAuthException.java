package top.luoren.basis.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 身份认证异常，用于捕获和处理身份认证时出现的异常
 *
 * @author luoren
 * @date 2019-05-06 15:56
 */
public class IdentityAuthException extends AuthenticationException {
    public IdentityAuthException(String msg) {
        super(msg);
    }

}
