package top.luoren.basis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * @author luoren
 * @date 2019-05-05 17:36
 */
@Data
@TableName("image_code")
public class ImageCode {
    @JsonIgnore
    @TableField(exist = false)
    private BufferedImage image;
    @TableId(type = IdType.INPUT)
    private String id;
    private String code;
    private LocalDateTime expireTime;

    public ImageCode() {
    }

    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    public void write(OutputStream sos) throws IOException {
        ImageIO.write(image, "jpeg", sos);
        sos.close();
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
