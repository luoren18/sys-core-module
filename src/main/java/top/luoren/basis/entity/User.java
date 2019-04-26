package top.luoren.basis.entity;

import lombok.Data;

/**
 * @author luoren
 * @date 2019-04-23 15:51
 */
@Data
public class User {
    private int id;
    private String name;
    private String password;
    private int age;
    private String email;
}
