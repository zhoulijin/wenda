package com.freestyle.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.freestyle.wenda.util.JedisAdapter;
import com.freestyle.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {

            String key = RedisKeyUtil.getBizEventqueue();
            jedisAdapter.lpush(key, JSONObject.toJSONString(eventModel));
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
