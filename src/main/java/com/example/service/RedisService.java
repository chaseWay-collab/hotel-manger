package com.example.service;

import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisService
 * @Description: redis
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 验证token
     * @param key
     * @return
     */
    public boolean exists(String key){
        return this.redisTemplate.hasKey(key);
    }

    /**
     * 过期时间
     * @param key
     * @return
     */
    public Long getExpire(String key){
        if(null == key){
            // 传入数据异常
            throw new MyException(BaseResponseCode.DATA_ERROR.getCode(), "key or TimeUnit不能为空");
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 操作String数据
     * @param key
     * @param value
     */
    public void set(String key, String value){
        this.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取value操作
     * @param key
     * @return
     */
    public String get(String key){
        String value = this.redisTemplate.opsForValue().get(key);
        //System.out.println("[40]value = " + value);
        return value;
    }

    /**
     * 删除key
     * @param key
     */
    public void del(String key){
        if(this.exists(key)){
            this.redisTemplate.delete(key);
        }
    }

    /**
     * 设置expire、key和value
     * @param key
     * @param value
     * @param seconds
     */
    public void setAndExpire(String key, String value, long seconds){
        this.redisTemplate.opsForValue().set(key, value);
        this.redisTemplate.expire(key, (long)seconds, TimeUnit.SECONDS);
    }

    /**
     * 获取 Set 中的 key 集合
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        return redisTemplate.keys("*" + pattern);
    }

    /**
     * 删除keys
     * @param pattern
     */
    public void delKeys(String pattern){
        Set<String> keys = redisTemplate.keys(pattern);
        //System.out.println("[redisService]58[keys]= " + keys );
        if (!CollectionUtils.isEmpty(keys)){
            this.redisTemplate.delete(keys);
        }
    }

    /**
     * increment
     * @param key
     * @param increment
     * @return
     */
    public long incrby(String key, long increment){
        if(null == key){
            // 传入数据异常
            throw new MyException(BaseResponseCode.DATA_ERROR.getCode(), "key不呢为空");
        }
        // increment(key,long) 以增量的方式将long值存储在变量中
        // opsForValue() 操作字符串
        Long increment1 = redisTemplate.opsForValue().increment(key, increment);
        //System.out.println("[RedisService]incrby(75): " + increment);
        return increment1;
    }


}
