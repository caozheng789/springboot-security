package per.cz.security.service;


import per.cz.security.result.ResultData;


/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/16 10:55
 */
public interface LikedServiceI {

    /**
     * 点赞文章
     * @param artId
     * @param userId
     * @return
     */
    ResultData putLike(Integer artId, Integer userId);

    /**
     * 获取文章点赞数
     * @param artId
     * @return
     */
    ResultData getLikes(Integer artId);

    /**
     * 删除点赞
     * @param userId
     * @return
     */
    ResultData delLike(Integer artId, Integer userId);

    /**
     * 获取所有点赞
     * @param artId
     * @return
     */
    ResultData getLikeAll(Integer artId);

    /**
     * 判断用户是否点赞
     * @param userId
     * @return
     */
    ResultData isMember(Integer artId,Integer userId);
}
