package per.cz.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import per.cz.security.result.ResultData;
import per.cz.security.service.LikedServiceI;

import java.util.Set;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/16 16:58
 */
@Service
public class LikeServiceImpl implements LikedServiceI {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public ResultData putLike(Integer artId, Integer userId) {
        Long add = redisTemplate.opsForSet().add(artId, userId);
        return ResultData.success(add);
    }

    @Override
    public ResultData getLikes(Integer artId) {
        Long size = redisTemplate.opsForSet().size(artId);
        return ResultData.success(size);
    }

    @Override
    public ResultData delLike(Integer artId, Integer userId) {
        //从集合中删除指定元素
        Long remove = redisTemplate.opsForSet().remove(artId, userId);
        return ResultData.success(remove);
    }

    @Override
    public ResultData getLikeAll(Integer artId) {
        //按照排名先后(从小到大)打印指定区间内的元素, -1为打印全部
        Set<Integer> members = redisTemplate.opsForSet().members(artId);
        //reverseRange 从大到小
        System.out.println(members);
        return ResultData.success(members);
    }

    @Override
    public ResultData isMember(Integer artId, Integer userId) {
        Boolean ifExist = redisTemplate.opsForSet().isMember(artId, userId);
        return ResultData.success(ifExist);
    }
}
