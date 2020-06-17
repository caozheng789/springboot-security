package per.cz.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import per.cz.security.result.ResultData;
import per.cz.security.service.LikedServiceI;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/16 10:36
 */
@Controller
@ResponseBody
@RequestMapping("like")
public class LikeController {

    @Autowired
    private LikedServiceI likedService;

    /**
     * 点赞
     * @param artId
     * @param userId
     * @return
     */
    @PutMapping
    public ResultData putLike(Integer artId, Integer userId){
        return likedService.putLike(artId,userId);
    }

    @GetMapping
    public ResultData getLikes(Integer artId){
        return likedService.getLikes(artId);
    }

    @DeleteMapping
    public ResultData delLike(Integer artId,Integer userId){
        return likedService.delLike(artId,userId);
    }

    /**
     * 获取所有成员
     * @param artId
     * @return
     */
    @GetMapping("getLikeAll")
    public ResultData getLikeAll(Integer artId){
        return likedService.getLikeAll(artId);
    }

    /**
     * 用户是否点赞
     * @param userId
     * @return
     */
    @GetMapping("isMember")
    public ResultData isMember(Integer artId,Integer userId){
        return likedService.isMember(artId,userId);
    }


}
