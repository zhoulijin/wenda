package com.freestyle.wenda.service;

import com.freestyle.wenda.util.JedisAdapter;
import com.freestyle.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;


    public long getLikeCount(int entiryType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entiryType, entityId);
        return jedisAdapter.scard(likeKey);
    }

    public int getLikeStatus(int userId, int entiryType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entiryType, entityId);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }

        String dislikeKey = RedisKeyUtil.getLikeKey(entiryType, entityId);
        return jedisAdapter.sismember(dislikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    public long like(int userId, int entiryType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entiryType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String dislikeKey = RedisKeyUtil.getDisLikeKey(entiryType, entityId);
        jedisAdapter.srem(dislikeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entiryType, int entityId) {
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entiryType, entityId);
        jedisAdapter.sadd(dislikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entiryType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }
}
