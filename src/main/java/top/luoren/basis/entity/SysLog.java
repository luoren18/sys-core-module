package top.luoren.basis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author luoren
 * @date 2019-05-05 09:13
 */
@Data
@TableName("syslog")
public class SysLog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private String ip;
    private String code;
    private String message;
    private LocalDateTime createTime;
}
