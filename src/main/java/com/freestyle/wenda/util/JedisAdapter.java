package com.freestyle.wenda.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisAdapter implements InitializingBean {

    public static void print(int index, Object object) {
        System.out.println(String.format("%d %s", index, object == null ? "null" : object.toString()));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/10");

        jedis.set("hello", "world");
        print(1, jedis.get("hello"));

        jedis.rename("hello", "newhello");
        print(1, jedis.get("hello"));
        print(1, jedis.get("newhello"));

        jedis.setex("hello2", 15, "world2");
        print(1, jedis.get("hello2"));

        jedis.set("pv", "100");
        jedis.incr("pv");
        print(1, jedis.get("pv"));

        jedis.incrBy("pv", 5);
        print(1, jedis.get("pv"));

        print(2, jedis.keys("*"));

        String listname = "list";
        jedis.del(listname);
        for (int i = 0; i < 10; i ++) {
            jedis.lpush(listname, "a" + i);
        }
        print(3, jedis.lrange(listname, 0, 2));
        print(4, jedis.llen(listname));
        print(5, jedis.lpop(listname));
        print(6, jedis.llen(listname));

        // hash

        String user = "userxx";
        print(7, jedis.hset(user, "name", "Jam"));
        print(8, jedis.hget(user, "name"));

        print(9, jedis.hexists(user, "email"));
        print(10, jedis.hexists(user, "name"));

        String likeKey1 = "like1";
        String likeKey2 = "like2";

        for (int i = 0; i < 10; i ++) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * i));
        }
        print(11, jedis.smembers(likeKey1));

        print(12, jedis.smembers(likeKey2));

        print(13, jedis.sunion(likeKey1, likeKey2));

        print(14, jedis.sdiff(likeKey1, likeKey2));

        print(15, jedis.sinter(likeKey1, likeKey2));


        print(16, jedis.sismember(likeKey1, "11"));

        print(17, jedis.scard(likeKey1));
    }

    JedisPool pool;

    @Override
    public void afterPropertiesSet() {
        String host = "redis://localhost:6379/10";
        pool = new JedisPool(host);
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch(Exception e) {

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch(Exception e) {

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch(Exception e) {

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch(Exception e) {

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}
