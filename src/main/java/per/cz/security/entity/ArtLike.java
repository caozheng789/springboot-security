package per.cz.security.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import per.cz.security.entity.emun.LikedStatusEnum;

/**
 * @author : zheng
 * @version : 1.0
 * @desc : 点赞
 * @date : 2020/6/16 10:44
 */
@Data
public class ArtLike {

    //主键id
    @Id
    private Integer id;

    //被点赞的用户的id
    private String likedUserId;

    //点赞的用户的id
    private String likedPostId;

    //点赞的状态.默认未点赞
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public ArtLike() {
    }

    public ArtLike(String likedUserId, String likedPostId, Integer status) {
        this.likedUserId = likedUserId;
        this.likedPostId = likedPostId;
        this.status = status;
    }
}
