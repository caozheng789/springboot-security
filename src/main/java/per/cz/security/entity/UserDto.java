package per.cz.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName(value = "sys_user")
public class UserDto implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String headimgurl;

    private String phone;

    private String telephone;

    private String email;

    private Date birthday;

    private Boolean sex;

    private Integer status;

    private Date createtime;

    private Date updatetime;

//    private List<Permission> permissions;




}