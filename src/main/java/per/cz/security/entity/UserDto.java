package per.cz.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@TableName(value = "sys_user")
public class UserDto extends BaseEntity<Integer> implements Serializable {


    private String username;

    private String password;

    private String nickname;

    private String headimgurl;

    private String phone;

    private String telephone;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private Boolean sex;

    private Integer status;



//    private List<Permission> permissions;




}