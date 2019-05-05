package top.luoren.basis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author luoren
 * @date 2019-05-05 17:36
 */
@Data
@TableName("image_code")
public class ImageCode {
    @TableField(exist = false)
    private BufferedImage image;
    @TableId
    private String id;
    private String code;
    private LocalDateTime expireTime;

    public ImageCode() {
    }

    public ImageCode(String id, BufferedImage image, String code, int expireIn) {
        this.id = id;
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ImageCode(String id, BufferedImage image, String code, LocalDateTime expireTime) {
        this.id = id;
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
