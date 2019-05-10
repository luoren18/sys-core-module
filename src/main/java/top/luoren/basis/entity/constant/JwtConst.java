package top.luoren.basis.entity.constant;

/**
 * @author luoren
 * @date 2019-05-10 17:34
 */
public class JwtConst {
    /**
     * 过期时间（ms 毫秒）
     */
    public static final long EXPIRATION_TIME = 432_000_000;
    /**
     * JWT密码
     */
    public static final String SECRET = "CodeSheepSecret";
    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";
    /**
     * 存放Token的Header Key
     */
    public static final String HEADER_STRING = "Authorization";
}
