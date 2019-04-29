package top.luoren.basis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author luoren
 * @date 2019-04-28 09:19
 */
@Data
public class Menu {
    private int id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private int parentId;
    @JsonIgnore
    private List<Role> roles;
    private boolean enabled;
}
