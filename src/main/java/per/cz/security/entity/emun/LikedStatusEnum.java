package per.cz.security.entity.emun;

import lombok.Getter;

/**
 * @author : zheng
 * @version : 1.0
 * @desc : LikedStatusEnum 用户点赞状态的枚举类：
 * @date : 2020/6/16 10:47
 */
@Getter
public enum LikedStatusEnum {
    LIKE(1, "点赞"),
    UNLIKE(0, "取消点赞/未点赞"),
    ;

    private Integer code;

    private String msg;

    LikedStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}